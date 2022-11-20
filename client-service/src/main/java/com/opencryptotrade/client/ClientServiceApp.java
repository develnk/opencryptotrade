package com.opencryptotrade.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import reactor.core.publisher.Hooks;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
public class ClientServiceApp {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(ClientServiceApp.class, args);
    }

}