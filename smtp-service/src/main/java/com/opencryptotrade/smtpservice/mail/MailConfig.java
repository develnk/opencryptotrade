package com.opencryptotrade.smtpservice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import lombok.SneakyThrows;

import com.opencryptotrade.smtpservice.dto.Smtp;

import java.io.File;


@Component
public class MailConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private GenericWebApplicationContext context;

    @Autowired
    PropertiesConfiguration properties;

    public boolean updateConfiguration(Smtp data) {
        try {
            updateFileConfig(data);
            updateMailer(data);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        return true;
    }

    public Smtp getSettings() {
        Smtp settings = new Smtp();
        settings.setUserName(environment.getProperty("mail.credentials.username"));
        settings.setHostName(environment.getProperty("mail.hostname"));
        settings.setPort(Integer.parseInt(environment.getProperty("mail.port")));
        settings.setFrom(environment.getProperty("mail.from"));
        settings.setDebug(Boolean.parseBoolean(environment.getProperty("mail.debug")));
        settings.setSsl(Boolean.parseBoolean(environment.getProperty("mail.ssl")));
        settings.setTls(Boolean.parseBoolean(environment.getProperty("mail.tls")));
        return settings;
    }

    private void updateMailer(Smtp data) {
        JavaMailSenderImpl sender = (JavaMailSenderImpl) context.getBean("mail.sender");
        sender.setHost(environment.getProperty("mail.hostname"));
        sender.setPort(Integer.parseInt(environment.getProperty("mail.port")));
        sender.setUsername(environment.getProperty("mail.credentials.username"));
        sender.setPassword(environment.getProperty("mail.credentials.password"));
        sender.getJavaMailProperties().setProperty("mail.debug", String.valueOf(data.isDebug()));
        if (data.isSsl()) {
            sender.getJavaMailProperties().setProperty("mail.ssl", String.valueOf(data.isSsl()));
            sender.getJavaMailProperties().setProperty("mail.tls", String.valueOf(false));
        }
        else if (data.isTls()) {
            sender.getJavaMailProperties().setProperty("mail.tls", String.valueOf(data.isSsl()));
            sender.getJavaMailProperties().setProperty("mail.ssl", String.valueOf(false));
        }
    }

    @SneakyThrows
    private void updateFileConfig(Smtp data) throws ConfigurationException {
        File propFile = properties.getFile();
        if (propFile.exists()) {
            File copied = new File(propFile.getPath() + ".bak");
            FileUtils.copyFile(propFile, copied);
        }

        properties.setProperty("mail.hostname", data.getHostName());
        properties.setProperty("mail.port", data.getPort());
        properties.setProperty("mail.from", data.getFrom());
        properties.setProperty("mail.credentials.username", data.getUserName());
        properties.setProperty("mail.credentials.password", data.getPassword());
        properties.setProperty("mail.debug", data.isDebug());
        if (data.isSsl()) {
            properties.setProperty("mail.ssl", data.isSsl());
            properties.setProperty("mail.tls", false);
        }
        else if (data.isTls()) {
            properties.setProperty("mail.tls", data.isTls());
            properties.setProperty("mail.ssl", false);
        }
        else {
            properties.setProperty("mail.tls", false);
            properties.setProperty("mail.ssl", false);
        }
        properties.save();
    }

}
