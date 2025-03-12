package com.sit.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sit.binding.LoginForm;
import com.sit.binding.SignUpForm;
import com.sit.binding.UnlockForm;
import com.sit.service.UserService;

@Controller
public class UsersController {

	@Autowired
	private UserService service;

	@GetMapping("/signup")
	public String signUpPage(Model m) {
		m.addAttribute("user",new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignUp(@ModelAttribute("user")SignUpForm form, Model m) {
		try {
			boolean status = service.signUp(form);
			String mail = form.getEmail();
			//System.out.println(mail);

			if (status) {
				m.addAttribute("succMsg", "Account Created! ,Please Check your Email");
			} else {
				m.addAttribute("errMsg", "Problem occurred");

			}
			 }
		catch (DataIntegrityViolationException e) {
			m.addAttribute("errMsg", "Email already exists");
		}
		return "signup";
	}

		@GetMapping("/login")
		public String loginPage(Model m) {
			m.addAttribute("loginForm",new LoginForm());
			return "login";
		}
		
		@PostMapping("/login")
		public String login(@ModelAttribute("loginForm")LoginForm form,Model m) {
			//System.out.println(form);
			String msg=service.login(form);
			if(msg.contains("Success")) {
				
				return "redirect:/dashboard";
			}
			m.addAttribute("errMsg", msg);
			return "login";	
		}


		@GetMapping("/unlock")
		public String unlockAccPage(@RequestParam(required = false) String email, Model m) {
			UnlockForm ul=new UnlockForm();
			ul.setEmail(email);
			
			m.addAttribute("unlock", ul); // Pass email to the view
			return "unlock";
		}

		@PostMapping("/unlock")
		public String unlockUserAcc(@ModelAttribute("unlock") UnlockForm unlock, Model m) {
			//System.out.println("UsersController.unlockUserAcc()--end");
			//System.out.println(unlock);

			if(unlock.getNewPass().equals(unlock.getConPass())) {
				if(service.unlock(unlock)) {
					m.addAttribute("succMsg", "Your account UnLocked Sucessfully...");
				}
				else {
					m.addAttribute("errMsg","Given Temporary Password is Wrong!!");
				}
			}
			else {
				m.addAttribute("errMsg","New Password and Confirm Password Must be Same!!");
			}
				
			
			//System.out.println("UsersController.unlockUserAcc()--end");
			return "unlock";
		}

		
		
		@GetMapping("/forgot")
		public String forgotPwdPage() {
			return "forgot";
		}


		@PostMapping("/forgot")
		public String forgot(@RequestParam("email")String email, Map<String, Object> m) {
			System.out.println(email);
		    String msg = service.forgotPwd(email);
		    if (msg.contains("Success")) {
		        m.put("succMsg", "Account details are sent to your email. Check your inbox to log in.");
		    } else {
		        m.put("errMsg", "Invalid email address. Please try again.");
		    }
		    return "forgot";
		}


	}
