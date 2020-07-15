package com.opencryptotrade.templatebuilder.config;

import com.github.mongobee.Mongobee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongobeeConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee(connectionString);
        runner.setDbName(database);
        runner.setChangeLogsScanPackage("com.opencryptotrade.templatebuilder.dbchange");
        return runner;
    }

}
