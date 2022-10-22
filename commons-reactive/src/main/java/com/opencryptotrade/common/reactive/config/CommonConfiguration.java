package com.opencryptotrade.common.reactive.config;

import com.opencryptotrade.common.model.user.SystemUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@Configuration
@EnableReactiveMongoAuditing
public class CommonConfiguration {

    @Bean
    public ReactiveAuditorAware<SystemUser> myAuditorProvider() {
        return new SpringSecurityAuditorAware();
    }

}
