package com.opencryptotrade.cryptocurrencyservice.configuration;

import com.opencryptotrade.common.events.EventuateKafkaAggregateSubscriptionsNew;
import com.opencryptotrade.cryptocurrencyservice.domain.CryptoCurrency;
import com.opencryptotrade.cryptocurrencyservice.domain.command.CryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.persistence.CryptoCurrencyViewRepository;
import com.opencryptotrade.cryptocurrencyservice.domain.snapshot.CryptoCurrencySnapshotStrategy;
import com.opencryptotrade.cryptocurrencyservice.service.CryptoCurrencyService;
import com.opencryptotrade.cryptocurrencyservice.service.CryptoCurrencyServiceImpl;
import com.opencryptotrade.cryptocurrencyservice.service.CryptoCurrencyHistoryViewWorkflow;
import io.eventuate.javaclient.spring.events.EnableEventHandlers;
import io.eventuate.local.java.events.EventuateKafkaAggregateSubscriptions;
import io.eventuate.local.java.spring.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.messaging.kafka.basic.consumer.EventuateKafkaConsumerConfigurationProperties;
import io.eventuate.messaging.kafka.basic.consumer.KafkaConsumerFactory;
import io.eventuate.messaging.kafka.common.EventuateKafkaConfigurationProperties;
import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@ComponentScan({"com.opencryptotrade"})
@EnableEventHandlers
@Import(EventuateDriverConfiguration.class)
@RequiredArgsConstructor
public class CryptoCurrencyConfiguration {

    private final CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    @Bean
    public CryptoCurrencyService cryptoCurrencyService(AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptocurrencyRepository) {
        return new CryptoCurrencyServiceImpl(cryptocurrencyRepository, cryptoCurrencyViewRepository);
    }

    @Bean
    public AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptocurrencyRepository(EventuateAggregateStore eventStore) {
        return new AggregateRepository<>(CryptoCurrency.class, eventStore);
    }

    @Bean
    public CryptoCurrencySnapshotStrategy cryptoCurrencySnapshotStrategy() {
        return new CryptoCurrencySnapshotStrategy();
    }

    @Bean
    public CryptoCurrencyHistoryViewWorkflow cryptoCurrencyWorkflow() {
        return new CryptoCurrencyHistoryViewWorkflow(cryptoCurrencyViewRepository);
    }

    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public EventuateKafkaAggregateSubscriptions aggregateEvents(EventuateKafkaConfigurationProperties eventuateLocalAggregateStoreConfiguration,
                                                                EventuateKafkaConsumerConfigurationProperties eventuateKafkaConsumerConfigurationProperties,
                                                                KafkaConsumerFactory kafkaConsumerFactory) {
        return new EventuateKafkaAggregateSubscriptionsNew(eventuateLocalAggregateStoreConfiguration, eventuateKafkaConsumerConfigurationProperties, kafkaConsumerFactory);
    }
}
