package com.bci.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bci.dto.PhoneDTO;
import com.bci.dto.UserDTO;
import com.bci.dto.UserResponseDTO;
import com.bci.entity.User;
import com.bci.exception.InvalidEmailFormatException;
import com.bci.exception.InvalidPasswordFormatException;
import com.bci.exception.InvalidTokenException;
import com.bci.exception.UserAlreadyExistsException;
import com.bci.exception.UserNotFoundException;
import com.bci.repository.UserRepository;
import com.bci.security.JwtUtil;
import com.bci.util.Message;
import com.bci.util.PatternConstants;

public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

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
	
//	@Test
//	public void testRegisterUser() {
//	    // Arrange
//	    UserDTO userDTO = new UserDTO();
//	    userDTO.setEmail("newuser@example.com");
//	    userDTO.setPassword("validPassword123");
//	    userDTO.setName("New User");
//	    userDTO.setPhones(Arrays.asList(new PhoneDTO(9876543210L, 1, "1")));
//	    
//	    User user = new User();
//	    user.setId(UUID.randomUUID());
//	    user.setEmail(userDTO.getEmail());
//	    user.setName(userDTO.getName());
//	    user.setPassword("encodedPassword");
//	    user.setIsActive(true);
//	    user.setCreated(LocalDateTime.now());
//	    user.setLastLogin(LocalDateTime.now());
//
//	    when(userRepository.existsByEmail(anyString())).thenReturn(false); // No existe el usuario
//	    when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//	    when(jwtUtil.generateToken(any(User.class))).thenReturn("generatedToken");
//	    when(userRepository.save(any(User.class))).thenReturn(user);
//
//	    // Act
//	    UserResponseDTO response = userService.registerUser(userDTO);
//
//	    // Assert
//	    assertNotNull(response);
//	    assertEquals(user.getId().toString(), response.getId());
//	    assertEquals(user.getName(), response.getName());
//	    assertEquals(user.getEmail(), response.getEmail());
//	    assertTrue(response.getActive());
//	    assertEquals("generatedToken", response.getToken());
//	    assertNotNull(response.getCreated());
//	    assertNotNull(response.getLastLogin());
//	}
	
//	@Test
//	public void testLoginUser_ValidToken() throws Exception {
//	    // Suponemos que el token ya ha sido generado en el test de sign-up y lo reutilizamos.
//	    String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuMUBleGFtcGxlLmNvbSIsInVzZXJJZCI6ImIyODBjZWFlLTMyNGMtNDAwMy1iNWNhLTg1Zjk3ZDI3NGFmYSIsImlhdCI6MTc0NTU1MDg5MCwiZXhwIjoxNzQ1NjM3MjkwfQ.cIBUQuXSsXghj2Fl4KxpJYFIRez7bnh7v8g_wkNr0r4";
//
//	    // Crear un usuario de prueba (mismo usuario usado en el test de sign-up)
//	    UUID userId = UUID.fromString("b280ceae-324c-4003-b5ca-85f97d274afa");  // Id del usuario registrado
//	    User mockUser = new User();
//	    mockUser.setId(userId);
//	    mockUser.setEmail("juan1@example.com");
//	    mockUser.setPassword("Juanjuan12");
//
//	    // Simulamos la recuperación del usuario del repositorio
//	    when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
//
//	    // Llamamos al método loginUser con el token reutilizado
//	    LoginResponseDTO loggedInUser = userService.loginUser(validToken);
//
//	    // Verificamos que el usuario devuelto no sea null
//	    assertNotNull(loggedInUser, "El usuario no debería ser null");
//
//	    // Verificamos que el usuario devuelto tenga los valores correctos
//	    assertEquals("juan1@example.com", loggedInUser.getEmail(), "El email del usuario no es correcto");
//
//	    // Verificamos que el método del repositorio fue llamado con el userId correcto
//	    verify(userRepository).findById(userId);
//	}
	
	// Test para loginUser con token inválido
    @Test
    public void testLoginUser_InvalidToken() {
        // Arrange
        String invalidToken = "Invalid token";

        // Act & Assert
        assertThrows(InvalidTokenException.class, () -> userService.loginUser(invalidToken));
    }

    // Test para loginUser cuando el usuario no existe
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

	
	// Test para createUser (indirectamente cubierto por registerUser)
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
		userDTO.setEmail("juan12@example.com"); // Email con formato válido
		userDTO.setPassword("Juanjuan12");
		userDTO.setName("Test User");

		// Mockear el repositorio para que el correo ya exista
		when(userRepository.existsByEmail("juan12@example.com")).thenReturn(true);

		// Comprobar que se lanza la excepción
//		UserService userService = new UserService();
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
		userDTO.setEmail("invalid-email"); // Email con formato incorrecto
		userDTO.setPassword("validPassword123");
		userDTO.setName("Test User");

		// Comprobar que se lanza la excepción
		InvalidEmailFormatException thrown = assertThrows(InvalidEmailFormatException.class, () -> {
			userService.registerUser(userDTO); // Esto debe lanzar la excepción si el email es inválido
		});

		// Verificar el mensaje de la excepción
		assertEquals(Message.FORMATO_EMAIL_INVALIDO.getMessage(), thrown.getMessage());
	}
	
	@Test
	void testInvalidPasswordFormat() {
		// Preparar datos
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("juan12@example.com"); // Email con formato incorrecto
		userDTO.setPassword("juan12");
		userDTO.setName("Test User");

		// Comprobar que se lanza la excepción
		InvalidPasswordFormatException thrown = assertThrows(InvalidPasswordFormatException.class, () -> {
			userService.registerUser(userDTO); // Esto debe lanzar la excepción si el email es inválido
		});

		// Verificar el mensaje de la excepción
		assertEquals(Message.FORMATO_CLAVE_INVALIDO.getMessage(), thrown.getMessage());
	}
}
