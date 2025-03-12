package com.sit.service;

import com.sit.binding.LoginForm;
import com.sit.binding.SignUpForm;
import com.sit.binding.UnlockForm;

public interface UserService {

	public String login(LoginForm form);
	
	public boolean signUp(SignUpForm form);
	
	public boolean unlock(UnlockForm form);
	
	public String forgotPwd(String email);
}
