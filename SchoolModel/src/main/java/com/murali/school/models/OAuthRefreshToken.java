package com.murali.school.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="OAUTH_REFRESH_TOKEN")
public class OAuthRefreshToken {

	private String token_id;
	private Long token;
	private Long authentication;
	public String getToken_id() {
		return token_id;
	}
	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}
	public Long getToken() {
		return token;
	}
	public void setToken(Long token) {
		this.token = token;
	}
	public Long getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Long authentication) {
		this.authentication = authentication;
	}
	
	
}
