package com.opencryptotrade.templatebuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
@EnableJpaRepositories({
        "com.opencryptotrade.templatebuilder.repository",
})
@EntityScan({
        "com.opencryptotrade.commons.user.domain"
})
@ComponentScan({
        "com.opencryptotrade.templatebuilder",
        "com.opencryptotrade.common.user.config"
})
@EnableMongoRepositories
public class TemplateBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateBuilderApplication.class, args);
    }

}
