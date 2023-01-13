package com.securityapp.stock.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securityapp.stock.dao.Users;
import com.securityapp.stock.dto.ResponseEntity;
import com.securityapp.stock.repository.UserSealRepository;
import com.securityapp.stock.repository.UsersRepository;


@Service()
public class AdminUserService {

	Logger logger = LoggerFactory.getLogger(AdminUserService.class.getName());

	@Autowired
	private UserSealRepository userSealRepo;

	@Autowired
	private UsersRepository ur;

	public ResponseEntity getUserStocks(Long userId, HashMap<String, Object> body) {

		ResponseEntity response = new ResponseEntity();
		HashMap<String, Object> content = new HashMap<>();
		try {
			
			String startDate = (String) body.get("startDate");
			String endDate = (String) body.get("endDate");
			List<Object[]> userStocks = userSealRepo.getUserStockByDate(userId, startDate, endDate);
			List<Map<String, Object>> details = new ArrayList<>();

			Integer totalBuyStockCount = 0;
			Integer totalSellStockCount = 0;

			for (Object[] obj : userStocks) {
				HashMap<String, Object> data = new HashMap<>();
				data.put("id", obj[0]);
				data.put("stockId", obj[1]);
				data.put("buyedStock", obj[3]);
				data.put("soldStock", obj[2]);
				data.put("name", obj[4]);
				data.put("updatedAt", obj[5]);
				totalBuyStockCount += (Integer) obj[3];
				totalSellStockCount += (Integer) obj[2];
				details.add(data);
			}
			content.put("data", details);
			content.put("total_sell_count", totalSellStockCount);
			content.put("total_buy_count", totalBuyStockCount);
			response.setContent(content);
			response.setStatus("Success");

		} catch (Exception e) {
			logger.error("Error : " + e.getCause() + " " + e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in fetching details");
		}

		return response;
	}

	public ResponseEntity getUsers() {

		ResponseEntity response = new ResponseEntity();
		HashMap<String, Object> content = new HashMap<>();

		try {

			List<Map<String, Object>> details = new ArrayList<>();
			List<Users> users = ur.findAll();
			for (Users user : users) {
				HashMap<String, Object> data = new HashMap<>();
				data.put("id", user.getId());
				data.put("name", user.getName());
				data.put("email", user.getEmail());
				data.put("role", user.getRole());
				data.put("createdAt", user.getCreatedAt());
				data.put("updatedAt", user.getUpdatedAt());
				data.put("stock_count", user.getStocksCount());
				data.put("sells_count", user.getSellsCount());
				details.add(data);
			}

			content.put("data", details);
			response.setContent(content);
			response.setStatus("Success");

		} catch (Exception e) {
//			logger.error("Error : " + e.getCause() + " " + e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in fetching details");
		}

		return response;
	}

	public InputStream writeUserStocksToCsv(Long userId, String startDate, String endDate) {
		try {
			
//			logger.info("Enter into writeUserStocksToCsv");
			List<Object[]> userStocks = userSealRepo.getUserStockByDate(userId, startDate, endDate);
			List<Map<String, Object>> details = new ArrayList<>();
			
			Integer totalBuyStockCount = 0;
			Integer totalSellStockCount = 0;
			int count = 1;
			try (final ByteArrayOutputStream stream = new ByteArrayOutputStream(); final CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(stream), CSVFormat.DEFAULT)) {
				
				csvPrinter.printRecord("Id", "Item Name", "Stock Count", "Sell Count", "Update Time");
				
				for (Object[] obj : userStocks) {
			          csvPrinter.printRecord(count++, obj[4], obj[3], obj[2],obj[5]);
						totalBuyStockCount += (Integer) obj[3];
						totalSellStockCount += (Integer) obj[2];
				
				}
				
				csvPrinter.flush();
	            return new ByteArrayInputStream(stream.toByteArray());
				
			}
			
			
		} catch(Exception e) {
//			logger.error("Error : "+e.getCause() + " "+e.getMessage());
			throw new RuntimeException("Csv writing error: " + e.getMessage());
		}
		
	}
}