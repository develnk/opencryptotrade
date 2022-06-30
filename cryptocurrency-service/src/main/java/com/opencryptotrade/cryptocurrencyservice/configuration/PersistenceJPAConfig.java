package com.opencryptotrade.cryptocurrencyservice.configuration;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories(
        basePackages = {"com.opencryptotrade.cryptocurrencyservice"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@DependsOn("dataSource")
public class PersistenceJPAConfig {

    private final ConfigurableListableBeanFactory beanFactory;
    private final JpaProperties jpaProperties;
    private final DataSource dataSource;


    @Autowired
    public PersistenceJPAConfig(ConfigurableListableBeanFactory beanFactory, JpaProperties jpaProperties, DataSource dataSource) {
        this.beanFactory = beanFactory;
        this.jpaProperties = jpaProperties;
        this.dataSource = dataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitName("crypto-persistence-unit");
        entityManagerFactory.setPackagesToScan(
                "com.opencryptotrade.cryptocurrencyservice",
                "org.axonframework.modelling.saga.repository.jpa",
                "org.axonframework.eventsourcing.eventstore.jpa",
                "org.axonframework.eventhandling.tokenstore.jpa"
        );
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setBeanFactory(beanFactory);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>(this.jpaProperties.getProperties());
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "org.springframework.orm.hibernate5.SpringSessionContext");
        properties.put(AvailableSettings.DIALECT, "com.opencryptotrade.cryptocurrencyservice.configuration.SpecificPostgreSQLDialect");
        entityManagerFactory.setJpaPropertyMap(properties);
        entityManagerFactory.setMappingResources("jpa/persistence-override.xml");
        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
