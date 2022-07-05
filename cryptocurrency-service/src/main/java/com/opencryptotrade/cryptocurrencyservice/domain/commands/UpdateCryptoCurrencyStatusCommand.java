package com.opencryptotrade.cryptocurrencyservice.domain.commands;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public record UpdateCryptoCurrencyStatusCommand(@TargetAggregateIdentifier UUID id, CryptoCurrencyDaemonStatus status) {
}
