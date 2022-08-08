package com.opencryptotrade.cryptocurrencyservice.configuration;

import com.opencryptotrade.cryptocurrencyservice.daemon.poller.CryptoCurrencyLookup;
import com.opencryptotrade.cryptocurrencyservice.daemon.poller.TargetCryptocurrencySplitter;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PollerConfig {

    private final ReactorQueryGateway reactorQueryGateway;

    @Bean
    public CryptoCurrencyLookup cryptoCurrencyLookup() {
        return new CryptoCurrencyLookup(reactorQueryGateway);
    }

    @Bean
    public TargetCryptocurrencySplitter targetCryptocurrencySplitter() {
        return new TargetCryptocurrencySplitter();
    }

}
