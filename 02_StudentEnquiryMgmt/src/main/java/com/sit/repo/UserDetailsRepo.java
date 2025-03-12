package com.sit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.entity.UserDetailsEntity;

public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Integer>{

	public UserDetailsEntity findByEmail(String email);
	public UserDetailsEntity findByEmailAndPwd(String email,String pwd);
}
