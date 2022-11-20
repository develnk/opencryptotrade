package com.opencryptotrade.common.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@Configuration
@EnableReactiveMongoAuditing
public class CommonConfiguration {

    @Bean
    public ReactiveAuditorAware<String> myAuditorProvider() {
        return new SpringSecurityAuditorAware();
    }

}
