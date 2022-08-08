package com.opencryptotrade.cryptocurrencyservice.daemon;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrency;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CoinDaemons {

    private static volatile CoinDaemons instance;
    private final Map<CryptoCurrency, Thread> daemons = new ConcurrentHashMap<>(20);
//    private final CryptoCurrencyViewRepository cryptoCurrencyViewRepository;

    private CoinDaemons() {
        LOGGER.debug("Start coin daemons");
    }


}
