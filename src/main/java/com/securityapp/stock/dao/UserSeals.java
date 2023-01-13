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
@Table(name = "user_seal")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSeals {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "id_user_stock")
	private Integer userStockId;
		
	@Column(name = "count")
	private Integer count;
	
	@Column(name = "created_at")
	private String createdAt;
	
	@Column(name = "updated_at")
	private String updatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserStockId() {
		return userStockId;
	}

	public void setUserStockId(Integer userStockId) {
		this.userStockId = userStockId;
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

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_user_stock", insertable = false, updatable = false)
	private UserStock userStock;

	public UserStock getUserStock() {
		return userStock;
	}

	public void setUserStock(UserStock userStock) {
		this.userStock = userStock;
	}

	@Override
	public String toString() {
		return "UserSeals {id: " + id + ", userStockId: " + userStockId + ", count: " + count + ", createdAt: " + createdAt
				+ ", updatedAt: " + updatedAt + "}";
	}
	


}
