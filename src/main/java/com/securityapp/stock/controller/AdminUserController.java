package com.securityapp.stock.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securityapp.stock.dto.ResponseEntity;
import com.securityapp.stock.service.AdminUserService;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {
	
	Logger logger = LoggerFactory.getLogger(AdminUserController.class.getName());
	
	@Autowired
	private AdminUserService aus;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/user/stocks", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity getUserStocks(@RequestParam("id_user") Long userId, @RequestBody HashMap<String, Object> body)
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
		logger.info("Entering into getUserStocks");
		
		try {
			
			response = aus.getUserStocks(userId, body);
			
		} catch(Exception e) {
//			logger.error("Error : "+e.getCause() + " "+e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in fetching details");
		}
		
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity getUsers()
			throws ClassNotFoundException, NoSuchFieldException, SecurityException {

		ResponseEntity response = new ResponseEntity();
//		logger.info("Entering into getUsers");
		
		try {
			
			response = aus.getUsers();
			
		} catch(Exception e) {
//			logger.error("Error : "+e.getCause() + " "+e.getMessage());
			response.setStatus("Error");
			response.setMessage("Issue in fetching details");
		}
		
		return response;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(path = "/user/stocks/download")
    public org.springframework.http.ResponseEntity<Resource> getUserStocksByCSV(HttpServletResponse servletResponse, @RequestParam ("id") Long userId, @RequestParam ("start") String start, @RequestParam ("end") String end) throws IOException {
        final InputStreamResource resource = new InputStreamResource(aus.writeUserStocksToCsv(userId, start, end));
        String fileName = "user_stocks_"+userId+"_"+start+"_"+end+".csv";
        String file = "attachment; filename=" + fileName;
        return org.springframework.http.ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, file)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }
}
