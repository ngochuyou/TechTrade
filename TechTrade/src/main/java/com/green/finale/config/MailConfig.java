package com.green.finale.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("youremail");
		mailSender.setPassword("yourpassword");
		
		Properties props = mailSender.getJavaMailProperties();
		
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "false");
		props.put("mail.from", "ngochuy.ou@gmail.com");
		mailSender.setJavaMailProperties(props);
		
		return mailSender;
	}
}
