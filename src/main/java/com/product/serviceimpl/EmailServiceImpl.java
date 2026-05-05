package com.product.serviceimpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.product.service.MailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements MailService{
	
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String senderId;
	
	@Override
	public boolean sentEmail(String receiverId, String message,String subject) {
		try {
			SimpleMailMessage mailMessage=new SimpleMailMessage();
			mailMessage.setTo(receiverId);
			mailMessage.setFrom(senderId);
			mailMessage.setSubject(subject);
			mailMessage.setText(message);
			mailSender.send(mailMessage );
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
