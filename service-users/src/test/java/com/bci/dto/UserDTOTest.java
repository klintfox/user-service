package com.bci.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UserDTOTest {

    @Test
    public void testGettersAndSetters() {
        UserDTO dto = new UserDTO();

        UUID id = UUID.randomUUID();
        String name = "Jane Doe";
        String email = "jane.doe@example.com";
        String password = "password456";
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now().minusDays(1);
        String token = "jwt-token-xyz";
        boolean isActive = true;

        dto.setId(id);
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setCreated(created);
        dto.setLastLogin(lastLogin);
        dto.setToken(token);
        dto.setActive(isActive);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getPassword()).isEqualTo(password);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getLastLogin()).isEqualTo(lastLogin);
        assertThat(dto.getToken()).isEqualTo(token);
        assertThat(dto.isActive()).isEqualTo(isActive);
    }

    @Test
    public void testConstructorWithArgs() {
        UUID id = UUID.randomUUID();
        String name = "Jane Doe";
        String email = "jane.doe@example.com";
        String password = "password456";
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now().minusDays(1);
        String token = "jwt-token-xyz";
        boolean isActive = true;

        UserDTO dto = new UserDTO(id, name, email, password, null, created, lastLogin, token, isActive);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getPassword()).isEqualTo(password);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getLastLogin()).isEqualTo(lastLogin);
        assertThat(dto.getToken()).isEqualTo(token);
        assertThat(dto.isActive()).isEqualTo(isActive);
    }

    @Test
    public void testEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        String name = "Jane Doe";
        String email = "jane.doe@example.com";
        String password = "password456";
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now().minusDays(1);
        String token = "jwt-token-xyz";
        boolean isActive = true;

        UserDTO dto1 = new UserDTO(id, name, email, password, null, created, lastLogin, token, isActive);
        UserDTO dto2 = new UserDTO(id, name, email, password, null, created, lastLogin, token, isActive);
        UserDTO dto3 = new UserDTO(UUID.randomUUID(), "John Doe", "john.doe@example.com", "password123", null, created, lastLogin, token, false);

        // Test for equality
        assertThat(dto1).isEqualTo(dto2);  // Should be equal
        assertThat(dto1).isNotEqualTo(dto3);  // Should not be equal

        // Test for hashCode
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }
    
    @Test
    public void testToString() {
        UUID id = UUID.randomUUID();
        String name = "Jane Doe";
        String email = "jane.doe@example.com";
        String password = "password456";
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now().minusDays(1);
        String token = "jwt-token-xyz";
        boolean isActive = true;

        UserDTO dto = new UserDTO(id, name, email, password, null, created, lastLogin, token, isActive);

        // Validate toString output contains relevant fields
        assertThat(dto.toString()).contains("id=" + id.toString());
        assertThat(dto.toString()).contains("name=" + name);
        assertThat(dto.toString()).contains("email=" + email);
        assertThat(dto.toString()).contains("created=" + created.toString());
    }
}
