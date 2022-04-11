package com.opencryptotrade.cryptocurrencyservice.domain.command;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import lombok.Getter;

@Getter
public class CreateCryptoCurrencyCommand implements CryptoCurrencyCommand {

    private final CryptoCurrencyType type;
    private final String symbol;
    private final CryptoCurrencyDaemonSettings cryptoCurrencyDaemonSettings;

    public CreateCryptoCurrencyCommand(CryptoCurrencyType type, String symbol, CryptoCurrencyDaemonSettings cryptoCurrencyDaemonSettings) {
        this.type = type;
        this.symbol = symbol;
        this.cryptoCurrencyDaemonSettings = cryptoCurrencyDaemonSettings;
    }
}
