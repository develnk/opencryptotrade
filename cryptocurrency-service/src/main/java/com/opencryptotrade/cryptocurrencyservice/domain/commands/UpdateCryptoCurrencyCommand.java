package com.opencryptotrade.cryptocurrencyservice.domain.commands;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;


public record UpdateCryptoCurrencyCommand(@TargetAggregateIdentifier UUID id, String symbol, CryptoCurrencyDaemonSettings cryptoCurrencyDaemonSettings) {
}
