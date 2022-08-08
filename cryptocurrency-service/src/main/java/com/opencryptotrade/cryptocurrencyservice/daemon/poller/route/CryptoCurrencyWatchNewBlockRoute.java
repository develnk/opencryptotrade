package com.opencryptotrade.cryptocurrencyservice.daemon.poller.route;

import com.opencryptotrade.cryptocurrencyservice.daemon.poller.PollingRoute;
import com.opencryptotrade.cryptocurrencyservice.daemon.poller.processor.CryptoCurrencyWatchNewBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@ConfigurationProperties(prefix = "cryptocurrency.monitor")
@Slf4j
public class CryptoCurrencyWatchNewBlockRoute extends PollingRoute {

    private static final String uri = String.format("quartz://cryptoCurrency/newBlock?cron=0/10+*+*+*+*+?&triggerStartDelay=%d",
            ThreadLocalRandom.current().nextLong(5L, 30L));

    public CryptoCurrencyWatchNewBlockRoute(CryptoCurrencyWatchNewBlock taskProcessor) {
        super(taskProcessor);
        super.setFromEndpointUri(uri);
    }

}
