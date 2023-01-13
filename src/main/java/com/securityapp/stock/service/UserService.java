package com.securityapp.stock.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securityapp.stock.dao.Users;
import com.securityapp.stock.repository.UsersRepository;

@Service
public class UserService {
	
	@Autowired
	private UsersRepository ur;

	public HashMap<String, Object> getLoggedInUserDetails(HttpServletRequest request, boolean b) {
		
		HashMap<String, Object> response = new HashMap<>();
		
		HttpSession session = ((HttpServletRequest) request).getSession(false);

		if (session != null) {
			HashMap<String, Object> data = new HashMap<>();
			Long userId = (Long) session.getAttribute("UserId");
			Users user = ur.getById(userId);
			
			if (user != null) {
				data.put("id", user.getId());
				data.put("name", user.getId());
				data.put("email", user.getEmail());
				data.put("role", user.getRole().name());
				response.put("user", data);
			}
		}
		
		return response;
	}

}
