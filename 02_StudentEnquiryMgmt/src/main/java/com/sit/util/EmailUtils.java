package com.sit.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mail;
	
	public boolean sendEmail(String to,String sub,String body) {
		boolean isSent=false;
		
		try {
			MimeMessage msg=mail.createMimeMessage();
			
			MimeMessageHelper h=new MimeMessageHelper(msg);
			
			h.setTo(to);
			h.setSubject(sub);
			h.setText(body,true);
			
			mail.send(msg);
			
			isSent=true;
		}
		catch(Exception e) {e.printStackTrace();}
		
	return isSent;
	
}
}
