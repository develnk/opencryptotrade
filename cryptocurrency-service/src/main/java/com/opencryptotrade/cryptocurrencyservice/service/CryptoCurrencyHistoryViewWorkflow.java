package com.opencryptotrade.cryptocurrencyservice.service;

import com.opencryptotrade.cryptocurrencyservice.domain.model.entity.CryptoCurrencyView;
import com.opencryptotrade.cryptocurrencyservice.domain.persistence.CryptoCurrencyViewRepository;
import com.opencryptotrade.cryptocurrencyservice.events.CryptoCurrencyCreatedEvent;
import com.opencryptotrade.cryptocurrencyservice.events.CryptoCurrencyUpdatedEvent;
import io.eventuate.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@EventSubscriber(id="cryptoCurrencyHistoryViewWorkflow")
public class CryptoCurrencyHistoryViewWorkflow {

    private final CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    public CryptoCurrencyHistoryViewWorkflow(CryptoCurrencyViewRepository cryptoCurrencyViewRepository) {
        this.cryptoCurrencyViewRepository = cryptoCurrencyViewRepository;
    }

    @EventHandlerMethod
    @Transactional
    public void createCryptoCurrency(DispatchedEvent<CryptoCurrencyCreatedEvent> de) {
        CryptoCurrencyCreatedEvent event = de.getEvent();
        CryptoCurrencyView newCrypto =  new CryptoCurrencyView();
        newCrypto.setSymbol(event.getSymbol());
        newCrypto.setType(event.getType().name());
        cryptoCurrencyViewRepository.save(newCrypto);
        LOGGER.debug(event.getSymbol());
    }

    @EventHandlerMethod
    public void updateCryptoCurrency(DispatchedEvent<CryptoCurrencyUpdatedEvent> de) {
        CryptoCurrencyUpdatedEvent event = de.getEvent();
        LOGGER.debug(event.getSettings().toString());
    }

}

