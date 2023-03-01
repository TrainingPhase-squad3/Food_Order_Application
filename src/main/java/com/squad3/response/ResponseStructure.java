package com.squad3.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ResponseStructure {

	private String message;

	private HttpStatus httpStatus;

	public ResponseStructure(String message, HttpStatus httpStatus) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public ResponseStructure() {
		super();

	}

}
