package com.sms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendPasswordResetMail(String resetToken) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo("chavanjotiram24@gmail.com");
		mailMessage.setSubject("Password Reset Link");
		mailMessage.setText("Please click below link to reset password:" + resetToken);

		mailMessage.setFrom("johndoe@example.com");

		javaMailSender.send(mailMessage);
		return false;

	}
}
