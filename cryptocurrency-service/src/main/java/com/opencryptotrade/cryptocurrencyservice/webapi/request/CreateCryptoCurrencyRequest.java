package com.opencryptotrade.cryptocurrencyservice.webapi.request;

import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyDaemonSettings;
import com.opencryptotrade.common.cryptocurrency.daemon.model.CryptoCurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCryptoCurrencyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private CryptoCurrencyType type;
    private String symbol;
    private CryptoCurrencyDaemonSettings settings;

}
