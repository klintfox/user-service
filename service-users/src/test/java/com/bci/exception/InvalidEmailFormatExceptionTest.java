package com.bci.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidEmailFormatExceptionTest {

    @Test
    public void testConstructorAndMessage() {
        String errorMessage = "Invalid email format";

        // Crear la excepción con el mensaje
        InvalidEmailFormatException exception = new InvalidEmailFormatException(errorMessage);

        // Verificar que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    public void testExceptionIsRuntimeException() {
        String errorMessage = "Invalid email format";

        // Crear la excepción
        InvalidEmailFormatException exception = new InvalidEmailFormatException(errorMessage);

        // Verificar que la excepción es una instancia de RuntimeException
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}
