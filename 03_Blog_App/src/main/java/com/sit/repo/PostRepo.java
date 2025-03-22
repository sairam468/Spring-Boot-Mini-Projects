package com.sit.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.entity.Post;
import com.sit.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	//public Optional<Post> findByUserAndPId(User user, Integer pId);
   
	public List<Post> findAllByUser(User user);
}
