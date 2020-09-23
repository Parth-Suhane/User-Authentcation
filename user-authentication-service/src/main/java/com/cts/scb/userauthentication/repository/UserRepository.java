package com.cts.scb.userauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.scb.userauthentication.entity.MyUserDetails;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUserDetails, Integer>{
		
	MyUserDetails findByUserName(String userName);
}
