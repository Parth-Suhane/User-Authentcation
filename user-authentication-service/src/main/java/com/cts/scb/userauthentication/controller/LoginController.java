package com.cts.scb.userauthentication.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.scb.userauthentication.configuration.SecurityConfiguration;
import com.cts.scb.userauthentication.dto.AuthenticationRequest;
import com.cts.scb.userauthentication.dto.AuthenticationResponse;
import com.cts.scb.userauthentication.dto.LoginDetails;
import com.cts.scb.userauthentication.exception.UserNullException;
import com.cts.scb.userauthentication.service.UserAuthenticationService;
import com.cts.scb.userauthentication.util.JwtUtil;


@Validated
@Import(SecurityConfiguration.class)
@RestController
@CrossOrigin
@RequestMapping("/loan-api")
@EnableFeignClients
public class LoginController {

	
	 
	 @Autowired
	 UserAuthenticationService userDetailsService;
	 
	 @Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private JwtUtil jwtTokenUtil;
		
		Logger logger = LoggerFactory.getLogger(LoginController.class);


	 @PostMapping(value = "/login")
		
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@Valid 
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();
		AuthenticationResponse response = null;
		
		logger.info("Inside the createAuthenticationToken method {}");
			
		try {
			if (authenticationRequest.getUsername() == null || authenticationRequest.getUsername().isEmpty() || authenticationRequest.getPassword() == null || authenticationRequest.getPassword() == "") {
                throw new UserNullException("Username and Password cannot be empty");
				}
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);

		
		
			final UserDetails userDetails = (UserDetails) userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
			if(userDetails.getUsername().isEmpty() || userDetails.getUsername() == null) {
				throw new UsernameNotFoundException("Invalid Username");
				}
			final String jwt = jwtTokenUtil.generateToken(userDetails);
			
			 if(jwt == null) {
				 throw new UserNullException("Error occured whie generating JWT");
			 }
			 else {
			 response = new AuthenticationResponse(jwt);			
			response.setUsername(userDetails.getUsername());
			List<String> roles = new ArrayList<String>();
			userDetails.getAuthorities().forEach((a) -> roles.add(a.getAuthority()));
			response.setRoles(roles);
			response.setMessage("SUCCESS");
			 }

			return new ResponseEntity<>(response,responseHeaders, HttpStatus.OK);
		}
		catch (Exception e) {
			response  = new AuthenticationResponse("");
			response.setMessage(e.getMessage());
			
			logger.error("Exception in LoginController createAuthenticationToken {} :" + e);
		    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

        }
		
		@GetMapping("/userdetails")
		public ResponseEntity<?> fetchUserDetails(HttpServletRequest req) throws Exception  {
			
			String token = req.getHeader("Authorization").replace("bearer ","");
		     String name  =jwtTokenUtil.extractUsername(token);
		     System.out.println(name);
		
			String role = jwtTokenUtil.extractRoles(token);
			 System.out.println(role);
			 
			 LoginDetails res =  new LoginDetails();
			 	res.setUsername(name);
			 	res.setRole(role);
			 
		HttpHeaders responseHeaders = new HttpHeaders();
		
		return new ResponseEntity<>(res, responseHeaders, HttpStatus.OK);		
			
		
		}
	 
	
	 
}
