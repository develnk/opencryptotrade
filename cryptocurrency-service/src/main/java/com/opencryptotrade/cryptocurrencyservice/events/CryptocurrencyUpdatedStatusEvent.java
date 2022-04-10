package com.opencryptotrade.cryptocurrencyservice.events;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CryptocurrencyUpdatedStatusEvent implements CryptoCurrencyEvent {

    private CryptoCurrencyDaemonStatus status;

}
