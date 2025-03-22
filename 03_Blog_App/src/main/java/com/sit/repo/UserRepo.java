package com.sit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	public User findByEmail(String email);
	
	public User findByEmailAndPwd(String email,String pwd);
}
