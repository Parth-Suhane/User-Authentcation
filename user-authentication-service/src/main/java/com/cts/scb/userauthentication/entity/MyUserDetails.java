package com.cts.scb.userauthentication.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Data
@Entity
@Table(name = "user")
public class MyUserDetails implements Serializable {

	@Override
	public String toString() {
		return "MyUserDetails [user_id=" + user_id + ", userName=" + userName + ", password=" + password + ", roles="
				+ roles + "]";
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;

	@NotEmpty
	@Column(name="user_name")
	private String userName;
	
	@NotEmpty
	@Column(name="password")
	private String password;
	
	@Column(name="roles")
	private String roles;

}