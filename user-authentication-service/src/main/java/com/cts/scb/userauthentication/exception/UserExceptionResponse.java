package com.cts.scb.userauthentication.exception;

public class UserExceptionResponse {
	private String message;
	 

	  public UserExceptionResponse (String message) {
	    this.setMessage(message);
	  }


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	  
	 

}
