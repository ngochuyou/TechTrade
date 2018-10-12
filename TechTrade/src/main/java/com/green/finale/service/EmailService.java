package com.green.finale.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {
	@Autowired
	public JavaMailSender emailSender;

	@Transactional
	public int sendVerifyCode(String to, String text, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		StringBuilder sb = new StringBuilder(text);
		int code = randomVerifyCode();
	
		sb.append(code);
		sb.append(".Please enter this code to continue. Thank you!\nTechTrade");
		message.setSubject(subject);
		message.setText(sb.toString());
		message.setTo(to);
		emailSender.send(message);
		
		return code;
	}

	public int randomVerifyCode() {
		Random ran = new Random();

		return ran.nextInt((9999 - 1000) + 1) + 1000;
	}
}
