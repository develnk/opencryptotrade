package com.opencryptotrade.cryptocurrencyservice.webapi.request;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCryptoCurrencyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;

}
