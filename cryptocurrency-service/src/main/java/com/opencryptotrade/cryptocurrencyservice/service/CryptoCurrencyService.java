package com.opencryptotrade.cryptocurrencyservice.service;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyType;
import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CryptoCurrencyService {

    @Autowired
    private CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    public Mono<Boolean> checkCryptoCurrencyExist(String symbol, CryptoCurrencyType type) {
        return cryptoCurrencyViewRepository.findBySymbolAndType(symbol, type.toString()).map(Objects::nonNull);
    }

}
