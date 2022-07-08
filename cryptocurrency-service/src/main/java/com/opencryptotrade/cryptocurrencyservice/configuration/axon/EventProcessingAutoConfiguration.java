package com.opencryptotrade.cryptocurrencyservice.configuration.axon;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.EventProcessingModule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(name = {"com.opencryptotrade.cryptocurrencyservice.configuration.axon.JdbcAutoConfiguration"})
public class EventProcessingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean({EventProcessingModule.class, EventProcessingConfiguration.class})
    public EventProcessingModule eventProcessingModule() {
        return new EventProcessingModule();
    }

}
