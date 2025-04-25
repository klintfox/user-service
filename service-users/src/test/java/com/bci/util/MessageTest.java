package com.bci.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MessageTest {

    @Test
    public void testEmailPattern() {
        String validEmail = "test@example.com";
        String invalidEmail = "test@com";

        // Verificar que el patrón de email coincida con un correo válido
        assertThat(PatternConstants.EMAIL_PATTERN.matcher(validEmail).matches()).isTrue();

        // Verificar que el patrón de email no coincida con un correo inválido
        assertThat(PatternConstants.EMAIL_PATTERN.matcher(invalidEmail).matches()).isFalse();
    }

    @Test
    public void testPasswordPattern() {
        String validPassword = "Password123";
        String invalidPassword = "password123";

        // Verificar que el patrón de contraseña coincida con una contraseña válida
        assertThat(PatternConstants.PASSWORD_PATTERN.matcher(validPassword).matches()).isTrue();

        // Verificar que el patrón de contraseña no coincida con una contraseña inválida
        assertThat(PatternConstants.PASSWORD_PATTERN.matcher(invalidPassword).matches()).isFalse();
    }

    @Test
    public void testUsuarioYaRegistrado() {
        // Verificar que la constante USUARIO_YA_REGISTRADO esté correctamente definida
        assertThat(Message.USUARIO_YA_REGISTRADO.getMessage()).isEqualTo(Message.USUARIO_YA_REGISTRADO.getMessage());
    }

    @Test
    public void testFormatoEmailInvalido() {
        // Verificar que la constante FORMATO_EMAIL_INVALIDO esté correctamente definida
        assertThat(Message.FORMATO_EMAIL_INVALIDO.getMessage()).isEqualTo(Message.FORMATO_EMAIL_INVALIDO.getMessage());
    }

    @Test
    public void testFormatoClaveInvalido() {
        // Verificar que la constante FORMATO_CLAVE_INVALIDO esté correctamente definida
        assertThat(Message.FORMATO_CLAVE_INVALIDO).isEqualTo(Message.FORMATO_CLAVE_INVALIDO);
    }

    @Test
    public void testUsuarioNoEncontrado() {
        // Verificar que la constante USUARIO_NO_ENCONTRADO esté correctamente definida
        assertThat(Message.USUARIO_NO_ENCONTRADO.getMessage()).isEqualTo(Message.USUARIO_NO_ENCONTRADO.getMessage());
    }

    @Test
    public void testTokenInvalido() {
        // Verificar que la constante TOKEN_INVALIDO esté correctamente definida
        assertThat(Message.TOKEN_INVALIDO.getMessage()).isEqualTo("Token no proporcionado o inválido.");
    }
    
}
