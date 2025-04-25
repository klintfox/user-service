package com.bci.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorDetailTest {

    @Test
    public void testConstructorWithCodigoAndDetail() {
        int codigo = 404;
        String detail = "Not Found";

        ErrorDetail errorDetail = new ErrorDetail(codigo, detail);

        assertThat(errorDetail.getCodigo()).isEqualTo(codigo);
        assertThat(errorDetail.getDetail()).isEqualTo(detail);
        assertThat(errorDetail.getTimestamp()).isNotNull();
    }

    @Test
    public void testConstructorWithDetailOnly() {
        String detail = "Bad Request";

        ErrorDetail errorDetail = new ErrorDetail(detail);

        // Default codigo should be 400
        assertThat(errorDetail.getCodigo()).isEqualTo(400);
        assertThat(errorDetail.getDetail()).isEqualTo(detail);
        assertThat(errorDetail.getTimestamp()).isNotNull();
    }

    @Test
    public void testGettersAndSetters() {
        ErrorDetail errorDetail = new ErrorDetail(404, "Not Found");

        // Test setters and getters
        errorDetail.setCodigo(500);
        errorDetail.setDetail("Internal Server Error");
        LocalDateTime newTimestamp = LocalDateTime.now().plusHours(1);
        errorDetail.setTimestamp(newTimestamp);

        assertThat(errorDetail.getCodigo()).isEqualTo(500);
        assertThat(errorDetail.getDetail()).isEqualTo("Internal Server Error");
        assertThat(errorDetail.getTimestamp()).isEqualTo(newTimestamp);
    }
}
