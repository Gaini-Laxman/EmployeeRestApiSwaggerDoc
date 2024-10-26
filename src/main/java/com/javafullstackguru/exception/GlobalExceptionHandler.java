package com.javafullstackguru.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException){
		return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistByException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	
	public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistByException userAlreadyExistException){
		return new ResponseEntity<>(userAlreadyExistException.getMessage(), HttpStatus.CONFLICT);
	}
	
	

}
