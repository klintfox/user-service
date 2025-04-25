package com.bci.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.bci.util.PatternConstants;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserResponseDTO registerUser(UserDTO userDTO) {
		validateUser(userDTO);
		User user = createUser(userDTO);
		if (user == null) {
	        throw new UserCreationException(Message.USER_CREATION_FAILED.getMessage());
	    }
		userRepository.save(user);
		String token = jwtUtil.generateToken(user);
		if (token == null) {
	        throw new TokenGenerationException(Message.TOKEN_GENERATION_FAILED.getMessage());
	    }
		return createResponse(user, token);
	}

	void validateUser(UserDTO userDTO) {
		if (!PatternConstants.EMAIL_PATTERN.matcher(userDTO.getEmail()).matches()) {
			throw new InvalidEmailFormatException(Message.FORMATO_EMAIL_INVALIDO.getMessage());
		}
		if (!PatternConstants.PASSWORD_PATTERN.matcher(userDTO.getPassword()).matches()) {
			throw new InvalidPasswordFormatException(Message.FORMATO_CLAVE_INVALIDO.getMessage());
		}
		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new UserAlreadyExistsException(Message.USUARIO_YA_REGISTRADO.getMessage());
		}
	}

	User createUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setIsActive(true);
		user.setCreated(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());

		if (userDTO.getPhones() != null && !userDTO.getPhones().isEmpty()) {
			List<Phone> phones = userDTO.getPhones().stream().map(phoneDTO -> {
				Phone phone = new Phone();
				phone.setPhoneNumber(phoneDTO.getNumber());
				phone.setCitycode(phoneDTO.getCitycode());
				phone.setCountrycode(phoneDTO.getContrycode());
				phone.setUser(user);
				return phone;
			}).collect(Collectors.toList());
			user.setPhones(phones);
		}

		return user;
	}

	UserResponseDTO createResponse(User user, String token) {
		UserResponseDTO responseDTO = new UserResponseDTO();
		responseDTO.setId(user.getId().toString());
		responseDTO.setCreated(user.getCreated());
		responseDTO.setLastLogin(user.getLastLogin());
		responseDTO.setToken(token);
		responseDTO.setActive(user.getIsActive());
		return responseDTO;
	}

	public LoginResponseDTO loginUser(String token) {
		if (token == null || !token.startsWith("Bearer ")) {
			throw new InvalidTokenException(Message.TOKEN_INVALIDO.getMessage());
		}
		token = token.substring(7);

		// Extraer el ID del usuario del token JWT
		UUID userId = jwtUtil.extractUserId(token);

		Optional<User> userOptional = userRepository.findById(userId);
		User user = userOptional
				.orElseThrow(() -> new UserNotFoundException(Message.USUARIO_NO_ENCONTRADO.getMessage()));

		String newToken = jwtUtil.generateToken(user);
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);

		// Crear el DTO de respuesta con los campos solicitados
		LoginResponseDTO responseDTO = new LoginResponseDTO();
		responseDTO.setId(user.getId());
		responseDTO.setCreated(user.getCreated());
		responseDTO.setLastLogin(user.getLastLogin());
		responseDTO.setToken(newToken);
		responseDTO.setIsActive(user.getIsActive());
		responseDTO.setName(user.getName());
		responseDTO.setEmail(user.getEmail());
		responseDTO.setPassword(user.getPassword());

		// Mapear los teléfonos de entidades a DTOs para la respuesta
		List<PhoneDTO> phoneDTOs = user.getPhones().stream().map(phone -> {
			PhoneDTO dto = new PhoneDTO();
			dto.setNumber(phone.getPhoneNumber());
			dto.setContrycode(phone.getCountrycode());
			dto.setCitycode(phone.getCitycode());
			return dto;
		}).collect(Collectors.toList());

		responseDTO.setPhones(phoneDTOs);

		return responseDTO;
	}
}
