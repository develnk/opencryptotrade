package com.opencryptotrade.smtpservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class SmtpConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpConfiguration.class);

    @Bean("mail.sender")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(env.getProperty("mail.hostname"));
//        mailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));
//        mailSender.setUsername(env.getProperty("mail.credentials.username"));
//        mailSender.setPassword(env.getProperty("mail.credentials.password"));
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.required", true);
//        props.put("mail.smtp.starttls.enable", env.getProperty("mail.tls"));
//        props.put("mail.debug", env.getProperty("mail.debug"));

        return mailSender;
    }

}
