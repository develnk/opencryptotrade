package com.opencryptotrade.cryptocurrencyservice.domain.model;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonStatus;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyType;
import com.opencryptotrade.cryptocurrencyservice.domain.commands.CreateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.commands.UpdateCryptoCurrencyCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.commands.UpdateCryptoCurrencyStatusCommand;
import com.opencryptotrade.cryptocurrencyservice.domain.events.CryptoCurrencyCreatedEvent;
import com.opencryptotrade.cryptocurrencyservice.domain.events.CryptoCurrencyUpdatedEvent;
import com.opencryptotrade.cryptocurrencyservice.domain.events.CryptocurrencyUpdatedStatusEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.io.Serial;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate(snapshotTriggerDefinition = "cryptoCurrencySnapshotTriggerDefinition", cache = "cache")
@Slf4j
@Getter
public class CryptoCurrency {

    @Serial
    private static final long serialVersionUID = 1L;

    @AggregateIdentifier
    private UUID id;
    private CryptoCurrencyType type;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;
    private CryptoCurrencyDaemonStatus status;

    private CryptoCurrency() {
    }

    @CommandHandler
    public CryptoCurrency(CreateCryptoCurrencyCommand command) {
        LOGGER.info("Handling CreateCryptoCurrencyCommand: {}", command);
        //Some command validation
        apply(new CryptoCurrencyCreatedEvent(command.id(), command.symbol(), command.type(), command.cryptoCurrencyDaemonSettings()));
    }

    @CommandHandler
    public static void handle(UpdateCryptoCurrencyCommand command) {
        LOGGER.info("Handling UpdateCryptoCurrencyCommand: {}", command);
        //Some command validation
        // ...
        apply(new CryptoCurrencyUpdatedEvent(command.id(), command.symbol(), command.cryptoCurrencyDaemonSettings()));
    }

    @CommandHandler
    public static void handle(UpdateCryptoCurrencyStatusCommand command) {
        LOGGER.info("Handling UpdateCryptoCurrencyDaemonStatusCommand: {}", command);
        //Some command validation
        // ...
        apply(new CryptocurrencyUpdatedStatusEvent(command.status()));
    }

    @EventSourcingHandler
    public void on(CryptoCurrencyCreatedEvent event) {
        LOGGER.info("Applying CryptoCurrencyCreatedEvent: {}", event);

        id = event.id();
        type = event.type();
        symbol = event.symbol();
        settings = event.settings();
    }

    @EventSourcingHandler
    public void on(CryptoCurrencyUpdatedEvent event) {
        LOGGER.info("Applying CryptoCurrencyUpdatedEvent: {}", event);

        id = event.id();
        symbol = event.symbol();
        settings = event.settings();
    }

    @EventSourcingHandler
    public void on(CryptocurrencyUpdatedStatusEvent event) {
        LOGGER.info("Applying CryptocurrencyUpdatedStatusEvent: {}", event);
        status = event.status();
    }

}
