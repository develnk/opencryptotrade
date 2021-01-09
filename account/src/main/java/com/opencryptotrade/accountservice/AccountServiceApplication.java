package com.opencryptotrade.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableHystrix
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories({
        "com.opencryptotrade.accountservice.repository",
        "com.opencryptotrade.commons.user.repository"
})
@EntityScan({
        "com.opencryptotrade.accountservice.domain",
        "com.opencryptotrade.commons.user.domain"
})
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }
}
