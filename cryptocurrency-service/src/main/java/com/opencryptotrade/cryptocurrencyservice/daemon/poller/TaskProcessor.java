package com.opencryptotrade.cryptocurrencyservice.daemon.poller;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrency;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ExecutionException;

@Slf4j
public abstract class TaskProcessor {

    private static final String EXCHANGE_HEADER_CRYPTOCURRENCY = "cryptocurrency";
    private static final String EXCHANGE_HEADER_PROCESSOR = "processor";

    /**
     * The name of the task-specific processor implementation. If the name is not set explicitly, the simple class name of the implementation will be
     * used.
     */
    @Setter
    private String name;

    @Handler
    public void run(@Body String cryptoCurrencyUuid, Exchange exchange) {
        Message inMessage = exchange.getIn();
        inMessage.setHeader(EXCHANGE_HEADER_CRYPTOCURRENCY, cryptoCurrencyUuid);
        inMessage.setHeader(EXCHANGE_HEADER_PROCESSOR, this.getName());
        try {
            process(cryptoCurrencyUuid, exchange);
        } catch (Throwable t) {
            logError(exchange, t);
        }
    }

    private static void logError(Exchange exchange, Throwable caughtThrowable) {
        Message message = exchange.getIn();
        LOGGER.error(String.format("***** ---> Error processing '%s' polling cycle for CryptoCurrency %s in route: %s",
                        message.getHeader(EXCHANGE_HEADER_PROCESSOR, String.class),
                        message.getHeader(EXCHANGE_HEADER_CRYPTOCURRENCY, CryptoCurrency.class).getId().toString(),
                        exchange.getFromRouteId()),
                caughtThrowable);
    }

    public abstract void process(@Body String cryptoCurrencyUuid, Exchange exchange) throws ExecutionException, InterruptedException;

    public String getName() {
        return StringUtils.isBlank(this.name) ? this.getClass().getSimpleName() : this.name;
    }
}
