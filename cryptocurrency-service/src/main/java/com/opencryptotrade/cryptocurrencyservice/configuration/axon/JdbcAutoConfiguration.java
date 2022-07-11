package com.opencryptotrade.cryptocurrencyservice.configuration.axon;

import com.zaxxer.hikari.HikariDataSource;
import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.common.jdbc.PersistenceExceptionResolver;
import org.axonframework.common.jdbc.UnitOfWorkAwareConnectionProviderWrapper;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.jdbc.JdbcTokenStore;
import org.axonframework.eventhandling.tokenstore.jdbc.TokenSchema;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jdbc.JdbcSQLErrorCodesResolver;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.jdbc.GenericSagaSqlSchema;
import org.axonframework.modelling.saga.repository.jdbc.JdbcSagaStore;
import org.axonframework.modelling.saga.repository.jdbc.SagaSqlSchema;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.jdbc.SpringDataSourceConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean({EventStorageEngine.class, EventBus.class})
    public EventStorageEngine eventStorageEngine(Serializer defaultSerializer,
                                                 PersistenceExceptionResolver persistenceExceptionResolver,
                                                 @Qualifier("eventSerializer") Serializer eventSerializer,
                                                 org.axonframework.config.Configuration configuration,
                                                 ConnectionProvider connectionProvider,
                                                 TransactionManager transactionManager) {
        return JdbcEventStorageEngine.builder()
                .snapshotSerializer(defaultSerializer)
                .upcasterChain(configuration.upcasterChain())
                .persistenceExceptionResolver(persistenceExceptionResolver)
                .eventSerializer(eventSerializer)
                .snapshotFilter(configuration.snapshotFilter())
                .connectionProvider(connectionProvider)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean({PersistenceExceptionResolver.class, EventStore.class})
    public PersistenceExceptionResolver jdbcSQLErrorCodesResolver() {
        return new JdbcSQLErrorCodesResolver();
    }

    // @Todo Hack!
    @Bean(name = "axonProperties")
    @ConfigurationProperties("spring.datasource.axon")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionProvider connectionProvider(@Qualifier("axonProperties") DataSourceProperties properties) {
        DataSource dataSource = properties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
        return new UnitOfWorkAwareConnectionProviderWrapper(new SpringDataSourceConnectionProvider(dataSource));
    }

    @Bean("tokenStore")
    @ConditionalOnMissingBean(TokenStore.class)
    @ConditionalOnBean(TokenSchema.class)
    public TokenStore tokenStoreWithCustomSchema(ConnectionProvider connectionProvider, Serializer serializer, TokenSchema tokenSchema) {
        return JdbcTokenStore.builder()
                .connectionProvider(connectionProvider)
                .schema(tokenSchema)
                .serializer(serializer)
                .build();
    }

    @Bean("tokenStore")
    @ConditionalOnMissingBean({TokenStore.class, TokenSchema.class})
    public TokenStore tokenStoreWithDefaultSchema(ConnectionProvider connectionProvider, Serializer serializer) {
        return JdbcTokenStore.builder()
                .connectionProvider(connectionProvider)
                .schema(new TokenSchema())
                .serializer(serializer)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean({SagaStore.class, SagaSqlSchema.class})
    public JdbcSagaStore sagaStore(ConnectionProvider connectionProvider, Serializer serializer) {
        return JdbcSagaStore.builder()
                .connectionProvider(connectionProvider)
                .sqlSchema(new GenericSagaSqlSchema())
                .serializer(serializer)
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(SagaStore.class)
    @ConditionalOnBean(SagaSqlSchema.class)
    public JdbcSagaStore sagaStore(ConnectionProvider connectionProvider, Serializer serializer, SagaSqlSchema schema) {
        return JdbcSagaStore.builder()
                .connectionProvider(connectionProvider)
                .sqlSchema(schema)
                .serializer(serializer)
                .build();
    }

}
