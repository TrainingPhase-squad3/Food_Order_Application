package com.squad3.exception;

public class NoSearchResultFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSearchResultFoundException(String message) {
		super(message);
	}

	public NoSearchResultFoundException() {
		super();
	}

}
