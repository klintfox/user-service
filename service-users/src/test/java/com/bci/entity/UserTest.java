package com.bci.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        String name = "Klint";
        String email = "klint@example.com";
        String password = "secure123";
        boolean isActive = true;
        LocalDateTime created = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now();

        Phone phone1 = new Phone();
        phone1.setPhoneNumber(12345678L);
        Phone phone2 = new Phone();
        phone2.setPhoneNumber(987654321L);
        List<Phone> phones = Arrays.asList(phone1, phone2);

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setIsActive(isActive);
        user.setCreated(created);
        user.setLastLogin(lastLogin);
        user.setPhones(phones);

        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getIsActive()).isEqualTo(isActive);
        assertThat(user.getCreated()).isEqualTo(created);
        assertThat(user.getLastLogin()).isEqualTo(lastLogin);
        assertThat(user.getPhones()).containsExactly(phone1, phone2);
    }
}
