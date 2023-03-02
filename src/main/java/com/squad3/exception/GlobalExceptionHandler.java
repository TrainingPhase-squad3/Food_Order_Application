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

import com.squad3.response.ApiResponse;

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
<<<<<<< HEAD

	@ExceptionHandler(value = NoSearchResultFoundException.class)
	public ResponseEntity<ApiResponse> noSearchResultException(NoSearchResultFoundException exception) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND));

	}

=======
	

	@ExceptionHandler(UserIdNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(UserIdNotFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse(exception.getMessage(), HttpStatus.NOT_FOUND));

	}
	@ExceptionHandler(NoOrderHistoryFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(NoOrderHistoryFoundException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse(exception.getMessage(), HttpStatus.NOT_FOUND));

	}
	@ExceptionHandler(InvalidTimeFrameException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(InvalidTimeFrameException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse(exception.getMessage(), HttpStatus.NOT_FOUND));

	}
	
	
	
>>>>>>> 513aa71789a872ac72fa7aed94c6df0af36ddeea
}
