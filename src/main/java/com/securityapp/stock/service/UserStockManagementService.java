package com.securityapp.stock.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securityapp.stock.dao.Sell;
import com.securityapp.stock.dao.Stock;
import com.securityapp.stock.dao.UserSeals;
import com.securityapp.stock.dao.UserStock;
import com.securityapp.stock.dao.Users;
import com.securityapp.stock.dto.ResponseEntity;
import com.securityapp.stock.repository.SellRepository;
import com.securityapp.stock.repository.StockRepository;
import com.securityapp.stock.repository.UserSealRepository;
import com.securityapp.stock.repository.UserStockRepository;
import com.securityapp.stock.repository.UsersRepository;



@Service()
public class UserStockManagementService {
	
//	Logger logger = LogManager.getLogger(UserStockManagementService.class.getName());
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private SellRepository sellRepo;
	
	@Autowired
	private UserSealRepository userSealRepo;
	
	@Autowired
	private UsersRepository ur;
	
	@Autowired
	private UserStockRepository userStockRepo;

	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ResponseEntity saveUserStocks(UserStock stock) {
		
//		logger.info("Entering into saveUserStocks");
		
		ResponseEntity response = new ResponseEntity();
		HashMap<String, Object> body = new HashMap<>();
		try {
			if (stock != null && stock.getStockId() != null && stock.getUserId() != null && stock.getCount() != null && stock.getCount().compareTo(new Integer(0)) > 0) {
				
				Stock stockData = stockRepo.getStockById(stock.getStockId());
				Sell sell = sellRepo.getSell(stock.getStockId());
//				logger.info("stockData - {}, sell - {} ", stockData, sell);
				
				if (stockData != null && stockData.getCount() >= stock.getCount()) {
					
					Integer stockCount = stockData.getCount() - stock.getCount();
					stockData.setCount(stockCount);
					stockData.setUpdateAt(df.format(new Date()));
					
					Integer userStockCount = stock.getCount(); 
					Integer usc = stock.getCount();
					UserStock userStock = userStockRepo.getUserStockByUserIdAndStocId(stock.getUserId(), stock.getStockId());
//					logger.info("userStock - {} ", userStock);
					
					if (userStock !=  null) {
						stock = userStock;
						userStockCount += stock.getCount();
						stock.setCount(userStockCount);
					} else {
						stock.setCreatedAt(df.format(new Date()));
					}
					stock = userStockRepo.save(stock);
					stockData = stockRepo.save(stockData);
					
					if (sell == null) {
						sell = new Sell();
						sell.setCount(0);
						sell.setCreatedAt(df.format(new Date()));
						sell.setStockId(stockData.getId());
						sell = sellRepo.save(sell);
					}
					
					UserSeals userSeal = userSealRepo.getUserSealsByUserStockId(stock.getId());
					if (userSeal == null) {
						userSeal = new UserSeals();
						userSeal.setUserStockId(stock.getId());
						userSeal.setCount(0);
						userSeal.setCreatedAt(df.format(new Date()));
						userSeal.setUpdatedAt(df.format(new Date()));
						userSealRepo.save(userSeal);
					} else {
						userSeal.setUpdatedAt(df.format(new Date()));
						userSealRepo.save(userSeal);
					}
					
					Users user = ur.findById(stock.getUserId()).get();
					Integer count = user.getStocksCount();
					count += usc;
					user.setStocksCount(count);
					user.setUpdatedAt(df.format(new Date()));
					user = ur.save(user);
					
					response.setMessage("Saved Successfully !!!");
					response.setStatus("Success");
					
				} else {
					response.setMessage("Required parameters are missing");
					response.setStatus("Error");
				}
				
			} else {
				response.setMessage("Required parameters are missing");
				response.setStatus("Error");
			}
		} catch (Exception e) {
//			logger.error("Error : {} - {} ", e.getCause(), e.getMessage());
			response.setMessage("Issue in saving details");
			response.setStatus("Error");
		}
		
		return response;
	}

	public ResponseEntity saveUserStocksSold(UserStock userStock) {
		
		ResponseEntity response = new ResponseEntity();
		try {
			
			if (userStock != null && userStock.getId() != null && !userStock.getId().equals("") && userStock.getCount() != null && !userStock.getCount().equals("")) {
				UserSeals us = userSealRepo.getUserSealsByUserStockId(userStock.getId());
				
				if (us != null && !us.equals("")) {
					Integer diff = us.getUserStock().getCount() - us.getCount();
					if (userStock.getCount().compareTo(diff) <= 0) {
						
						Users user = us.getUserStock().getUser();
						Integer count = user.getSellsCount();
						count += userStock.getCount();
						user.setSellsCount(count);
						user.setUpdatedAt(df.format(new Date()));
					
						count = us.getCount();
						count += userStock.getCount();
						us.setCount(count);
						us.setUpdatedAt(df.format(new Date()));
						us = userSealRepo.save(us);
						Sell sell = sellRepo.getSell(us.getUserStock().getStockId());
						count = sell.getCount();
						count += userStock.getCount();
						sell.setCount(count);
						sell = sellRepo.save(sell);
						user = ur.save(user);
						
						
						response.setMessage("Saved Successfully !!!");
						response.setStatus("Success");
					} else {
						response.setMessage("Insufficient stock details. Please check");
						response.setStatus("Error");
					}
				} else {
					response.setMessage("Record Missing");
					response.setStatus("Error");
				}
				
			} else {
				response.setMessage("Required parameters are missing");
				response.setStatus("Error");
			}
		} catch (Exception e) {
//			logger.error("Error : {} - {} ", e.getCause(), e.getMessage());
			response.setMessage("Issue in saving details");
			response.setStatus("Error");
		}
		
		return response;
	}

	public ResponseEntity getAllUserStocks(Long userId) {
		
		ResponseEntity response = new ResponseEntity();
		
		try {
			
			List<Object[]> list = userSealRepo.getCurrentUserStocks(userId);
			List<HashMap<String, Object>> details = new ArrayList<>();
			HashMap<String, Object> content = new HashMap<>();
			
			for (Object[] obj: list) {
				HashMap<String, Object> li = new HashMap<>();
				li.put("id", obj[0]);
				li.put("stockId", obj[1]);
				Integer soldCount = (Integer) obj[2];
				Integer stockCount = (Integer) obj[3];
				Integer remainingCount = stockCount - soldCount;
				li.put("stockCount", stockCount);
				li.put("soldCount", soldCount);
				li.put("remainingCount", remainingCount);
				li.put("name", obj[4]);
				li.put("userId", userId);
				details.add(li);
				
			}
			content.put("data", details);
			response.setContent(content);
			response.setStatus("Success");
			
		} catch (Exception e) {
//			logger.error("Error : {} - {} ", e.getCause(), e.getMessage());
			response.setMessage("Issue in getting details");
			response.setStatus("Error");
		}
		
		return response;
	}

	public ResponseEntity getUserStocks(Long userId, Integer userStockId) {
		
		ResponseEntity response = new ResponseEntity();
		
		try {
			HashMap<String, Object> content = new HashMap<>();
			UserSeals obj = userSealRepo.getCurrentUserStocksByUserStockId(userId, userStockId);
			
			if (obj != null) {
				HashMap<String, Object> li = new HashMap<>();
				li.put("id", obj.getUserStockId());
				li.put("stockId", obj.getUserStock().getStockId());
				Integer soldCount = obj.getCount();
				Integer stockCount = obj.getUserStock().getCount();
				Integer remainingCount = stockCount - soldCount;
				li.put("stockCount", stockCount);
				li.put("soldCount", soldCount);
				li.put("remainingCount", remainingCount);
				li.put("name", obj.getUserStock().getStock().getName());
				li.put("userId", userId);
				content.put("data", li);
				
				response.setContent(content);
				response.setStatus("Success");
			}
			
		} catch( Exception e) {
//			logger.error("Error : {} - {} ", e.getCause(), e.getMessage());
			response.setMessage("Issue in getting details");
			response.setStatus("Error");
		}
		
		return response;
	}
	
	
	
}
