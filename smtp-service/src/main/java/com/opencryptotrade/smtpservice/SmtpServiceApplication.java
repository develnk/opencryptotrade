package com.opencryptotrade.smtpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableCircuitBreaker
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories({
        "com.opencryptotrade.commons.user.repository"
})
@EntityScan({
        "com.opencryptotrade.commons.user.domain"
})
@ComponentScan({
        "com.opencryptotrade.smtpservice",
        "com.opencryptotrade.authservice.service",
        "com.opencryptotrade.commons.user.config"
})
public class SmtpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmtpServiceApplication.class, args);
    }

}
