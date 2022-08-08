package com.opencryptotrade.cryptocurrencyservice.domain.events;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrency;
import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyView;
import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyViewRepository;
import com.opencryptotrade.cryptocurrencyservice.domain.queries.CryptoCurrencyQuery;
import com.opencryptotrade.cryptocurrencyservice.domain.queries.FindAllCryptoCurrenciesQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CryptoCurrencyProjection {

    private final CryptoCurrencyViewRepository cryptoCurrencyViewRepository;
    private final QueryUpdateEmitter queryUpdateEmitter;
    private final TransactionalOperator operator;

    @EventHandler
    public void on(CryptoCurrencyCreatedEvent event) {
        LOGGER.info("EventHandler: Applying CryptoCurrencyCreatedEvent: {}", event);

        CryptoCurrencyView cryptoCurrencyView = new CryptoCurrencyView();
        cryptoCurrencyView.setSymbol(event.symbol());
        cryptoCurrencyView.setType(event.type().name());
        cryptoCurrencyView.setEntityId(event.id().toString());
        cryptoCurrencyViewRepository.save(cryptoCurrencyView).as(operator::transactional).then().subscribe();

//        /* sending it to subscription queries of type FindAllRoomCleaningSchedules. */
//        queryUpdateEmitter.emit(FindAllCryptoCurrenciesQuery.class,
//                query -> true);
    }

    @EventHandler
    public void on(CryptoCurrencyUpdatedEvent event) {
        LOGGER.info("EventHandler: Applying CryptoCurrencyUpdatedEvent: {}", event);

        CryptoCurrencyView cryptoCurrencyView = getCryptoCurrencyView(event.id().toString());
        cryptoCurrencyView.setSymbol(event.symbol());
        cryptoCurrencyViewRepository.save(cryptoCurrencyView).as(operator::transactional).then().subscribe();
    }

    private CryptoCurrencyView getCryptoCurrencyView(String entityId) {
        return cryptoCurrencyViewRepository.findByEntityId(entityId).block();
    }

    @QueryHandler
    public Mono<CryptoCurrencyView> handle(CryptoCurrencyQuery query) {
        LOGGER.debug("Handling CryptoCurrencyQuery: {}", query);
        return cryptoCurrencyViewRepository.findBySymbolAndType(query.symbol(), query.type().name());
    }

    @QueryHandler
    public List<CryptoCurrencyView> handle(FindAllCryptoCurrenciesQuery query) {
        LOGGER.debug("Handling CryptoCurrencyQuery findAll");
        return cryptoCurrencyViewRepository.findAll().collectList().block();
    }


}
