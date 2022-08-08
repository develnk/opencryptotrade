package com.opencryptotrade.cryptocurrencyservice.daemon.poller;

import com.opencryptotrade.cryptocurrencyservice.domain.projections.CryptoCurrencyView;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.support.DefaultMessage;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TargetCryptocurrencySplitter {

    @Handler
    @SuppressWarnings("MethodMayBeStatic")
    public List<Message> split(@Body List<CryptoCurrencyView> cryptoCurrencies, Exchange exchange) {
        List<String> uuids = cryptoCurrencies.stream().map(CryptoCurrencyView::getEntityId).collect(Collectors.toList());

        Assert.notNull(cryptoCurrencies, "Exchange body must not be null; has to contain list of crypto currencies.");
        LOGGER.debug("Splitting exchange payload of {} crypto currencies: {}", uuids.size(), cryptoCurrencies);
        List<Message> messages = new ArrayList<>();
        for (String uuid : uuids) {
            DefaultMessage message = new DefaultMessage(exchange.getContext());
            message.setBody(uuid);
            messages.add(message);
        }

        LOGGER.debug("Camel Exchange messages after split: {} - to be processed individually.", messages.size());
        return messages;
    }

}
