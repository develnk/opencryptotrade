package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import java.util.UUID;

public record CryptoCurrencyUpdatedEvent(UUID id, String symbol, CryptoCurrencyDaemonSettings settings) {
}
