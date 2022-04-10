package com.opencryptotrade.cryptocurrencyservice.configuration;

import com.opencryptotrade.cryptocurrencyservice.domain.CryptoCurrency;
import com.opencryptotrade.cryptocurrencyservice.domain.CryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.service.CryptoCurrencyService;
import com.opencryptotrade.cryptocurrencyservice.service.CryptoCurrencyServiceImpl;
import io.eventuate.sync.AggregateRepository;
import io.eventuate.sync.EventuateAggregateStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoCurrencyConfiguration {

    @Bean
    public CryptoCurrencyService cryptoCurrencyService(AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptocurrencyRepository) {
        return new CryptoCurrencyServiceImpl(cryptocurrencyRepository);
    }

    @Bean
    public AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptocurrencyRepository(EventuateAggregateStore eventStore) {
        return new AggregateRepository<>(CryptoCurrency.class, eventStore);
    }
}
