package com.sit.service;

import java.security.KeyStore.Entry;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sit.binding.LoginForm;
import com.sit.binding.SignUpForm;
import com.sit.binding.UnlockForm;
import com.sit.entity.UserDetailsEntity;
import com.sit.repo.UserDetailsRepo;
import com.sit.util.EmailUtils;
import com.sit.util.PasswordUtils;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDetailsRepo repo;

	@Autowired
	private EmailUtils mail;
	
	@Autowired
	private HttpSession session;

	@Override
	public String login(LoginForm form) {
		UserDetailsEntity entity=repo.findByEmailAndPwd(form.getEmail(),form.getPwd());
		if(entity==null) {
			return "Invalid Credentials...";
		}
		if(entity.getAccStatus().equals("UnLocked")) {
            System.out.println("User Logged in Sucessfully with Name :: "+entity.getName()+" and UserId :: "+entity.getUserId()+"...");
			
			session.setAttribute("userId", entity.getUserId());
			return "Success";
		}
		if(entity.getAccStatus().equals("LOCKED")) {

			return "Your Account Locked with Name :: "+entity.getName()+"and UserId :: "+entity.getUserId()+"...";
		}
		else {
			return "Error Occured...";
		}
	}

	@Override
	public boolean signUp(SignUpForm form) {

		System.out.println("UserServiceImpl.signUp()--start");


		//copy data from binding obj to entity obj
		UserDetailsEntity e=new UserDetailsEntity();
		BeanUtils.copyProperties(form, e);

		//generate random password and set to obj
		String tempPwd=PasswordUtils.generateRandomPasswor();
		e.setPwd(tempPwd);

		//set account status LOCKED
		e.setAccStatus("LOCKED");


		//insert record
		repo.save(e);

		//send email
		String to=form.getEmail();
		String sub="Unlock Your Account ! AshokIT";
		StringBuffer body=new StringBuffer("");
		body.append("<h1>Use Below Temporary Password to Unlock your Account</h1>");
		body.append("Temporary Password : "+tempPwd);
		body.append("<br>");
		body.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Click Here to Unlock Your Account</a>");

		mail.sendEmail(to, sub,body.toString());

		System.out.println("UserServiceImpl.signUp()--end");


		return true;
	}

	@Override
	public boolean unlock(UnlockForm form) {

		UserDetailsEntity entity=repo.findByEmail(form.getEmail());

		if(entity.getPwd().equals(form.getTempPass())) {
			entity.setPwd(form.getNewPass());
			entity.setAccStatus("UnLocked");
			repo.save(entity);
			return true;
		}
		else {
			return false;
		}


	}

	@Override
	public String forgotPwd(String email) {
		UserDetailsEntity entity=repo.findByEmail(email);
		if(entity==null) {
			return "Invalid Email...,Account Not Found";
		}
		if(entity.getAccStatus().equals("UnLocked")) {
			
			String sub="Recover Your Password..! AshokIT";
			StringBuffer body=new StringBuffer("");
			body.append("<h1>Use Below Password to Login to your Account</h1>");
			body.append("User ID :: "+entity.getUserId());
			body.append("<br>");
			body.append("Name :: "+entity.getName());
			body.append("<br>");
			body.append("Password :: "+entity.getPwd());
			
			mail.sendEmail(email, sub,body.toString());
			
			return "Success";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "Account LOCKED";
		}
		else {
			return "Error Occured...";
		}
	}

}
