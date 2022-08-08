package com.opencryptotrade.cryptocurrencyservice.daemon.poller;

import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyView;
import com.opencryptotrade.cryptocurrencyservice.domain.queries.FindAllCryptoCurrenciesQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;

@Slf4j
@RequiredArgsConstructor
public class CryptoCurrencyLookup {

    private final ReactorQueryGateway reactorQueryGateway;

    @Handler
    public void findAllRegisteredCryptoCurrency(Exchange exchange) {
        exchange.getIn().setBody(reactorQueryGateway.query(new FindAllCryptoCurrenciesQuery(),  ResponseTypes.multipleInstancesOf(CryptoCurrencyView.class)).block());
    }
}
