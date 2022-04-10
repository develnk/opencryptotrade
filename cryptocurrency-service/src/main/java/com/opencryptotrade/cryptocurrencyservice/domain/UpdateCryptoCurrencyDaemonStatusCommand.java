package com.opencryptotrade.cryptocurrencyservice.domain;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonStatus;
import lombok.Getter;

@Getter
public class UpdateCryptoCurrencyDaemonStatusCommand implements CryptoCurrencyCommand {

    private final CryptoCurrencyDaemonStatus status;

    public UpdateCryptoCurrencyDaemonStatusCommand(CryptoCurrencyDaemonStatus status) {
        this.status = status;
    }

}
