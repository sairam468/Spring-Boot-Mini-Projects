package com.sit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.entity.Comment;
import com.sit.entity.Post;
import com.sit.entity.User;



public interface CommentRepo extends JpaRepository<Comment, Integer>{
	
	public List<Comment> findAllByPost(Post post);
	
	public List<Comment> findAllByUser(User user);
	
}
