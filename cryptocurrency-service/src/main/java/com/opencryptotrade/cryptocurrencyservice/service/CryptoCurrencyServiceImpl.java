package com.opencryptotrade.cryptocurrencyservice.service;

import com.opencryptotrade.common.validator.ErrorResponse;
import com.opencryptotrade.cryptocurrencyservice.domain.*;
import com.opencryptotrade.cryptocurrencyservice.domain.command.CreateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.CryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.UpdateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.command.UpdateCryptoCurrencyDaemonStatusCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import com.opencryptotrade.cryptocurrencyservice.domain.model.entity.CryptoCurrencyView;
import com.opencryptotrade.cryptocurrencyservice.domain.persistence.CryptoCurrencyViewRepository;
import com.opencryptotrade.cryptocurrencyservice.exceptions.CryptoCurrencyDuplicateException;
import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.sync.AggregateRepository;
import java.util.Optional;


public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {

    private final AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptoCurrencyRepository;
    private final CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    public CryptoCurrencyServiceImpl(AggregateRepository<CryptoCurrency, CryptoCurrencyCommand> cryptoCurrencyRepository, CryptoCurrencyViewRepository cryptoCurrencyViewRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.cryptoCurrencyViewRepository = cryptoCurrencyViewRepository;
    }

    @Override
    public EntityWithIdAndVersion<CryptoCurrency> createCryptoCurrency(CryptoCurrencyType type, String symbol, CryptoCurrencyDaemonSettings settings) {
        Optional<CryptoCurrencyView> cryptoCurrencyView = cryptoCurrencyViewRepository.findOneCryptoCurrencyViewBySymbolAndType(symbol, type.name());
        if (cryptoCurrencyView.isEmpty()) {
            return cryptoCurrencyRepository.save(new CreateCryptoCurrencyCommand(type, symbol, settings));
        }
        else {
            throw new CryptoCurrencyDuplicateException("Found duplication");
        }
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
