package com.EventManagementSystem.EventManagementSystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
	public class MailUtils {
		@Autowired
		private JavaMailSender javaMailSender;
		
		public void sendEmail(String toEmail, String subject, String message,String from) {
			
			SimpleMailMessage mail = new  SimpleMailMessage();		
			mail.setTo(toEmail);
			mail.setSubject(subject);
			mail.setText(message);
		    mail.setFrom(from); // Specify the sender's email

			
			javaMailSender.send(mail);
		}
        public void sendEmail1(String toEmail, String subject, String message) {
			
			SimpleMailMessage mail = new  SimpleMailMessage();		
			mail.setTo(toEmail);
			mail.setSubject(subject);
			mail.setText(message);

			
			javaMailSender.send(mail);
		}

	}

