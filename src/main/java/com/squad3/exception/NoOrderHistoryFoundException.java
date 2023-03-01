package com.squad3.exception;

public class NoOrderHistoryFoundException extends RuntimeException {
	
	
	
	

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public NoOrderHistoryFoundException(String message) {
			super(message);
		}

		public NoOrderHistoryFoundException() {
			super("No Order History Is Found");
		}

}

