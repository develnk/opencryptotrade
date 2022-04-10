package com.opencryptotrade.cryptocurrencyservice.events;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CryptoCurrencyCreatedEvent implements CryptoCurrencyEvent {

    private String symbol;
    private CryptoCurrencyType type;
    private CryptoCurrencyDaemonSettings settings;

}
