package com.sit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sit.binding.LoginForm;
import com.sit.binding.SignUpForm;
import com.sit.entity.Comment;
import com.sit.entity.Post;
import com.sit.service.UserService;

import jakarta.servlet.http.HttpSession;




@Controller
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private HttpSession session;


	@GetMapping("/")
	public String home(Model model) {
		List<Post> posts = service.getAllPosts();
		model.addAttribute("posts", posts);
		return "index";
	}

	@GetMapping("/login")
	public String login(Model m) {
		m.addAttribute("loginForm", new LoginForm());
		m.addAttribute("errMsg", ""); // Ensure error message attribute exists
		m.addAttribute("succMsg", ""); // Ensure success message attribute exists
		return "login";
	}


	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm")LoginForm form,Model m) {
		//System.out.println(form);
		String msg=service.login(form);
		if("Success".equals(msg)) {
			return "redirect:/viewPost";
		}
		else {
			m.addAttribute("errMsg", msg);
			return "login";	
		}
	}



	@GetMapping("/register")
	public String signUp(Model m) {
		m.addAttribute("user",new SignUpForm());
		return "register";
	}

	@PostMapping("/register")
	public String handleSignup(@ModelAttribute("user") SignUpForm form, Model model) {
		// Here you would add logic to save user details
		System.out.println("User Registered: " + form);

		String msg = service.signUp(form);
		if(msg.equalsIgnoreCase("success")) {
			model.addAttribute("succMsg","User Details Registered Successfully...");
		}
		else if(msg.equalsIgnoreCase("emailExist")) {
			model.addAttribute("errMsg", "Email Id Already Exists...");
		}
		else {
			model.addAttribute("errMsg", "Invalid Details");
		}

		model.addAttribute("msg", msg);
		return "register"; // Redirect user to login page
	}

	@GetMapping("/comments")
	public String comments(Map<String,Object> m) {
		
		List<Comment> byUser = service.findAllCommentsForUserPosts();
		 m.put("comments", byUser);
			
		return "comments"; // This should match the name of your Thymeleaf template
	}


	@GetMapping("/logout")
	public String logout( Model model) {
		model.addAttribute("succMsg","Logged out Successfully...");
		session.invalidate();
		return "redirect:/"; // This should match the name of your Thymeleaf template
	}

	@GetMapping("/addPost")
	public String showAddPostForm(Model model) {
		model.addAttribute("post", new Post());
		return "addPost";
	}

	@PostMapping("/savePost")
	public String savePost(@ModelAttribute Post post) {
		service.savePost(post);
		return "redirect:/viewPost";
	}

	@GetMapping("/viewPost")
	public String viewPosts(Model model) {
		model.addAttribute("posts", service.getAllPostsByUser());
		return "viewPost";
	}

	@GetMapping("/post/{id}")
	public String openPost(@PathVariable Integer id,Model model) {
		Post post = service.getPostById(id);
		if (post == null) {
			return "redirect:/viewPost"; // Redirect if post not found
		}
		List<Comment> comments = service.findByPost(post);

		model.addAttribute("comments", comments);

		model.addAttribute("post", post);
		return "openPost"; // Renders openPost.html

	}
	
	@GetMapping("/hpost/{id}")
	public String openHomePost(@PathVariable Integer id,Model model) {
		Post post = service.getPostById(id);
		if (post == null) {
			return "redirect:/"; // Redirect if post not found
		}
		List<Comment> comments = service.findByPost(post);

		model.addAttribute("comments", comments);

		model.addAttribute("post", post);
		return "viewHomePost"; // Renders openPost.html

	}
	
	@GetMapping("/editPost/{id}")
	public String editPost(@PathVariable Integer id,Model model) {
		
		Post post = service.getPostById(id);
		model.addAttribute("post",post);
		return "editPost";
	}
	
	@PostMapping("/updatePost")
	public String updatePost(@ModelAttribute Post post,RedirectAttributes redirectAttributes) {
	    Post existingPost = service.getPostById(post.getPId());
	    
	    if (existingPost != null) {
	        existingPost.setTitle(post.getTitle());
	        existingPost.setDescription(post.getDescription());
	        existingPost.setContent(post.getContent());

	        // Save updated post
	        service.savePost(existingPost);
	        
	        redirectAttributes.addFlashAttribute("succMsg","User Details Updated Successfully...");
	    }

	    return "redirect:/viewPost"; // Redirect to posts list after updating
	}

	@PostMapping("/deletePost/{id}")
	public String deletePost(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		service.deletePostById(id);
		redirectAttributes.addFlashAttribute("succMsg", "Post Deleted Successfully");
		return "redirect:/viewPost";
	}

	@PostMapping("/post/{postId}/comment")
	public String addComment(@PathVariable Integer postId, @RequestParam String name, 
			@RequestParam String eMail, @RequestParam String content) {
		Post post = service.getPostById(postId);
		if(post==null) {
			throw new  RuntimeException("Post not found");
		}
		Comment comment = new Comment();
		comment.setName(name);
		comment.setEMail(eMail);
		comment.setContent(content);
		comment.setPost(post);
		Comment saveComment = service.saveComment(comment);

		if(saveComment!= null) {
			return "redirect:/post/" + postId;
		}
		return null;
	}
	
	@PostMapping("/post/{postId}/hcomment")
	public String addHComment(@PathVariable Integer postId, @RequestParam String name, 
			@RequestParam String eMail, @RequestParam String content) {
		Post post = service.getPostById(postId);
		if(post==null) {
			throw new  RuntimeException("Post not found");
		}
		Comment comment = new Comment();
		comment.setName(name);
		comment.setEMail(eMail);
		comment.setContent(content);
		comment.setPost(post);
		Comment saveComment = service.saveComment(comment);

		if(saveComment!= null) {
			return "redirect:/hpost/" + postId;
		}
		return null;
	}
	
	@PostMapping("/deleteComment")
	public String deleteComment(@RequestParam("commentId") Integer commentId,RedirectAttributes redirectAttributes) {
		
		service.deleteCommentById(commentId);
		redirectAttributes.addFlashAttribute("succMsg", "Comment Deleted Successfully");
		return "redirect:/comments";
	}
	
}
