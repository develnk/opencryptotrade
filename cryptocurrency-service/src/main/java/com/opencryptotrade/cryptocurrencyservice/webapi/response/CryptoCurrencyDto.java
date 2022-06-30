package com.opencryptotrade.cryptocurrencyservice.webapi.response;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CryptoCurrencyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;

}
