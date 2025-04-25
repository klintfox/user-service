package com.bci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bci.dto.LoginResponseDTO;
import com.bci.dto.UserDTO;
import com.bci.dto.UserResponseDTO;
import com.bci.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/sign-up")
	public ResponseEntity<Object> signUp(@Valid @RequestBody UserDTO userDTO) {

		UserResponseDTO registeredUser = userService.registerUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestHeader("Authorization") String token) {

		LoginResponseDTO userDTO = userService.loginUser(token);
		return ResponseEntity.ok(userDTO);

	}
}
