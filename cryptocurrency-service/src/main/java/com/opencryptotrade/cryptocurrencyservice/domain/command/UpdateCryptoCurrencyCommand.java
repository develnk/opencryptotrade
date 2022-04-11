package com.opencryptotrade.cryptocurrencyservice.domain.command;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import lombok.Getter;

@Getter
public class UpdateCryptoCurrencyCommand implements CryptoCurrencyCommand {

    private final String symbol;
    private final CryptoCurrencyDaemonSettings cryptoCurrencyDaemonSettings;

    public UpdateCryptoCurrencyCommand(String symbol, CryptoCurrencyDaemonSettings cryptoCurrencyDaemonSettings) {
        this.symbol = symbol;
        this.cryptoCurrencyDaemonSettings = cryptoCurrencyDaemonSettings;
    }
}
