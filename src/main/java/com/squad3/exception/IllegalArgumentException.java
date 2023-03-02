package com.squad3.exception;

public class IllegalArgumentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalArgumentException(String message) {
		super(message);
	}

	public IllegalArgumentException() {
		super("Invalid timeFrame");
	}

}
