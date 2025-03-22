package com.sit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sit.binding.LoginForm;
import com.sit.binding.SignUpForm;
import com.sit.entity.Comment;
import com.sit.entity.Post;
import com.sit.entity.User;
import com.sit.repo.CommentRepo;
import com.sit.repo.PostRepo;
import com.sit.repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo repo;

	@Autowired
	private PostRepo postRepository;
	
	@Autowired
	private CommentRepo comRepo;


	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm form) {
		User entity=repo.findByEmailAndPwd(form.getEmail(),form.getPwd());
		if(entity==null) {
			return "Invalid Credentials...";
		}
		if(entity!=null) {
			System.out.println("Success");

			session.setAttribute("userId", entity.getId());
			return "Success";
		}
		else {
			return "Error Occured...";
		}
	}

	@Override
	public String signUp(SignUpForm form) {
		User user=new User();
		BeanUtils.copyProperties(form, user);

		User byEmail = repo.findByEmail(user.getEmail());

		if(byEmail == null){
			User save = repo.save(user);
			if(save == null){
				return "regError";
			}
			return "success";
		}
		else if(byEmail != null){
			return "emailExist";
		}
		return "Details Already Exists";
	}

	@Override
	public List<Post> getAllPostsByUser() {
		int uid=(int) session.getAttribute("userId");
		User user = repo.findById(uid).get();
		
		List<Post> all = postRepository.findAllByUser(user);
		List<Post> collect = all.stream().filter(p->(p.getStatus().equalsIgnoreCase("active"))).collect(Collectors.toList());

		return collect;
	}
	
	@Override
	public List<Post> getAllPosts() {
		List<Post> all = postRepository.findAll();
		List<Post> collect = all.stream().filter(p->(p.getStatus().equalsIgnoreCase("active"))).collect(Collectors.toList());

		return collect;
	}

	@Override
	public Post savePost(Post post) {
		int uid=(int) session.getAttribute("userId");
		System.out.println("-------"+uid+"--------");
		User user = repo.findById(uid).get();
		post.setUser(user);
		return postRepository.save(post);
	}

	@Override
	public Post getPostById(Integer id) {
		return postRepository.findById(id).orElse(null); // Fetch post by ID
	}

	@Override
	public void deletePostById(Integer id) {
		Post post = postRepository.findById(id).get();
		post.setStatus("inactive");
		postRepository.save(post);
		
	}

	@Override
	public Comment saveComment(Comment comment) {
		
		Post p = comment.getPost();
		User user = p.getUser();
		
		comment.setUser(user);
		Comment save = comRepo.save(comment);
		return save;
	}

	@Override
	public List<Comment> findByPost(Post p) {
		List<Comment> list = comRepo.findAllByPost(p);
		return list;
	}

	@Override
	public List<Comment> findAllCommentsForUserPosts() {
	    int uid = (int) session.getAttribute("userId"); // Get the userId from session
	    User user = repo.findById(uid).orElse(null); // Fetch user from repo

	    if (user == null) {
	        return new ArrayList<>(); // Return an empty list if the user is not found
	    }

	    List<Post> posts = user.getPosts(); // Get all posts of the user
	    List<Comment> allComments = new ArrayList<>();

	    for (Post post : posts) {
	        List<Comment> comments = comRepo.findAllByPost(post); // Get comments for each post
	        allComments.addAll(comments);
	    }

	    return allComments; // Return all comments for all posts
	}

	@Override
	public List<Comment> findByUser() {

		int uid=(int) session.getAttribute("userId");
		User u = repo.findById(uid).get();
		
		List<Comment> list = comRepo.findAllByUser(u);
		return list;
	}
	
	@Override
	public void deleteCommentById(Integer id) {
		comRepo.deleteById(id);
	}

}


