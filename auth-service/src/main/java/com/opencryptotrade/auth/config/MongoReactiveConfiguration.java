package com.opencryptotrade.auth.config;

import com.mongodb.*;
import com.mongodb.connection.netty.NettyStreamFactoryFactory;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@EnableReactiveMongoRepositories(basePackages = {"com.opencryptotrade.*"})
public class MongoReactiveConfiguration {

   @Value("${spring.data.mongodb.user}")
    private String user;
    @Value("${spring.data.mongodb.password}")
    private String password;
    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private Integer port;
    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.authSource}")
    private String authSource;

    private String authMode;

    @Bean
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(this.user, this.authSource, this.password.toCharArray());
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> {
                    builder.hosts(Arrays.asList(
                            new ServerAddress(host, port)
                    ));
                })
                .streamFactoryFactory(NettyStreamFactoryFactory.builder().build())
                .credential(credential)
                .codecRegistry(codecRegistry)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoDatabaseFactory());
    }

    @Bean
    public ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory() {
        return new SimpleReactiveMongoDatabaseFactory(mongoClient(), this.database);
    }

}