package com.opencryptotrade.cryptocurrencyservice.events;

import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "com.opencryptotrade.cryptocurrencyservice.domain.CryptoCurrency")
public interface CryptoCurrencyEvent extends Event {
}
