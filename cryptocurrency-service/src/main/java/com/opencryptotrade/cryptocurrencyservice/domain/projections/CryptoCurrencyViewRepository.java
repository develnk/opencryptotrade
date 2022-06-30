package com.opencryptotrade.cryptocurrencyservice.domain.projections;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptoCurrencyViewRepository extends JpaRepository<CryptoCurrencyView, Integer> {

    Optional<CryptoCurrencyView> findBySymbolAndType(String symbol, String type);

    Optional<CryptoCurrencyView> findByEntityId(String entityId);
}
