package com.opencryptotrade.cryptocurrencyservice.service;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyType;
import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CryptoCurrencyService {

    private final CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    public Boolean checkCryptoCurrencyExist(String symbol, CryptoCurrencyType type) {
        return cryptoCurrencyViewRepository.findBySymbolAndType(symbol, type.toString()).isPresent();
    }

}
