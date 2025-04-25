package com.bci.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PhoneDTOTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        Long number = 12345678L;
        Integer citycode = 1;
        String contrycode = "56";

        PhoneDTO phoneDTO = new PhoneDTO(number, citycode, contrycode);

        assertThat(phoneDTO.getNumber()).isEqualTo(number);
        assertThat(phoneDTO.getCitycode()).isEqualTo(citycode);
        assertThat(phoneDTO.getContrycode()).isEqualTo(contrycode);
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber(98765432L);
        phoneDTO.setCitycode(2);
        phoneDTO.setContrycode("57");

        assertThat(phoneDTO.getNumber()).isEqualTo(98765432L);
        assertThat(phoneDTO.getCitycode()).isEqualTo(2);
        assertThat(phoneDTO.getContrycode()).isEqualTo("57");
    }

    @Test
    public void testEqualsAndHashCode() {
        Long number = 12345678L;
        Integer citycode = 1;
        String contrycode = "56";

        PhoneDTO phoneDTO1 = new PhoneDTO(number, citycode, contrycode);
        PhoneDTO phoneDTO2 = new PhoneDTO(number, citycode, contrycode);
        PhoneDTO phoneDTO3 = new PhoneDTO(98765432L, 2, "57");

        // Test for equality
        assertThat(phoneDTO1).isEqualTo(phoneDTO2);  // Should be equal
        assertThat(phoneDTO1).isNotEqualTo(phoneDTO3);  // Should not be equal

        // Test for hashCode
        assertThat(phoneDTO1.hashCode()).isEqualTo(phoneDTO2.hashCode());
        assertThat(phoneDTO1.hashCode()).isNotEqualTo(phoneDTO3.hashCode());
    }

    @Test
    public void testToString() {
        Long number = 12345678L;
        Integer citycode = 1;
        String contrycode = "56";

        PhoneDTO phoneDTO = new PhoneDTO(number, citycode, contrycode);

        // Validate toString output contains relevant fields
        assertThat(phoneDTO.toString()).contains("number=" + number.toString());
        assertThat(phoneDTO.toString()).contains("citycode=" + citycode.toString());
        assertThat(phoneDTO.toString()).contains("contrycode=" + contrycode);
    }
}
