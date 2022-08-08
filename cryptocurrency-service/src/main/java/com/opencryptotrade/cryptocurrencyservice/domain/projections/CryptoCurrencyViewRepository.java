package com.opencryptotrade.cryptocurrencyservice.domain.projections;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CryptoCurrencyViewRepository extends ReactiveCrudRepository<CryptoCurrencyView, Integer> {

    Mono<CryptoCurrencyView> findBySymbolAndType(String symbol, String type);

    Mono<CryptoCurrencyView> findByEntityId(String entityId);
}
