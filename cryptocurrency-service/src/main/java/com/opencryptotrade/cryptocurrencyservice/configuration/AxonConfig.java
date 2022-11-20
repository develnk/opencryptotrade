package com.opencryptotrade.cryptocurrencyservice.configuration;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrency;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.DuplicateCommandHandlerResolution;
import org.axonframework.commandhandling.DuplicateCommandHandlerResolver;
import org.axonframework.commandhandling.gateway.ExponentialBackOffIntervalRetryScheduler;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.extensions.reactor.commandhandling.gateway.DefaultReactorCommandGateway;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.config.SpringAxonConfiguration;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.axonframework.common.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.concurrent.Executors;

@Configuration
public class AxonConfig {

    @Bean
    public EventSourcingRepository<CryptoCurrency> eventSourcingRepository(EventStore eventStore) {
        return EventSourcingRepository.builder(CryptoCurrency.class)
                .aggregateFactory(new GenericAggregateFactory<>(CryptoCurrency.class))
                .eventStore(eventStore)
                .cache(cache())
                .build();
    }

    /***********************************************************/
    /* Register a dispatch interceptors on the command gateway */
    /***********************************************************/

//    @Autowired
//    public void reactiveCommandGatewayConfiguration(ReactorCommandGateway reactorCommandGateway) {
//        reactorCommandGateway.registerDispatchInterceptor(new LoggingReactorMessageDispatchInterceptor<>());
//    }

    /***************************************/
    /*  Aggregate cache configuration   */
    /***************************************/

    @Bean("cache")
    public Cache cache() {
        return new WeakReferenceCache();
    }

    /***************************************/
    /*  Aggregate snapshot configuration   */
    /***************************************/

    @Bean
    public SpringAggregateSnapshotterFactoryBean snapshotFactoryBean() {
        var aggregateSnapshotter = new SpringAggregateSnapshotterFactoryBean();
        aggregateSnapshotter.setExecutor(Executors.newSingleThreadExecutor());
        return aggregateSnapshotter;
    }

    @Bean("cryptoCurrencySnapshotTriggerDefinition")
    EventCountSnapshotTriggerDefinition cryptoCurrencySnapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, 3);
    }

    @Bean
    public EventStore eventStore(EventStorageEngine storageEngine) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .build();
    }
    @Bean
    public EventStorageEngine storageEngine(Serializer defaultSerializer,
                                            @Qualifier("eventSerializer") Serializer eventSerializer,
                                            SpringAxonConfiguration configuration,
                                            TransactionManager transactionManager,
                                            ConnectionProvider connectionProvider) {
        return JdbcEventStorageEngine.builder()
                .eventSerializer(eventSerializer)
                .snapshotSerializer(defaultSerializer)
                .upcasterChain(configuration.getObject().upcasterChain())
                .connectionProvider(connectionProvider)
                .transactionManager(transactionManager)
                .build();
    }

    /**
     *  The RetryScheduler is capable of scheduling retries when command execution has failed.
     *  When a command fails due to an exception that is explicitly non-transient, no retries are done at all.
     *  Note that the retry scheduler is only invoked when a command fails due to a RuntimeException.
     *  Checked exceptions are regarded as a "business exception" and will never trigger a retry.
     */
    @Bean
    public ReactorCommandGateway reactiveCommandGateway(CommandBus commandBus) {
        var scheduledExecutorService = Executors.newScheduledThreadPool(5);
        var retryScheduler = ExponentialBackOffIntervalRetryScheduler
                .builder()
                .retryExecutor(scheduledExecutorService)
                .maxRetryCount(3)
                .backoffFactor(1000)
                .build();

        return DefaultReactorCommandGateway.builder()
                .commandBus(commandBus)
                .retryScheduler(retryScheduler)
                .build();
    }

    /**
     *   Duplicate command handler configured to fail on duplicate registrations
     *   Command is always routed to a single destination.
     *   This means that during the registration of a command handler within a given JVM,
     *   a second registration of an identical command handler method should be dealt with in a desirable manner.
     *   By default, the LoggingDuplicateCommandHandlerResolver is used, which will log a warning and returns the candidate handler.
     */
    @Bean
    public DuplicateCommandHandlerResolver duplicateCommandHandlerResolver() {
        return DuplicateCommandHandlerResolution.rejectDuplicates();
    }

}
