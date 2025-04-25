package com.bci.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserNotFoundExceptionTest {

    @Test
    public void testConstructorAndMessage() {
        String errorMessage = "User not found";

        // Crear la excepción con el mensaje
        UserNotFoundException exception = new UserNotFoundException(errorMessage);

        // Verificar que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    public void testExceptionIsRuntimeException() {
        String errorMessage = "User not found";

        // Crear la excepción
        UserNotFoundException exception = new UserNotFoundException(errorMessage);

        // Verificar que la excepción es una instancia de RuntimeException
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
