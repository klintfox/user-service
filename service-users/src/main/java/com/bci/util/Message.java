package com.bci.util;

public enum Message {

	USUARIO_YA_REGISTRADO("Usuario ya registrado."),
	FORMATO_EMAIL_INVALIDO("El formato del correo electrónico no es válido. Formato correcto aaaaaaa@undominio.algo"),
	FORMATO_CLAVE_INVALIDO(
			"La clave no cumple con el formato requerido. Debe tener al menos una mayúscula, exactamente dos números y entre 8 y 12 caracteres."),
	USUARIO_NO_ENCONTRADO("Usuario no encontrado."), TOKEN_INVALIDO("Token no proporcionado o inválido.");

	private final String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
