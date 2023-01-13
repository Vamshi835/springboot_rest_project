package com.securityapp.stock.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securityapp.stock.dao.Stock;
import com.securityapp.stock.dto.ResponseEntity;
import com.securityapp.stock.repository.StockRepository;



@Service()
public class StrokeManagementService {

//	Logger logger = LogManager.getLogger(StrokeManagementService.class.getName());
	
	@Autowired
	private StockRepository stockRepository;

	
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public ResponseEntity saveStockDetails(Stock stock) {
		
		ResponseEntity response = new ResponseEntity();
//		logger.info("Entering into save stocks");
		
		try {
			if (stock != null) {
				if (stock.getId() != null && !stock.getCount().equals("")) {
					Integer count = stock.getCount();
					stock = stockRepository.getStockById(stock.getId());
					
					if (null != stock) {
						count += stock.getCount();
						stock.setCount(count);
						stock.setUpdateAt(df.format(new Date()));
					}
				} else {
					stock.setCreatedAt(df.format(new Date()));
					stock.setUpdateAt(df.format(new Date()));
					stock.setIsActive("Y");
				}
				stockRepository.save(stock);
				response.setMessage("saved succesfully!!!");
				response.setStatus("Success");
			} else {
				response.setMessage("Stock details are empty");
				response.setStatus("Error");
			}
		} catch (Exception e) {
//			logger.error("Error : " + e.getCause() +" " + e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in saving details");
		}
		
		
		
		return response;
	}

	public ResponseEntity getStockDetails(Integer id) {
		
		ResponseEntity response = new ResponseEntity();
		
		if (id != null && !id.equals("")) {
			Stock stock = stockRepository.getStockById(id);
			
			if (stock != null) {
				HashMap<String, Object> details = new HashMap<>();
				details.put("data", stock);
				response.setContent(details);
				response.setStatus("Success");
			}
		} else {
			response.setMessage("Missing required details in request");
			response.setStatus("Error");
		}
		
		return response;
	}

	public ResponseEntity getAllStockDetails() {
		
		ResponseEntity response = new ResponseEntity();
		List<Stock> stocks = stockRepository.getAllStocks();
		HashMap<String, Object> details = new HashMap<>();
		details.put("data", stocks);
		response.setContent(details);
		response.setStatus("Success");
		return response;
	}
	
}
