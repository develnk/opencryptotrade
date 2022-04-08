package com.opencryptotrade.smtpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories({
        "com.opencryptotrade.common.user.repository"
})
@EntityScan({
        "com.opencryptotrade.commons.user.domain"
})
@ComponentScan({
        "com.opencryptotrade.smtpservice",
        "com.opencryptotrade.common.user.config"
})
public class SmtpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmtpServiceApplication.class, args);
    }

}
