package com.opencryptotrade.cryptocurrencyservice.configuration;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.opencryptotrade.cryptocurrencyservice"},
        entityManagerFactoryRef = "customEntityManagerFactory",
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
    @Bean
    public LocalContainerEntityManagerFactoryBean customEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitName("crypto-persistence-unit");
        entityManagerFactory.setPackagesToScan("com.opencryptotrade.cryptocurrencyservice");
        entityManagerFactory.setDataSource(dataSource);

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>(this.jpaProperties.getProperties());
        properties.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        properties.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put(AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(this.beanFactory));
        entityManagerFactory.setJpaPropertyMap(properties);

        return entityManagerFactory;
    }

    @Primary
    @Bean
    public JpaTransactionManager transactionManager(@Qualifier("customEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
