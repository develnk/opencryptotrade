package com.opencryptotrade.cryptocurrencyservice.domain.commands;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyType;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record  CreateCryptoCurrencyCommand(@TargetAggregateIdentifier UUID id, CryptoCurrencyType type, String symbol, CryptoCurrencyDaemonSettings cryptoCurrencyDaemonSettings) {
}
