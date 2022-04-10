package com.opencryptotrade.cryptocurrencyservice.events;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyUpdatedEvent implements CryptoCurrencyEvent {

    private String symbol;
    private CryptoCurrencyDaemonSettings settings;

}
