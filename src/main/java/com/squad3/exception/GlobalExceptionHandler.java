package com.squad3.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	@ExceptionHandler(RequestedQuantityNotAvailableException.class)
	public ResponseEntity<String> handleRequestedQuantityNotAvailableException(RequestedQuantityNotAvailableException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(exception.getMessage());

	}
	@ExceptionHandler(FoodItemNotFoundException.class)
	public ResponseEntity<String> handleFoodItemNotFoundException(FoodItemNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(exception.getMessage());

	}
	@ExceptionHandler(VendorNotFoundException.class)
	public ResponseEntity<String> handleVendorNotFoundException(VendorNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(exception.getMessage());

	}
}
