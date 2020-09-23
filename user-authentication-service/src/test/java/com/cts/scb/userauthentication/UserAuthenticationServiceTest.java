package com.cts.scb.userauthentication;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.scb.userauthentication.entity.MyUserDetails;
import com.cts.scb.userauthentication.repository.UserRepository;
import com.cts.scb.userauthentication.service.UserAuthenticationService;

public class UserAuthenticationServiceTest {

	
	  @InjectMocks
	  UserAuthenticationService authService;
	  
	  @Mock 
	  UserRepository userRepository;
	  
	  
	  @Before public void init() { 
		  MockitoAnnotations.initMocks(this); 
		  }
	  
	  
	  public BCryptPasswordEncoder passwordEncoder() { return new
	  BCryptPasswordEncoder(); }
	  
	  @Test
	  public void postive_loadUserByUsername() throws
	  UsernameNotFoundException {
	  
	  MyUserDetails user = new MyUserDetails();
	  
	  user.setUserName("rashmi"); user.setPassword("rash"); user.setRoles("user");
	  user.setUser_id(1);
	  
	  String username = "rashmi";
	  when(userRepository.findByUserName(username)).thenReturn(user);
	  
	  assertEquals(user.getUserName(), username); // return null;
	  
	  }
	  
	  @Test public void negative_loadUserByUsername() throws
	  UsernameNotFoundException {
	  
	  MyUserDetails user = new MyUserDetails();
	  
	  user.setUserName("abc"); user.setPassword("abc"); user.setRoles("user");
	  user.setUser_id(1);
	  
	  String username = "abc";
	  when(userRepository.findByUserName(username)).thenThrow(NullPointerException.
	  class); // return null;
	  
	  }
	  
	 
}