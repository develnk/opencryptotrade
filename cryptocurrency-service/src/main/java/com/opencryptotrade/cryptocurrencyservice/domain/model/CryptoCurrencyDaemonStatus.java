package com.opencryptotrade.cryptocurrencyservice.domain.model;

public record CryptoCurrencyDaemonStatus(Integer currentHeight, Integer processedHeight, Boolean synced) {
}
