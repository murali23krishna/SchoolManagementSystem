package com.murali.school.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="OAUTH_CODE")
public class OAuthCode {

	private String code;
	private Long authentication;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Long authentication) {
		this.authentication = authentication;
	}
	
}
