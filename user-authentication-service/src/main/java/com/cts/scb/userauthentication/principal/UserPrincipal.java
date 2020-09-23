package com.cts.scb.userauthentication.principal;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cts.scb.userauthentication.entity.MyUserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

//@SuppressWarnings("serial")
public class UserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private int id;
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	
	//BCryptPasswordEncoder encoder = passwordEncoder();

	public UserPrincipal(MyUserDetails user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		 this.authorities = Arrays.stream(user.getRoles().split(","))
		 .map(SimpleGrantedAuthority::new)
		 .collect(Collectors.toList());

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
