package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyView;
import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CryptoCurrencyViewEventHandler {

    @Autowired
    private CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    @EventHandler
    @Transactional
    public void cryptoCurrencyCreatedEventHandler(CryptoCurrencyCreatedEvent event) {
        LOGGER.info("EventHandler: Applying CryptoCurrencyCreatedEvent: {}", event);

        CryptoCurrencyView cryptoCurrencyView = new CryptoCurrencyView();
        cryptoCurrencyView.setSymbol(event.symbol());
        cryptoCurrencyView.setType(event.type().name());
        cryptoCurrencyView.setEntityId(event.id().toString());
        cryptoCurrencyViewRepository.save(cryptoCurrencyView);
    }

    @EventHandler
    @Transactional
    public void cryptoCurrencyUpdatedEventHandler(CryptoCurrencyUpdatedEvent event) {
        LOGGER.info("EventHandler: Applying CryptoCurrencyUpdatedEvent: {}", event);

        CryptoCurrencyView cryptoCurrencyView = getCryptoCurrencyView(event.id().toString());
        cryptoCurrencyView.setSymbol(event.symbol());
        cryptoCurrencyViewRepository.save(cryptoCurrencyView);
    }


    private CryptoCurrencyView getCryptoCurrencyView(String entityId) {
        return cryptoCurrencyViewRepository.findByEntityId(entityId).block();
    }

}
