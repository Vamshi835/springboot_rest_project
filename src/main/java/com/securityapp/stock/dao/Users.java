package com.securityapp.stock.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.securityapp.stock.utils.Role;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String email;
	private String password;
	private Role role;
	
	@Column(name = "created_at")
	private String createdAt;
	
	@Column(name = "updated_at")
	private String updatedAt;
	
	@Column(name = "stocks_count")
	private Integer stocksCount = 0;
	
	@Column(name = "sells_count")
	private Integer sellsCount = 0;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getStocksCount() {
		return stocksCount;
	}

	public void setStocksCount(Integer stocksCount) {
		this.stocksCount = stocksCount;
	}

	public Integer getSellsCount() {
		return sellsCount;
	}

	public void setSellsCount(Integer sellsCount) {
		this.sellsCount = sellsCount;
	}

	@Override
	public String toString() {
		return "{id: " + id + ", name: " + name + ", email: " + email + ", password: " + password + ", role: " + role
				+ ", createdAt: " + createdAt + ", updatedAt: " + updatedAt + ", stocksCount: " + stocksCount
				+ ", sellsCount: " + sellsCount + "}";
	}
	
	

}
