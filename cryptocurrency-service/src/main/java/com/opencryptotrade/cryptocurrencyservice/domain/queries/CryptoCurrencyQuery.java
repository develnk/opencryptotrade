package com.opencryptotrade.cryptocurrencyservice.domain.queries;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;

import java.util.UUID;

public record CryptoCurrencyQuery(UUID id, String symbol, CryptoCurrencyType type) {
}
