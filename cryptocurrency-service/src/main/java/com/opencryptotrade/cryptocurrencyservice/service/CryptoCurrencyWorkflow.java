package com.opencryptotrade.cryptocurrencyservice.service;

import com.opencryptotrade.cryptocurrencyservice.events.CryptoCurrencyCreatedEvent;
import com.opencryptotrade.cryptocurrencyservice.events.CryptoCurrencyUpdatedEvent;
import io.eventuate.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EventSubscriber(id="cryptoCurrencyWorkflow")
public class CryptoCurrencyWorkflow {

    @EventHandlerMethod
    public void cryptoCurrencyCreated(DispatchedEvent<CryptoCurrencyCreatedEvent> de) {
        CryptoCurrencyCreatedEvent event = de.getEvent();
        LOGGER.debug(event.getSymbol());
    }

    @EventHandlerMethod
    public void cryptoCurrencyUpdated(DispatchedEvent<CryptoCurrencyUpdatedEvent> de) {
        CryptoCurrencyUpdatedEvent event = de.getEvent();
        LOGGER.debug(event.getSettings().toString());
    }

}

