package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;

public record CryptocurrencyUpdatedStatusEvent(CryptoCurrencyDaemonStatus status) {
}
