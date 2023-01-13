package com.securityapp.stock.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_stock")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_stock")
	private Integer stockId;
	
	@Column(name = "id_user")
	private Long userId;
	
	@Column(name = "count")
	private Integer count;
	
	@Column(name = "created_at")
	private String createdAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStockId() {
		return stockId;
	}

	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_stock", insertable = false, updatable = false)
	private Stock stock;

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_user", insertable = false, updatable = false)
	private Users user;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UserStock {id: " + id + ", stockId: " + stockId + ", userId: " + userId + ", count: " + count
				+ ", createdAt: " + createdAt + "}";
	}

	

	

}
