package com.bci.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseTest {

    @Test
    public void testGettersAndSetters() {
        // Crear algunos objetos ErrorDetail
        ErrorDetail errorDetail1 = new ErrorDetail(404, "Not Found");
        ErrorDetail errorDetail2 = new ErrorDetail(500, "Internal Server Error");

        // Crear la lista de errores
        List<ErrorDetail> errorDetails = Arrays.asList(errorDetail1, errorDetail2);

        // Crear la instancia de ErrorResponse y asignar la lista de errores
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(errorDetails);

        // Verificar que los métodos getter y setter funcionan correctamente
        assertThat(errorResponse.getError()).isEqualTo(errorDetails);
        assertThat(errorResponse.getError().size()).isEqualTo(2);
        assertThat(errorResponse.getError().get(0).getCodigo()).isEqualTo(404);
        assertThat(errorResponse.getError().get(1).getCodigo()).isEqualTo(500);
    }

    @Test
    public void testSetErrorNull() {
        // Crear una instancia de ErrorResponse con null en la lista de errores
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError(null);

        // Verificar que el valor de error es null
        assertThat(errorResponse.getError()).isNull();
    }
}
