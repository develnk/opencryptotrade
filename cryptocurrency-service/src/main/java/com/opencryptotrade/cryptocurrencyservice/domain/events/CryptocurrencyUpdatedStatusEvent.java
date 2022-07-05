package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonStatus;

public record CryptocurrencyUpdatedStatusEvent(CryptoCurrencyDaemonStatus status) {
}
