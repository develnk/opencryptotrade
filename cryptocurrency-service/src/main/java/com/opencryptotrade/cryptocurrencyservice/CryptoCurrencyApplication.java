package com.opencryptotrade.cryptocurrencyservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@Slf4j
@SpringBootApplication
public class CryptoCurrencyApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(CryptoCurrencyApplication.class, args);
    }

}
