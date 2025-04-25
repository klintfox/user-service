package com.bci.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bci.dto.LoginResponseDTO;
import com.bci.dto.PhoneDTO;
import com.bci.dto.UserDTO;
import com.bci.dto.UserResponseDTO;
import com.bci.repository.UserRepository;
import com.bci.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private UserController userController;

	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void testSignUp_Success() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setName(""); // As provided
		userDTO.setEmail("juan1@example.com");
		userDTO.setPassword("Juanjuan12");
		userDTO.setPhones(Arrays.asList(new PhoneDTO(12334567L, 2, "52"))); // Assuming PhoneDTO exists

		// Create a mock UserResponseDTO object that will be returned by the service
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setId("b280ceae-324c-4003-b5ca-85f97d274afa");
		userResponseDTO.setToken(
				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuMUBleGFtcGxlLmNvbSIsInVzZXJJZCI6ImIyODBjZWFlLTMyNGMtNDAwMy1iNWNhLTg1Zjk3ZDI3NGFmYSIsImlhdCI6MTc0NTU1MDg5MCwiZXhwIjoxNzQ1NjM3MjkwfQ.cIBUQuXSsXghj2Fl4KxpJYFIRez7bnh7v8g_wkNr0r4");
		userResponseDTO.setActive(true);

		// Mock the behavior of the userService
		when(userService.registerUser(userDTO)).thenReturn(userResponseDTO);

		// Perform the POST request and check the response
		mockMvc.perform(post("/api/usuarios/sign-up").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "  \"name\": \"\",\n" + "  \"email\": \"juan1@example.com\",\n"
						+ "  \"password\": \"Juanjuan12\",\n" + "  \"phones\": [\n" + "    {\n"
						+ "      \"number\": 12334567,\n" + "      \"citycode\": 2,\n"
						+ "      \"contrycode\": \"52\"\n" + "    }\n" + "  ]\n" + "}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("b280ceae-324c-4003-b5ca-85f97d274afa"))
				.andExpect(jsonPath("$.token").value(
						"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuMUBleGFtcGxlLmNvbSIsInVzZXJJZCI6ImIyODBjZWFlLTMyNGMtNDAwMy1iNWNhLTg1Zjk3ZDI3NGFmYSIsImlhdCI6MTc0NTU1MDg5MCwiZXhwIjoxNzQ1NjM3MjkwfQ.cIBUQuXSsXghj2Fl4KxpJYFIRez7bnh7v8g_wkNr0r4"))
				.andExpect(jsonPath("$.active").value(true));

		// Verify that the registerUser method was called once
		verify(userService, times(1)).registerUser(userDTO);
	}

	@Test
	public void testLogin() throws Exception {
		// Crear un token de ejemplo (este token puede ser un valor simulado)
		String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuMUBleGFtcGxlLmNvbSIsInVzZXJJZCI6ImU3NzE4YzY5LWY2MmEtNDZiZC05NzhjLTJkN2E3ZDZhYjFjOCIsImlhdCI6MTc0NTU1MDU0OSwiZXhwIjoxNzQ1NjM2OTQ5fQ.-_0eJqUlAtNBqwka0tkt2lN2_-cwY_xacyfAcmNXU7Q";

		// Crear el ResponseDTO simulado que se retornará
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		UUID mockId = UUID.fromString("e7718c69-f62a-46bd-978c-2d7a7d6ab1c8");
		responseDTO.setId(mockId);
		responseDTO.setToken(token);
		responseDTO.setIsActive(true);
		responseDTO.setName("");
		responseDTO.setEmail("juan1@example.com");
		responseDTO.setPassword("$2a$10$9Faal6mYDl0ZdRtvrBppq.e2Eg9Nq59I4n9FVLfc1uRPA/QRjrMPq");
		responseDTO.setPhones(Arrays.asList(new PhoneDTO(12334567L, 2, "52")));

		// Simula que el servicio loginUser retorna el ResponseDTO cuando el token es
		// válido
		when(userService.loginUser(token)).thenReturn(responseDTO);

		// Realiza la solicitud POST al endpoint de login
		mockMvc.perform(post("/api/usuarios/login").header("Authorization", token)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(mockId.toString())).andExpect(jsonPath("$.token").value(token))
				.andExpect(jsonPath("$.isActive").value(true)).andExpect(jsonPath("$.name").value(""))
				.andExpect(jsonPath("$.email").value("juan1@example.com"))
				.andExpect(jsonPath("$.phones[0].number").value(12334567))
				.andExpect(jsonPath("$.phones[0].citycode").value(2))
				.andExpect(jsonPath("$.phones[0].contrycode").value("52"));

		// Verifica que el método loginUser fue llamado una vez
		verify(userService, times(1)).loginUser(token);
	}

}