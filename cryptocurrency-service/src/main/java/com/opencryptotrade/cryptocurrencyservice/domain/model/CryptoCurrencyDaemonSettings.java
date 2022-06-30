package com.opencryptotrade.cryptocurrencyservice.domain.model;

public record CryptoCurrencyDaemonSettings(String host, Integer port, String userName, String password) {
}
