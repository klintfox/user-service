package com.bci.exception;

import java.util.List;

public class ErrorResponse {

    private List<ErrorDetail> error;

	public List<ErrorDetail> getError() {
        return error;
    }

    public void setError(List<ErrorDetail> error) {
        this.error = error;
    }
}

