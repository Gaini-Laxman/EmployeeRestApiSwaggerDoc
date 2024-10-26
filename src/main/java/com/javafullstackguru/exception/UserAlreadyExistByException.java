package com.javafullstackguru.exception;

public class UserAlreadyExistByException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAlreadyExistByException (String message) {
		super(message);
	}
}
