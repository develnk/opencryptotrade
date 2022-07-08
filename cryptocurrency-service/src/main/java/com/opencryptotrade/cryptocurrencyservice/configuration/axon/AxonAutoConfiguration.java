package com.opencryptotrade.cryptocurrencyservice.configuration.axon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencryptotrade.cryptocurrencyservice.configuration.axon.properties.DistributedCommandBusProperties;
import com.opencryptotrade.cryptocurrencyservice.configuration.axon.properties.EventProcessorProperties;
import com.opencryptotrade.cryptocurrencyservice.configuration.axon.properties.TagsConfigurationProperties;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.DuplicateCommandHandlerResolver;
import org.axonframework.commandhandling.LoggingDuplicateCommandHandlerResolver;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.AxonThreadFactory;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.config.TagsConfiguration;
import org.axonframework.eventhandling.*;
import org.axonframework.eventhandling.async.SequencingPolicy;
import org.axonframework.eventhandling.async.SequentialPerAggregatePolicy;
import org.axonframework.eventhandling.gateway.DefaultEventGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.StreamableMessageSource;
import org.axonframework.messaging.SubscribableMessageSource;
import org.axonframework.messaging.annotation.HandlerDefinition;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.messaging.correlation.CorrelationDataProvider;
import org.axonframework.messaging.correlation.MessageOriginProvider;
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor;
import org.axonframework.queryhandling.*;
import org.axonframework.serialization.*;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotter;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;

@Configuration
@AutoConfigureAfter(EventProcessingAutoConfiguration.class)
@EnableConfigurationProperties(value = {
        EventProcessorProperties.class,
        DistributedCommandBusProperties.class,
        TagsConfigurationProperties.class
})
public class AxonAutoConfiguration implements BeanClassLoaderAware {

    private final EventProcessorProperties eventProcessorProperties;
    private final TagsConfigurationProperties tagsConfigurationProperties;
    private final ApplicationContext applicationContext;

    private ClassLoader beanClassLoader;

    public AxonAutoConfiguration(EventProcessorProperties eventProcessorProperties,
                                 TagsConfigurationProperties tagsConfigurationProperties,
                                 ApplicationContext applicationContext) {
        this.eventProcessorProperties = eventProcessorProperties;
        this.tagsConfigurationProperties = tagsConfigurationProperties;
        this.applicationContext = applicationContext;
    }
    @Bean
    public TagsConfiguration tagsConfiguration() {
        return tagsConfigurationProperties.toTagsConfiguration();
    }

    @Bean
    @ConditionalOnMissingBean
    public RevisionResolver revisionResolver() {
        return new AnnotationRevisionResolver();
    }


    @Bean
    @Primary
    @Qualifier("defaultSerializer")
    public Serializer serializer(RevisionResolver revisionResolver) {
        Map<String, ObjectMapper> objectMapperBeans = applicationContext.getBeansOfType(ObjectMapper.class);
        ObjectMapper objectMapper = objectMapperBeans.containsKey("defaultAxonObjectMapper")
                ? objectMapperBeans.get("defaultAxonObjectMapper")
                : objectMapperBeans.values().stream().findFirst()
                .orElseThrow(() -> new NoSuchBeanDefinitionException(ObjectMapper.class));

        ChainingConverter converter = new ChainingConverter(beanClassLoader);
        return JacksonSerializer.builder()
                .defaultTyping()
                .lenientDeserialization()
                .revisionResolver(revisionResolver)
                .converter(converter)
                .objectMapper(objectMapper)
                .build();
    }


    @Bean
    public ConfigurerModule serializerConfigurer(Serializer serializer) {
        return configurer -> {
            configurer.configureEventSerializer(c -> serializer);
            configurer.configureMessageSerializer(c -> serializer);
            configurer.configureSerializer(c -> serializer);
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public CorrelationDataProvider messageOriginProvider() {
        return new MessageOriginProvider();
    }

    @Qualifier("eventStore")
    @Bean(name = "eventBus")
    @ConditionalOnMissingBean(EventBus.class)
    @ConditionalOnBean(EventStorageEngine.class)
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, org.axonframework.config.Configuration configuration) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

    @ConditionalOnMissingBean
    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return DefaultCommandGateway.builder().commandBus(commandBus).build();
    }

    @ConditionalOnMissingBean
    @Bean
    public QueryGateway queryGateway(QueryBus queryBus) {
        return DefaultQueryGateway.builder().queryBus(queryBus).build();
    }

    @Bean
    @ConditionalOnMissingBean({EventStorageEngine.class, EventBus.class})
    public SimpleEventBus eventBus(org.axonframework.config.Configuration configuration) {
        return SimpleEventBus.builder()
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore"))
                .build();
    }

    @ConditionalOnMissingBean
    @Bean
    public EventGateway eventGateway(EventBus eventBus) {
        return DefaultEventGateway.builder().eventBus(eventBus).build();
    }

    @ConditionalOnMissingBean(Snapshotter.class)
    @ConditionalOnBean(EventStore.class)
    @Bean
    public SpringAggregateSnapshotter aggregateSnapshotter(org.axonframework.config.Configuration configuration,
                                                           HandlerDefinition handlerDefinition,
                                                           ParameterResolverFactory parameterResolverFactory,
                                                           EventStore eventStore,
                                                           TransactionManager transactionManager) {
        return SpringAggregateSnapshotter.builder()
                .repositoryProvider(configuration::repository)
                .transactionManager(transactionManager)
                .eventStore(eventStore)
                .parameterResolverFactory(parameterResolverFactory)
                .handlerDefinition(handlerDefinition)
                .build();
    }

    @SuppressWarnings("unchecked")
    @Autowired
    public void configureEventHandling(EventProcessingConfigurer eventProcessingConfigurer,
                                       ApplicationContext applicationContext) {
        eventProcessorProperties.getProcessors().forEach((name, settings) -> {
            Function<org.axonframework.config.Configuration, SequencingPolicy<? super EventMessage<?>>> sequencingPolicy =
                    resolveSequencingPolicy(applicationContext, settings);
            eventProcessingConfigurer.registerSequencingPolicy(name, sequencingPolicy);

            if (settings.getMode() == EventProcessorProperties.Mode.TRACKING) {
                TrackingEventProcessorConfiguration config = TrackingEventProcessorConfiguration
                        .forParallelProcessing(settings.getThreadCount())
                        .andBatchSize(settings.getBatchSize())
                        .andInitialSegmentsCount(initialSegmentCount(settings, 1))
                        .andTokenClaimInterval(settings.getTokenClaimInterval(),
                                settings.getTokenClaimIntervalTimeUnit());
                Function<org.axonframework.config.Configuration, StreamableMessageSource<TrackedEventMessage<?>>> messageSource =
                        resolveMessageSource(applicationContext, settings);
                eventProcessingConfigurer.registerTrackingEventProcessor(name, messageSource, c -> config);
            } else if (settings.getMode() == EventProcessorProperties.Mode.POOLED) {
                eventProcessingConfigurer.registerPooledStreamingEventProcessor(
                        name,
                        resolveMessageSource(applicationContext, settings),
                        (config, builder) -> {
                            ScheduledExecutorService workerExecutor = Executors.newScheduledThreadPool(
                                    settings.getThreadCount(), new AxonThreadFactory("WorkPackage[" + name + "]")
                            );
                            config.onShutdown(workerExecutor::shutdown);
                            return builder.workerExecutor(workerExecutor)
                                    .initialSegmentCount(initialSegmentCount(settings, 16))
                                    .tokenClaimInterval(tokenClaimIntervalMillis(settings))
                                    .batchSize(settings.getBatchSize());
                        }
                );
            } else {
                if (settings.getSource() == null) {
                    eventProcessingConfigurer.registerSubscribingEventProcessor(name);
                } else {
                    eventProcessingConfigurer.registerSubscribingEventProcessor(
                            name,
                            c -> applicationContext.getBean(settings.getSource(), SubscribableMessageSource.class)
                    );
                }
            }
        });
    }

    private int initialSegmentCount(EventProcessorProperties.ProcessorSettings settings, int defaultCount) {
        return settings.getInitialSegmentCount() != null ? settings.getInitialSegmentCount() : defaultCount;
    }

    private long tokenClaimIntervalMillis(EventProcessorProperties.ProcessorSettings settings) {
        return settings.getTokenClaimIntervalTimeUnit().toMillis(settings.getTokenClaimInterval());
    }

    @SuppressWarnings("unchecked")
    private Function<org.axonframework.config.Configuration, StreamableMessageSource<TrackedEventMessage<?>>> resolveMessageSource(
            ApplicationContext applicationContext, EventProcessorProperties.ProcessorSettings v) {
        Function<org.axonframework.config.Configuration, StreamableMessageSource<TrackedEventMessage<?>>> messageSource;
        if (v.getSource() == null) {
            messageSource = org.axonframework.config.Configuration::eventStore;
        } else {
            messageSource = c -> applicationContext.getBean(v.getSource(), StreamableMessageSource.class);
        }
        return messageSource;
    }

    @SuppressWarnings("unchecked")
    private Function<org.axonframework.config.Configuration, SequencingPolicy<? super EventMessage<?>>> resolveSequencingPolicy(
            ApplicationContext applicationContext, EventProcessorProperties.ProcessorSettings v) {
        Function<org.axonframework.config.Configuration, SequencingPolicy<? super EventMessage<?>>> sequencingPolicy;
        if (v.getSequencingPolicy() != null) {
            sequencingPolicy = c -> applicationContext.getBean(v.getSequencingPolicy(), SequencingPolicy.class);
        } else {
            sequencingPolicy = c -> SequentialPerAggregatePolicy.instance();
        }
        return sequencingPolicy;
    }

    @Bean
    @ConditionalOnMissingBean
    public DuplicateCommandHandlerResolver duplicateCommandHandlerResolver() {
        return LoggingDuplicateCommandHandlerResolver.instance();
    }

    @ConditionalOnMissingBean(
            ignoredType = {
                    "org.axonframework.commandhandling.distributed.DistributedCommandBus",
                    "org.axonframework.axonserver.connector.command.AxonServerCommandBus",
                    "org.axonframework.extensions.multitenancy.components.commandhandeling.MultiTenantCommandBus"
            },
            value = CommandBus.class
    )
    @Qualifier("localSegment")
    @Bean
    public SimpleCommandBus commandBus(TransactionManager txManager, org.axonframework.config.Configuration axonConfiguration,
                                       DuplicateCommandHandlerResolver duplicateCommandHandlerResolver) {
        SimpleCommandBus commandBus =
                SimpleCommandBus.builder()
                        .transactionManager(txManager)
                        .duplicateCommandHandlerResolver(duplicateCommandHandlerResolver)
                        .messageMonitor(axonConfiguration.messageMonitor(CommandBus.class, "commandBus"))
                        .build();
        commandBus.registerHandlerInterceptor(
                new CorrelationDataInterceptor<>(axonConfiguration.correlationDataProviders())
        );
        return commandBus;
    }

    @ConditionalOnMissingBean(value = QueryBus.class)
    @Qualifier("localSegment")
    @Bean
    public SimpleQueryBus queryBus(org.axonframework.config.Configuration axonConfiguration, TransactionManager transactionManager) {
        return SimpleQueryBus.builder()
                .messageMonitor(axonConfiguration.messageMonitor(QueryBus.class, "queryBus"))
                .transactionManager(transactionManager)
                .errorHandler(axonConfiguration.getComponent(
                        QueryInvocationErrorHandler.class,
                        () -> LoggingQueryInvocationErrorHandler.builder().build()
                ))
                .queryUpdateEmitter(axonConfiguration.getComponent(QueryUpdateEmitter.class))
                .build();
    }

    @Bean
    public QueryUpdateEmitter queryUpdateEmitter(org.axonframework.config.Configuration configuration) {
        return SimpleQueryUpdateEmitter.builder()
                .updateMessageMonitor(configuration.messageMonitor(
                        QueryUpdateEmitter.class, "queryUpdateEmitter"
                ))
                .build();
    }

    @Override
    public void setBeanClassLoader(@NonNull ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }
}
