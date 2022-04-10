package com.opencryptotrade.cryptocurrencyservice.domain.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class CryptoCurrencyDaemonStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer currentHeight;
    private Integer processedHeight;
    private Boolean synced;

}
