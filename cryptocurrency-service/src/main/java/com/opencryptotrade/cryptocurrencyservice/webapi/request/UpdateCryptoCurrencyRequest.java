package com.opencryptotrade.cryptocurrencyservice.webapi.request;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class UpdateCryptoCurrencyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String entityId;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;

}
