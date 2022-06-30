package com.opencryptotrade.cryptocurrencyservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Hooks;

@Slf4j
@SpringBootApplication
public class CryptoCurrencyApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(CryptoCurrencyApplication.class, args);
    }

}
