package com.opencryptotrade.cryptocurrencyservice.service;

import com.opencryptotrade.cryptocurrencyservice.domain.*;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.sync.AggregateRepository;


public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptoCurrencyRepository;

    public CryptoCurrencyServiceImpl(AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    @Override
    public EntityWithIdAndVersion<CryptoCurrency> createCryptoCurrency(CryptoCurrencyType type, String symbol, CryptoCurrencyDaemonSettings settings) {
        return cryptoCurrencyRepository.save(new CreateCryptoCurrencyCommand(type, symbol, settings));
    }

    @Override
    public EntityWithIdAndVersion<CryptoCurrency> updateCryptoCurrency(String entityId, String symbol, CryptoCurrencyDaemonSettings settings) {
        return cryptoCurrencyRepository.update(entityId, new UpdateCryptoCurrencyCommand(symbol, settings));
    }

    @Override
    public EntityWithIdAndVersion<CryptoCurrency> updateCryptoCurrencyStatus(String entityId, CryptoCurrencyDaemonStatus status) {
        return cryptoCurrencyRepository.update(entityId, new UpdateCryptoCurrencyDaemonStatusCommand(status));
    }

}
