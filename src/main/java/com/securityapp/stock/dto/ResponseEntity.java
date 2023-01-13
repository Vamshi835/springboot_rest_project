package com.securityapp.stock.dto;

import java.util.HashMap;


public class ResponseEntity {
	
	private HashMap<String, Object> content;
	
	private String message;
	private String status;
	
	public ResponseEntity() {
	}
	
	public ResponseEntity(HashMap<String, Object> content, String status, String message) {
		super();
		
		this.message = message;
		this.content = content;
		this.status = status;
	}

	public Object getContent() {
		return content;
	}
	
	public ResponseEntity setContent(HashMap<String, Object> content) {
		this.content = content;
		return this;
	}
	
	public String getStatus() {
		return status;
	}

	public ResponseEntity setStatus(String status) {
		this.status = status;
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ResponseEntity setMessage(String message) {
		this.message = message;
		return this;
	}
}
