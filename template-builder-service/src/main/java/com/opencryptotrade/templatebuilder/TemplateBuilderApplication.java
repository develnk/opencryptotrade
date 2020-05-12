package com.opencryptotrade.templatebuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableOAuth2Client
@EnableHystrix
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories("com.opencryptotrade.templatebuilder.repository")
@EnableScheduling
@EnableMongoRepositories
public class TemplateBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateBuilderApplication.class, args);
    }

}
