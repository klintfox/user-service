package com.bci.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserResponseDTOTest {

    @Test
    public void testGettersAndSetters() {
        UserResponseDTO dto = new UserResponseDTO();

        String id = "uuid-123";
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now().minusHours(1);
        String token = "jwt-token-xyz";
        boolean isActive = true;

        dto.setId(id);
        dto.setCreated(created);
        dto.setLastLogin(lastLogin);
        dto.setToken(token);
        dto.setActive(isActive);

        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getCreated()).isEqualTo(created);
        assertThat(dto.getLastLogin()).isEqualTo(lastLogin);
        assertThat(dto.getToken()).isEqualTo(token);
        assertThat(dto.isActive()).isEqualTo(isActive);
    }
}
