package com.murali.school.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="OAUTH_CLIENT_TOKEN")
public class OAuthClientToken {

	private String token_id;
	private Long token;
	private String authentication_id;
	private String user_name;
	private String client_id;
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
	public String getAuthentication_id() {
		return authentication_id;
	}
	public void setAuthentication_id(String authentication_id) {
		this.authentication_id = authentication_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
}
