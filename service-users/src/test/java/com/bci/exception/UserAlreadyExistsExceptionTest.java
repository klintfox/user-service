package com.bci.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAlreadyExistsExceptionTest {

    @Test
    public void testConstructorAndMessage() {
        String errorMessage = "User already exists";

        // Crear la excepción con el mensaje
        UserAlreadyExistsException exception = new UserAlreadyExistsException(errorMessage);

        // Verificar que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    public void testExceptionIsRuntimeException() {
        String errorMessage = "User already exists";

        // Crear la excepción
        UserAlreadyExistsException exception = new UserAlreadyExistsException(errorMessage);

        // Verificar que la excepción es una instancia de RuntimeException
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
