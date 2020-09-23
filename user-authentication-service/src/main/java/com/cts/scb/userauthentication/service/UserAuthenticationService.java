package com.cts.scb.userauthentication.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.scb.userauthentication.controller.LoginController;
import com.cts.scb.userauthentication.entity.MyUserDetails;
import com.cts.scb.userauthentication.repository.UserRepository; 

@Service
public class UserAuthenticationService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;



	Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		BCryptPasswordEncoder encoder = passwordEncoder();
		MyUserDetails user = userRepository.findByUserName(username);
		logger.info("User details" + user);
		if(user == null)
		{ 
			throw new UsernameNotFoundException("User Name "+username  +"Not Found"); 
		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(),encoder
				.encode(user.getPassword()),getGrantedAuthorities(user));
	}



	@Bean public BCryptPasswordEncoder passwordEncoder() { 
		return new
				BCryptPasswordEncoder(); 
	}


	private Collection<GrantedAuthority> getGrantedAuthorities(MyUserDetails
			user)
	{

		Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();

		grantedAuthority.add(new SimpleGrantedAuthority(user.getRoles())); 
		return grantedAuthority;
	}

}
