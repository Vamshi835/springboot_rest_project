package com.securityapp.stock.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securityapp.stock.dao.Stock;
import com.securityapp.stock.dto.ResponseEntity;
import com.securityapp.stock.service.StrokeManagementService;


@RestController
@RequestMapping("/api/stock")
public class StrokeManagementController {

//	Logger logger = LogManager.getLogger(StrokeManagementController.class.getName());
	
	@Autowired
	StrokeManagementService stockService;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity save(@RequestBody Stock stock)
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		
		try {
			response = stockService.saveStockDetails(stock);
		} catch (Exception e) {
//			logger.error(e.getCause() + " " +e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in saving details");
		}
		
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity get(@RequestParam ("id") Integer id)
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		
		try {
			response = stockService.getStockDetails(id);
		} catch (Exception e) {
//			logger.error(e.getCause() + " " +e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in getting details");
		}
		
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/all", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity getAll()
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		
		try {
			response = stockService.getAllStockDetails();
		} catch (Exception e) {
//			logger.error(e.getCause() + " " +e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in getting details");
		}
		
		return response;
	}

}
