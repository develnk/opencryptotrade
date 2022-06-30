package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import java.util.UUID;

public record CryptoCurrencyCreatedEvent(UUID id, String symbol, CryptoCurrencyType type, CryptoCurrencyDaemonSettings settings) {
}
