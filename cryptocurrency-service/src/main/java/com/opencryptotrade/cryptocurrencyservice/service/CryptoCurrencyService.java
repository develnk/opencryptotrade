package com.opencryptotrade.cryptocurrencyservice.service;

import com.opencryptotrade.cryptocurrencyservice.domain.CryptoCurrency;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import io.eventuate.EntityWithIdAndVersion;

public interface CryptoCurrencyService {

    EntityWithIdAndVersion<CryptoCurrency> createCryptoCurrency(CryptoCurrencyType type, String symbol, CryptoCurrencyDaemonSettings settings);

    EntityWithIdAndVersion<CryptoCurrency> updateCryptoCurrency(String entityId, String symbol, CryptoCurrencyDaemonSettings settings);

    EntityWithIdAndVersion<CryptoCurrency> updateCryptoCurrencyStatus(String entityId, CryptoCurrencyDaemonStatus status);

}
