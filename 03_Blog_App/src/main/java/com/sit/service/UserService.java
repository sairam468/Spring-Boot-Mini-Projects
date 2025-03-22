package com.sit.service;

import java.util.List;

import com.sit.binding.LoginForm;
import com.sit.binding.SignUpForm;
import com.sit.entity.Comment;
import com.sit.entity.Post;
import com.sit.entity.User;


public interface UserService {

	public String login(LoginForm form);

	public String signUp(SignUpForm form);

	public List<Post> getAllPosts();
	
	public List<Post> getAllPostsByUser();

	public Post savePost(Post post) ;

	public Post getPostById(Integer id);

	public void deletePostById(Integer id);
	
	public Comment saveComment(Comment comment) ;
	
	public List<Comment> findByPost(Post p);
	
	public List<Comment> findByUser();
	
	public void deleteCommentById(Integer id);
}
