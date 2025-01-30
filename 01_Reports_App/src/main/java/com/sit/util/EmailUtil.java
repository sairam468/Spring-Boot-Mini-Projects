package com.sit.util;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String sub,String body,String to,File f) {
		
		try {
			MimeMessage mmg=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mmg,true);
			helper.setSubject(sub);
			helper.setText(body,true);
			helper.setTo(to);
			helper.addAttachment("Plans-Data", f);
			
			mailSender.send(mmg);  
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}

}
