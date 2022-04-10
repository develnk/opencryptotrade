package com.opencryptotrade.cryptocurrencyservice.webapi.request;

import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.cryptocurrencyservice.domain.model.CryptoCurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CreateCryptoCurrencyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private CryptoCurrencyType type;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;

}
