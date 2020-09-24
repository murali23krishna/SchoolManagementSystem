package com.murali.school.models;

import java.io.Serializable;
import java.util.Date;

public class CommonDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6551745271314563596L;
	
	
	private String errorMessage;
	private String successMessage;
	private String errorCode;
	private Date date;
	
	public Date getDate() {
		return new Date();
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	
	

}
