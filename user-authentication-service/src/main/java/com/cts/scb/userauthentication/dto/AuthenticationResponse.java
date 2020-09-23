package com.cts.scb.userauthentication.dto;

import java.io.Serializable;
import java.util.List;

public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 345L;
	
    private final String jwt;
	private String username;
	private String message;
    private List<String> roles;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
