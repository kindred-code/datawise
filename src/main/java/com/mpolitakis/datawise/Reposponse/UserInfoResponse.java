package com.mpolitakis.datawise.Reposponse;


import java.util.List;

import com.mpolitakis.datawise.Models.RefreshToken;

public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private String token;
	private RefreshToken refreshToken;


	public UserInfoResponse(Long id, String username, String email, String accessToken, RefreshToken refreshToken, List<String> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.token = accessToken;
		this.refreshToken = refreshToken;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}
}
