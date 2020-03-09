package com.opencryptotrade.smtpservice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mail implements EmailService {

    @Autowired
    public JavaMailSender emailSender;


    @Autowired
    private Environment environment;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(environment.getProperty("mail.from"));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String... templateArgs) {

    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {

    }
}
