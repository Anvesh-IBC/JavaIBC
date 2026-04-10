package com.ibc.training.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("error", "NOT_FOUND");
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	@ExceptionHandler(DuplicateException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateException ex) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("error", "DUPLICATE");
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("error", "VALIDATION_FAILED");
		List<String> errors = new ArrayList<String>();
		int i;
		for (i = 0; i < ex.getBindingResult().getFieldErrors().size(); i++) {
			String field = ex.getBindingResult().getFieldErrors().get(i).getField();
			String msg = ex.getBindingResult().getFieldErrors().get(i).getDefaultMessage();
			errors.add(field + ":" + msg);
		}
		body.put("details", errors);
		return ResponseEntity.badRequest().body(body);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("error", "INTERNAL_ERROR");
		body.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}

}
