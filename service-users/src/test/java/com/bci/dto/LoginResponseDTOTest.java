package com.bci.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class LoginResponseDTOTest {

    @Test
    public void testGettersAndSetters() {
        LoginResponseDTO dto = new LoginResponseDTO();

        UUID id = UUID.randomUUID();
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now().minusHours(2);
        String token = "jwt-token-abc";
        boolean isActive = true;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "password123";

        dto.setId(id);
        dto.setCreated(created);
        dto.setLastLogin(lastLogin);
        dto.setToken(token);
        dto.setIsActive(isActive);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getLastLogin()).isEqualTo(lastLogin);
        assertThat(dto.getToken()).isEqualTo(token);
        assertThat(dto.getIsActive()).isEqualTo(isActive);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getPassword()).isEqualTo(password);
    }

    @Test
    public void testConstructorWithArgs() {
        UUID id = UUID.randomUUID();
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now().minusHours(2);
        String token = "jwt-token-abc";
        boolean isActive = true;
        String name = "John Doe";
        String email = "john.doe@example.com";
        String password = "password123";

        LoginResponseDTO dto = new LoginResponseDTO(id, created, lastLogin, token, isActive, name, email, password);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getLastLogin()).isEqualTo(lastLogin);
        assertThat(dto.getToken()).isEqualTo(token);
        assertThat(dto.getIsActive()).isEqualTo(isActive);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getPassword()).isEqualTo(password);
    }
}
