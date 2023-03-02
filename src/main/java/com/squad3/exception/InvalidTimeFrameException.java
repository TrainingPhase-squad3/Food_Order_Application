package com.squad3.exception;

public class InvalidTimeFrameException  extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidTimeFrameException(String message) {
		super(message);
	}

	public InvalidTimeFrameException() {
		super("Invalid TimeFrame");
	}

}
