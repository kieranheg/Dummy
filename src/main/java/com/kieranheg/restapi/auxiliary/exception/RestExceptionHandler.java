package com.kieranheg.restapi.auxiliary.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@RequestMapping(produces = "application/json")
@ResponseBody
public class RestExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<String> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		return new ResponseEntity<>(ex.getMessage(), BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> defaultExceptionHandler(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), INTERNAL_SERVER_ERROR);
	}
}
