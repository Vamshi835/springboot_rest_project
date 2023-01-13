package com.securityapp.stock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securityapp.stock.security.config.UserDetailsImpl;
import com.securityapp.stock.security.jwt.JwtUtils;
import com.securityapp.stock.utils.JwtResponse;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
		
	@PostMapping(value="/user", produces = "application/json")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> Users() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("data", "Hello User!!!");
		return ResponseEntity.ok(data);
	}
	
	@PostMapping(value="/logout", produces = "application/json")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> logOut() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("staus", "SUCCESS");
		return ResponseEntity.ok(data);
	}
	
	@PostMapping(value="/admin", produces = "application/json")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> AdminUsers() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("data", "Hello Admin!!!");
		return ResponseEntity.ok(data);
	}
	
	
	@PostMapping(value="/login", produces = "application/json")
	@CrossOrigin(origins = "*")
	public ResponseEntity<?> authenticateUser(@RequestBody HashMap<String, Object> loginRequest) {
		
//		Map<String, Object> loginRequest = new HashMap<>();
		logger.info("loginRequest - {} ", loginRequest);
		System.out.println("loginRequest -  "+ loginRequest);
		HashMap<String, Object> response = new HashMap<>();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.get("username"), loginRequest.get("password")));
			
			if (authentication != null && !authentication.equals("") && authentication.getAuthorities() != null
					&& authentication.getAuthorities().size() > 0) {
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
				List<String> roles = userDetails.getAuthorities().stream()
						.map(item -> item.getAuthority())
						.collect(Collectors.toList());
				
				logger.info("userDetails - {} ", userDetails);
				logger.info("roles - {} ", roles);
				
				return ResponseEntity.ok(new JwtResponse(jwt, 
						 userDetails.getId(), 
						 userDetails.getUsername(), 
						 userDetails.getUsername(), 
						 roles, "SUCCESS"));
			} else {
				response.put("status", "ERROR");
			}

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			logger.error("Error - {} ",e.getMessage());
			HashMap<String, Object> error = new HashMap<>();
			error.put("error", e.getMessage());
			error.put("status", "ERROR");
			return ResponseEntity.ok(error);
		}
	}
}
