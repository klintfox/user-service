package com.bci.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidPasswordFormatExceptionTest {

    @Test
    public void testConstructorAndMessage() {
        String errorMessage = "Invalid password format";

        // Crear la excepción con el mensaje
        InvalidPasswordFormatException exception = new InvalidPasswordFormatException(errorMessage);

        // Verificar que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    public void testExceptionIsRuntimeException() {
        String errorMessage = "Invalid password format";

        // Crear la excepción
        InvalidPasswordFormatException exception = new InvalidPasswordFormatException(errorMessage);

        // Verificar que la excepción es una instancia de RuntimeException
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
