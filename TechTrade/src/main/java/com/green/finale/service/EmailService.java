package com.green.finale.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	public JavaMailSender emailSender;

	public int sendVerifyCode(String to) {
		SimpleMailMessage message = new SimpleMailMessage();
		StringBuilder sb = new StringBuilder("Hi! Your account's verify code is ");
		int code = randomVerifyCode(); 
		
		sb.append(code);
		sb.append(".Please enter this code on your sign-up form continue. Thank you!\nTechTrade");
		message.setSubject("TechTrade - Verify your account");
		message.setText(sb.toString());
		message.setTo(to);
		emailSender.send(message);
		
		return code;
	}
	
	public int randomVerifyCode() {
		Random ran = new Random();
		
		return ran.nextInt((9999-1000) + 1) + 1000;
	}
}
