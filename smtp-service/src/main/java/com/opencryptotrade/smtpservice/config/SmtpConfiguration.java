package com.opencryptotrade.smtpservice.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.File;
import java.util.Properties;

@Configuration
public class SmtpConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpConfiguration.class);

    private final ConfigurableEnvironment env;

    public SmtpConfiguration(ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    @ConditionalOnProperty(name = "spring.config.location")
    public PropertiesConfiguration propertiesConfiguration(@Value("${spring.config.location}") String path) throws Exception {
        String filePath = new File(path.substring("file:".length())).getCanonicalPath();
        PropertiesConfiguration configuration = new PropertiesConfiguration(new File(filePath));
        FileChangedReloadingStrategy strategy = new FileChangedReloadingStrategy();
        strategy.setRefreshDelay(1000);
        configuration.setReloadingStrategy(strategy);
        return configuration;
    }

    @Bean("mail.sender")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("mail.hostname"));
        mailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));
        mailSender.setUsername(env.getProperty("mail.credentials.username"));
        mailSender.setPassword(env.getProperty("mail.credentials.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", true);
        props.put("mail.smtp.starttls.enable", env.getProperty("mail.tls"));
        props.put("mail.debug", env.getProperty("mail.debug"));

        return mailSender;
    }

}
