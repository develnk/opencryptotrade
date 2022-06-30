package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyView;
import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyViewRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CryptoCurrencyViewEventHandler {

    private final @NonNull CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    @EventHandler
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

        CryptoCurrencyView cryptoCurrencyView = getCryptoCurrencyView(event.id().toString()).orElseThrow();
        cryptoCurrencyView.setSymbol(event.symbol());
        cryptoCurrencyViewRepository.save(cryptoCurrencyView);
    }


    private Optional<CryptoCurrencyView> getCryptoCurrencyView(String entityId) {
        return cryptoCurrencyViewRepository.findByEntityId(entityId);
    }

}
