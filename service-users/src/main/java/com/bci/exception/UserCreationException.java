package com.bci.exception;

public class UserCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserCreationException(String message) {
		super(message);
	}
}
