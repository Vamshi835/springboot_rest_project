package com.securityapp.stock.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securityapp.stock.dao.UserStock;
import com.securityapp.stock.dto.ResponseEntity;
import com.securityapp.stock.service.UserStockManagementService;



@RestController
@RequestMapping("/api/user/stock")
public class UserStockManagementController {

//	Logger logger = LogManager.getLogger(UserStockManagementController.class.getName());
	
	@Autowired
	private UserStockManagementService usms;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity save(@RequestBody UserStock stock)
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		
		try {
			response = usms.saveUserStocks(stock);
		} catch (Exception e) {
//			logger.error("Error : "+e.getCause() + " "+e.getMessage());
//			logger.error("Error in saving the user stocks");
			
			response.setMessage("Error in saving the user stocks");
			response.setStatus("Error");
		}
		
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/sell", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity sell(@RequestBody UserStock userStock)
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		
		try {
			response = usms.saveUserStocksSold(userStock);
		} catch (Exception e) {
//			logger.error("Error : "+e.getCause() + " "+e.getMessage());
//			logger.error("Error in saving the user stocks");
			
			response.setMessage("Error in saving the user sells details");
			response.setStatus("Error");
		}
		
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/all", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity getAll(@RequestParam ("id_user") Long userId)
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		
		try {
			response = usms.getAllUserStocks(userId);
		} catch (Exception e) {
//			logger.error("Error : "+e.getCause() + " "+e.getMessage());
//			logger.error("Error in getting the user stocks");
			
			response.setMessage("Error in getting the details");
			response.setStatus("Error");
		}
		
		return response;
	}
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity getUserStock(@RequestParam ("id_user") Long userId, @RequestParam ("id_user_stock") Integer userStockId)
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		
		try {
			response = usms.getUserStocks(userId, userStockId);
		} catch (Exception e) {
//			logger.error("Error : "+e.getCause() + " "+e.getMessage());
//			logger.error("Error in getting the user stocks");
			
			response.setMessage("Error in getting the details");
			response.setStatus("Error");
		}
		
		return response;
	}

}
