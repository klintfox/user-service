package com.bci.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bci.dto.LoginResponseDTO;
import com.bci.dto.PhoneDTO;
import com.bci.dto.UserDTO;
import com.bci.dto.UserResponseDTO;
import com.bci.entity.Phone;
import com.bci.entity.User;
import com.bci.exception.InvalidEmailFormatException;
import com.bci.exception.InvalidPasswordFormatException;
import com.bci.exception.InvalidTokenException;
import com.bci.exception.TokenGenerationException;
import com.bci.exception.UserAlreadyExistsException;
import com.bci.exception.UserCreationException;
import com.bci.exception.UserNotFoundException;
import com.bci.repository.UserRepository;
import com.bci.security.JwtUtil;
import com.bci.util.Message;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Spy
	@InjectMocks
	private UserService userService;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	private UserDTO userDTO;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		userDTO = new UserDTO();
		userDTO.setEmail("test@example.com");
		userDTO.setPassword("Password123");
		userDTO.setName("Test User");
		userDTO.setPhones(Arrays.asList(new PhoneDTO(1234567890L, 1, "1")));
	}

	@Test
	void loginUser_shouldReturnLoginResponse_whenTokenIsValid() {
		String bearerToken = "Bearer abc.def.ghi";
		String rawToken = "abc.def.ghi";
		UUID userId = UUID.randomUUID();

		User user = new User();
		user.setId(userId);
		user.setName("Jane Doe");
		user.setEmail("jane@example.com");
		user.setPassword("securePass123");
		user.setCreated(LocalDateTime.of(2023, 1, 1, 12, 0));
		user.setIsActive(true);

		Phone phone = new Phone();
		phone.setPhoneNumber(123456789L);
		phone.setCitycode(1);
		phone.setCountrycode("57");

		user.setPhones(List.of(phone));

		String newGeneratedToken = "new.jwt.token";

		// Mocks
		when(jwtUtil.extractUserId(rawToken)).thenReturn(userId);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(jwtUtil.generateToken(user)).thenReturn(newGeneratedToken);
		when(userRepository.save(any(User.class))).thenReturn(user);

		LoginResponseDTO response = userService.loginUser(bearerToken);

		assertNotNull(response);
		assertEquals(userId, response.getId());
		assertEquals(user.getCreated(), response.getCreated());
		assertNotNull(response.getLastLogin());
		assertEquals(newGeneratedToken, response.getToken());
		assertTrue(response.getIsActive());
		assertEquals(user.getName(), response.getName());
		assertEquals(user.getEmail(), response.getEmail());
		assertEquals(user.getPassword(), response.getPassword());
		assertEquals(1, response.getPhones().size());
		assertEquals(phone.getPhoneNumber(), response.getPhones().get(0).getNumber());
		assertEquals(phone.getCitycode(), response.getPhones().get(0).getCitycode());
		assertEquals(phone.getCountrycode(), response.getPhones().get(0).getContrycode());
	}

	@Test
	public void testCreateUser_WithPhones() {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("John");
		userDTO.setEmail("john@example.com");
		userDTO.setPassword("password123");

		PhoneDTO phoneDTO = new PhoneDTO();
		phoneDTO.setNumber(123456789L);
		phoneDTO.setCitycode(1);
		phoneDTO.setContrycode("54");
		userDTO.setPhones(Collections.singletonList(phoneDTO));

		User user = userService.createUser(userDTO);

		// Assert
		assertNotNull(user);
		assertEquals(userDTO.getName(), user.getName());
		assertEquals(userDTO.getEmail(), user.getEmail());
		assertTrue(user.getIsActive());
		assertNotNull(user.getCreated());
		assertNotNull(user.getLastLogin());

		assertNotNull(user.getPhones());
		assertEquals(1, user.getPhones().size());

		Phone phone = user.getPhones().get(0);
		assertEquals(phoneDTO.getNumber(), phone.getPhoneNumber());
		assertEquals(phoneDTO.getCitycode(), phone.getCitycode());
		assertEquals(phoneDTO.getContrycode(), phone.getCountrycode());
		assertEquals(user, phone.getUser());
	}

	@Test
	public void testLoginUser_InvalidToken() {
		// Arrange
		String invalidToken = "Invalid token";

		// Act & Assert
		assertThrows(InvalidTokenException.class, () -> userService.loginUser(invalidToken));
	}

	@Test
	public void testLoginUser_UserNotFound() {
		// Arrange
		String token = "Bearer fakeToken";
		UUID userId = UUID.randomUUID();

		when(jwtUtil.extractUserId(anyString())).thenReturn(userId);
		when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userService.loginUser(token));
	}

	@Test
	public void testCreateUser() {
		UserDTO userDTO = new UserDTO();
		userDTO.setName("John");
		userDTO.setEmail("john@example.com");
		userDTO.setPassword("password123");

		User user = userService.createUser(userDTO);

		assertNotNull(user);
		assertEquals(userDTO.getName(), user.getName());
		assertEquals(userDTO.getEmail(), user.getEmail());
		assertTrue(user.getIsActive());
		assertNotNull(user.getCreated());
		assertNotNull(user.getLastLogin());
	}

	// Test para createResponse (indirectamente cubierto por registerUser)
	@Test
	public void testCreateResponse() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("John");
		user.setEmail("john@example.com");
		user.setIsActive(true);
		user.setCreated(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());

		String token = "fakeToken";
		UserResponseDTO response = userService.createResponse(user, token);

		assertNotNull(response);
		assertEquals(user.getId().toString(), response.getId());
		assertEquals(user.getCreated(), response.getCreated());
		assertEquals(user.getLastLogin(), response.getLastLogin());
		assertEquals(token, response.getToken());
	}

	@Test
	void registerUser_shouldThrowUserCreationException_whenUserIsNull() {
		// Arrange
		UserDTO userDTO = new UserDTO();
		userDTO.setName("John");
		userDTO.setEmail("john@example.com");
		userDTO.setPassword("ValidPass123");

		// Mock: createUser devuelve null
		doReturn(null).when(userService).createUser(userDTO);

		// Act & Assert
		UserCreationException exception = assertThrows(UserCreationException.class, () -> {
			userService.registerUser(userDTO);
		});

		assertEquals(Message.USER_CREATION_FAILED.getMessage(), exception.getMessage());
	}

	@Test
	void registerUser_shouldThrowTokenGenerationException_whenTokenIsNull() {
		// Arrange
		UserDTO userDTO = new UserDTO();
		userDTO.setName("Jane");
		userDTO.setEmail("jane@example.com");
		userDTO.setPassword("ValidPass456");

		User fakeUser = new User();
		fakeUser.setEmail(userDTO.getEmail());
		fakeUser.setPassword(userDTO.getPassword());
		fakeUser.setName(userDTO.getName());

		// No-op para validateUser
		doNothing().when(userService).validateUser(userDTO);

		// Simula que se crea un usuario válido
		doReturn(fakeUser).when(userService).createUser(userDTO);

		// Simula guardado en la BD
		when(userRepository.save(any(User.class))).thenReturn(fakeUser);

		// Simula fallo en generación de token
		when(jwtUtil.generateToken(any(User.class))).thenReturn(null);

		// Act & Assert
		TokenGenerationException exception = assertThrows(TokenGenerationException.class,
				() -> userService.registerUser(userDTO));

		assertEquals(Message.TOKEN_GENERATION_FAILED.getMessage(), exception.getMessage());
	}

	@Test
	public void testRegisterUser_InvalidEmailFormat() {
		// Arrange
		userDTO.setEmail("invalid-email"); // Invalid email format

		// Act & Assert
		assertThrows(InvalidEmailFormatException.class, () -> userService.registerUser(userDTO));
	}

	@Test
	void testRegisterUser_UserAlreadyExists() {
		// Preparar datos
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("juan12@example.com");
		userDTO.setPassword("Juanjuan12");
		userDTO.setName("Test User");

		// Mockear el repositorio para que el correo ya exista
		when(userRepository.existsByEmail("juan12@example.com")).thenReturn(true);

		// Comprobar que se lanza la excepción
		UserAlreadyExistsException thrown = assertThrows(UserAlreadyExistsException.class, () -> {
			userService.registerUser(userDTO);
		});

		// Verificar el mensaje de la excepción
		assertEquals(Message.USUARIO_YA_REGISTRADO.getMessage(), thrown.getMessage());
	}

	@Test
	void testInvalidEmailFormat() {
		// Preparar datos
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("invalid-email");
		userDTO.setPassword("validPassword123");
		userDTO.setName("Test User");

		// Comprobar que se lanza la excepción
		InvalidEmailFormatException thrown = assertThrows(InvalidEmailFormatException.class, () -> {
			userService.registerUser(userDTO);
		});

		// Verificar el mensaje de la excepción
		assertEquals(Message.FORMATO_EMAIL_INVALIDO.getMessage(), thrown.getMessage());
	}

	@Test
	void testInvalidPasswordFormat() {
		// Preparar datos
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("juan12@example.com");
		userDTO.setPassword("juan12");
		userDTO.setName("Test User");

		// Comprobar que se lanza la excepción
		InvalidPasswordFormatException thrown = assertThrows(InvalidPasswordFormatException.class, () -> {
			userService.registerUser(userDTO);
		});

		// Verificar el mensaje de la excepción
		assertEquals(Message.FORMATO_CLAVE_INVALIDO.getMessage(), thrown.getMessage());
	}
}
