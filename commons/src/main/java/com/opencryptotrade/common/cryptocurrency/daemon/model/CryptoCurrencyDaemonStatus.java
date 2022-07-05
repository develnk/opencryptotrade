package com.opencryptotrade.common.cryptocurrency.daemon.model;

public record CryptoCurrencyDaemonStatus(Integer currentHeight, Integer processedHeight, Boolean synced) {
}
