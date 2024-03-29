package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyType;
import java.util.UUID;

public record CryptoCurrencyCreatedEvent(UUID id, String symbol, CryptoCurrencyType type, CryptoCurrencyDaemonSettings settings) {
}
