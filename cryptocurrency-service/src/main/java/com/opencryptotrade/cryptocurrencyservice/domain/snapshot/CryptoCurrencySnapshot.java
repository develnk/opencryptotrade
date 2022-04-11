package com.opencryptotrade.cryptocurrencyservice.domain.snapshot;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import io.eventuate.Snapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
class CryptoCurrencySnapshot implements Snapshot {

    private CryptoCurrencyType type;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;
    private CryptoCurrencyDaemonStatus status;

}
