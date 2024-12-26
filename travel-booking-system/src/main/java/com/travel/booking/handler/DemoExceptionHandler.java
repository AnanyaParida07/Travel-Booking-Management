package com.travel.booking.handler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.travel.booking.exception.NotFoundException;
import com.travel.booking.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class DemoExceptionHandler {

	@SuppressWarnings("unchecked")
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<JSONObject> handleNoProductPresent(NotFoundException ex) {
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();
		response.put("code", "Failed");
		response.put("message", "Record Not Found");
		response.put("details", details);
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);

	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<JSONObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			JSONObject detail = new JSONObject();
			try {
				detail.put(((FieldError) error).getField(), error.getDefaultMessage());
				details.add(detail);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		});
		response.put("code", "VALERRCOD");
		response.put("message", "Validation Failed");
		response.put("details", details);

		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
		return new ResponseEntity<>("HTTP method not supported: " + ex.getMethod(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<JSONObject> handleValidationExceptions(HttpClientErrorException ex) {
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		response.put("code", "VALERRCOD");
		response.put("message", "Incorrect Password");
		response.put("details", details);
		response.put("access_token", "No token");
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<JSONObject> handleUserNotFoundException(UserNotFoundException ex) {
		JSONObject response = new JSONObject();
		JSONArray details = new JSONArray();

		response.put("code", "VALERRCOD");
		response.put("message", "Invalid Username");
		response.put("details", details);
		response.put("access_token", "No token");
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
	}

}
