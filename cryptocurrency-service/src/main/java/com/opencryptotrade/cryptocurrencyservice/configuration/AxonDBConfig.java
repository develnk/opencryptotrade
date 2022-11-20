package com.opencryptotrade.cryptocurrencyservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class AxonDBConfig {

    @Value("${spring.datasource.axon.driver-class-name}")
    private String driverName;

    @Value("${spring.datasource.axon.url}")
    private String url;

    @Value("${spring.datasource.axon.username}")
    private String userName;

    @Value("${spring.datasource.axon.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(userName);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

}
