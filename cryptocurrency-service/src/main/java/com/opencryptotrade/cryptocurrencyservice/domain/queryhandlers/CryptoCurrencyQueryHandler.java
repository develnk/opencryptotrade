package com.opencryptotrade.cryptocurrencyservice.domain.queryhandlers;

import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyView;
import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyViewRepository;
import com.opencryptotrade.cryptocurrencyservice.domain.queries.CryptoCurrencyQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CryptoCurrencyQueryHandler {

    @Autowired
    private CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    @QueryHandler
    public Mono<CryptoCurrencyView> handle(CryptoCurrencyQuery query) {
        LOGGER.info("Handling CryptoCurrencyQuery: {}", query);
        return cryptoCurrencyViewRepository.findBySymbolAndType(query.symbol(), query.type().name());
    }
}
