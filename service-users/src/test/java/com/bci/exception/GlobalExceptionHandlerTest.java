package com.bci.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void testHandleValidationErrors() {
        // Crear un error de validación simulado
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("object", "field", "Field is required"));

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Llamar al método handleValidationErrors
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationErrors(exception);

        // Verificar el código de estado y los detalles del error
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getError().get(0).getDetail()).isEqualTo("Field is required");
        assertThat(response.getBody().getError().get(0).getCodigo()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testHandleUserAlreadyExistsException() {
        String message = "User already exists";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleUsuarioExistente(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getError().get(0).getDetail()).isEqualTo(message);
    }

    @Test
    public void testHandleInvalidEmailFormatException() {
        String message = "Invalid email format";
        InvalidEmailFormatException exception = new InvalidEmailFormatException(message);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInvalidadEmialFormat(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getError().get(0).getDetail()).isEqualTo(message);
    }

    @Test
    public void testHandleInvalidPasswordFormatException() {
        String message = "Invalid password format";
        InvalidPasswordFormatException exception = new InvalidPasswordFormatException(message);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleInvalidadEmialFormat(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getError().get(0).getDetail()).isEqualTo(message);
    }

    @Test
    public void testHandleGenericError() {
        String message = "Internal server error";
        Exception exception = new Exception(message);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericError(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getError().get(0).getDetail()).isEqualTo("Error interno del servidor");
    }
}
