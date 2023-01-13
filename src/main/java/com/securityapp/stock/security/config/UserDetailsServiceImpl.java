package com.securityapp.stock.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.securityapp.stock.dao.Users;
import com.securityapp.stock.repository.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersRepository ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = ur.getUserByEmail(username);
		
		if(user == null || user.equals("")) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		
		return UserDetailsImpl.build(user);
	}

}
