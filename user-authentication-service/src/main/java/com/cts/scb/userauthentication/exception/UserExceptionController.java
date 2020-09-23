package com.cts.scb.userauthentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.scb.userauthentication.dto.AuthenticationResponse;

@ControllerAdvice
public class UserExceptionController extends ResponseEntityExceptionHandler  {
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(UserNullException.class)
	  public final ResponseEntity<Object> handleAllExceptions(UserNullException ex) {
		 
		
		 AuthenticationResponse exceptionResponse = new AuthenticationResponse(
		  ex.getMessage());
		 
	    return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
	    
	  }
}
