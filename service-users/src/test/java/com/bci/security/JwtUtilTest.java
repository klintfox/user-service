package com.bci.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bci.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateTokenAndExtractUserId() {
        // Arrange: Crear un usuario simulado
        User user = new User();
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        user.setEmail("test@example.com");

        // Act: Generar el token y luego extraer el userId
        String token = jwtUtil.generateToken(user);
        UUID extractedId = jwtUtil.extractUserId(token);

        // Assert: Verificar que el ID extraído es el mismo que el original
        assertNotNull(token);
        assertEquals(userId, extractedId);
    }
    
    @Test
    void testExtractUserId_withInvalidToken_shouldThrowException() {
        // Arrange: Crear un token inválido (firma incorrecta)
        String token = Jwts.builder()
                .setSubject("test@example.com")
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256)) // clave aleatoria
                .compact();

        // Act & Assert
        assertThrows(SignatureException.class, () -> {
            jwtUtil.extractUserId(token);
        });
    }
    
    @Test
    void testExtractUserId_tokenWithoutUserIdClaim_shouldThrowException() {
        // Arrange: Crear un token válido pero sin el claim "userId"
        SecretKey key = jwtUtil.getSecretKey(); // Accedemos a la misma clave que usa la clase
        String tokenWithoutUserId = Jwts.builder()
                .setSubject("test@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            jwtUtil.extractUserId(tokenWithoutUserId);
        });
    }
}
