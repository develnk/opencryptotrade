package com.opencryptotrade.cryptocurrencyservice.daemon.poller.processor;

import com.opencryptotrade.cryptocurrencyservice.daemon.poller.TaskProcessor;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.messaging.GenericMessage;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CryptoCurrencyWatchNewBlock extends TaskProcessor {

    private final EventSourcingRepository<CryptoCurrency> eventSourcingRepository;

    @Override
    @SuppressWarnings("unchecked")
    public void process(String cryptoCurrencyUuid, Exchange exchange) {
        LOGGER.info("CryptoCurrencyProcessor started");
        DefaultUnitOfWork<?> uow = DefaultUnitOfWork.startAndGet(new GenericMessage(cryptoCurrencyUuid));
        CryptoCurrency cryptoCurrency = eventSourcingRepository.load(cryptoCurrencyUuid).getWrappedAggregate().getAggregateRoot();
        cryptoCurrencyWatch(cryptoCurrency);
        uow.commit();
        LOGGER.info("***** Finished processing metadata. ******");
    }

    private void cryptoCurrencyWatch(CryptoCurrency cryptoCurrency) {

    }

}
