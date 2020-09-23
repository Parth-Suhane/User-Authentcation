package com.cts.scb.userauthentication;





import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.cts.scb.userauthentication.controller.LoginController;
import com.cts.scb.userauthentication.dto.AuthenticationRequest;
import com.cts.scb.userauthentication.dto.AuthenticationResponse;
import com.cts.scb.userauthentication.entity.MyUserDetails;
import com.cts.scb.userauthentication.repository.UserRepository;
import com.cts.scb.userauthentication.service.UserAuthenticationService;
import com.cts.scb.userauthentication.util.JwtUtil;


public class LoginControllerTest {


     @InjectMocks 
     UserAuthenticationService authService;
      
      @Mock
      UserRepository userRepository;
      
      @Mock
      LoginController controller;
      
      @Mock
      JwtUtil jwtTokenUtil;
      
      protected MockMvc mvc;
      
      @Before
      public void init() { 
          MockitoAnnotations.initMocks(this); 
          }
      
      @Test
      public void login_success_test() throws Exception {
          
          AuthenticationResponse response = new AuthenticationResponse(null);
          AuthenticationRequest request = new AuthenticationRequest();
          request.setUsername("abc");
          request.setPassword("abc");
          
          List<String> roles = new ArrayList<>();


          roles.add("user");
         
          
          response.setUsername("abc");
          response.setRoles(roles);
          response.getJwt();
          
         // 
          UserDetails user = null;
          
        //  ResponseEntity entity = new ResponseEntity();
                
          MockHttpServletRequest request1 = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request1));
            when(controller.createAuthenticationToken(request)).thenReturn(new ResponseEntity<AuthenticationResponse>(response,HttpStatus.OK));

      }
      
      @Test
      public void negative_login_test() throws Exception {
          
          AuthenticationResponse response = new AuthenticationResponse(null);
          AuthenticationRequest request = new AuthenticationRequest();
    
          List<String> roles = new ArrayList<>();


          roles.add("user");
         
          
          response.setUsername("abc");
          response.setRoles(roles);
          response.getJwt();
          
         // 
          UserDetails user = null;
          
        //  ResponseEntity entity = new ResponseEntity();
                
          MockHttpServletRequest request1 = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request1));
            when(controller.createAuthenticationToken(request)).thenReturn(new ResponseEntity<AuthenticationResponse>(response,HttpStatus.BAD_REQUEST));

      }
      
      
}











