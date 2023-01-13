package com.securityapp.stock.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.securityapp.stock.security.jwt.AuthEntryPointJwt;
import com.securityapp.stock.security.jwt.AuthTokenFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImpl us;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	 @Override
	    protected void configure(HttpSecurity http) 
	      throws Exception {
		 http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/login").permitAll()
			.antMatchers("/api/logout", "/api/stock/all", "/api/stock", "/api/user/stock", "/api/user/stock/save", "/api/user/stock/*", "api/user/stock/all").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
			.antMatchers("/api/admin/**", "/api/stock/save", "/api/admin/user/stocks", "/api/admin/user/stocks/download").hasAnyAuthority("ROLE_ADMIN")
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	          
	    }
	 
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(us).passwordEncoder(passwordEncoder());
	}

	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

}
