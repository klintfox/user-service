package com.bci.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> new ErrorDetail(HttpStatus.BAD_REQUEST.value(), err.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorResponse response = new ErrorResponse();
		response.setError(errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUsuarioExistente(UserAlreadyExistsException ex) {
		ErrorDetail detail = new ErrorDetail(HttpStatus.CONFLICT.value(), ex.getMessage());
		ErrorResponse response = new ErrorResponse();
		response.setError(List.of(detail));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(InvalidEmailFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidadEmialFormat(InvalidEmailFormatException ex) {
		ErrorDetail detail = new ErrorDetail(HttpStatus.CONFLICT.value(), ex.getMessage());
		ErrorResponse response = new ErrorResponse();
		response.setError(List.of(detail));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(InvalidPasswordFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidadEmialFormat(InvalidPasswordFormatException ex) {
		ErrorDetail detail = new ErrorDetail(HttpStatus.CONFLICT.value(), ex.getMessage());
		ErrorResponse response = new ErrorResponse();
		response.setError(List.of(detail));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericError(Exception ex) {
		ErrorDetail detail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor");
		ErrorResponse response = new ErrorResponse();
		response.setError(List.of(detail));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
	
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<ErrorResponse> handleUserCreationException(UserCreationException ex) {
	    ErrorDetail detail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	    ErrorResponse response = new ErrorResponse();
	    response.setError(List.of(detail));
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	@ExceptionHandler(TokenGenerationException.class)
	public ResponseEntity<ErrorResponse> handleTokenGenerationException(TokenGenerationException ex) {
	    ErrorDetail detail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
	    ErrorResponse response = new ErrorResponse();
	    response.setError(List.of(detail));
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

}