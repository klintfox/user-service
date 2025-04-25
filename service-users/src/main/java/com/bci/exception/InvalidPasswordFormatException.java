package com.bci.exception;

public class InvalidPasswordFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordFormatException(String message) {
		super(message);
	}
}
