package com.bci.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "created", "last_login", "token", "isActive", "name", "email", "password", "phones" })
public class LoginResponseDTO implements Serializable {

	private static final long serialVersionUID = -6499381132235469726L;

	@JsonProperty("id")
	private UUID id;

	@JsonProperty("created")
	private LocalDateTime created;

	@JsonProperty("last_login")
	private LocalDateTime lastLogin;

	@JsonProperty("token")
	private String token;

	@JsonProperty("isActive")
	private boolean isActive;

	@JsonProperty("phones")
	private List<PhoneDTO> phones;

	@JsonProperty("name")
	private String name;

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	public LoginResponseDTO() {
	}
	
	public LoginResponseDTO(UUID id, LocalDateTime created, LocalDateTime lastLogin, String token, boolean isActive, String name, String email, String password) {
        this.id = id;
        this.created = created;
        this.lastLogin = lastLogin;
        this.token = token;
        this.isActive = isActive;
        this.name = name;
        this.email = email;
        this.password = password;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<PhoneDTO> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneDTO> phones) {
		this.phones = phones;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}