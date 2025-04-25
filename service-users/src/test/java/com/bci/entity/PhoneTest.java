package com.bci.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void testGettersAndSetters() {
        Long id = 1L;
        long phoneNumber = 12345678L;
        Integer citycode = 1;
        String countrycode = "56";

        User user = new User();
        user.setName("Klint");
        user.setEmail("klint@example.com");

        Phone phone = new Phone();
        phone.setId(id);
        phone.setPhoneNumber(phoneNumber);
        phone.setCitycode(citycode);
        phone.setCountrycode(countrycode);
        phone.setUser(user);

        assertThat(phone.getId()).isEqualTo(id);
        assertThat(phone.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(phone.getCitycode()).isEqualTo(citycode);
        assertThat(phone.getCountrycode()).isEqualTo(countrycode);
        assertThat(phone.getUser()).isEqualTo(user);
    }
}
