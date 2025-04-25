package com.bci.exception;

import java.time.LocalDateTime;

public class ErrorDetail {

	private LocalDateTime timestamp;
	private int codigo;
	private String detail;

	public ErrorDetail(int codigo, String detail) {
		this.timestamp = LocalDateTime.now();
		this.codigo = codigo;
		this.detail = detail;
	}

	public ErrorDetail(String detail) {
		this.timestamp = LocalDateTime.now();
		this.codigo = 400;
		this.detail = detail;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
