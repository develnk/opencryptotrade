package com.opencryptotrade.cryptocurrencyservice.configuration.axon;

import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.common.transaction.TransactionManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfigureAfter(TransactionAutoConfiguration.class)
@AutoConfigureBefore(AxonAutoConfiguration.class)
@Configuration
public class NoOpTransactionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(TransactionManager.class)
    public TransactionManager axonTransactionManager() {
        return NoTransactionManager.INSTANCE;
    }

}
