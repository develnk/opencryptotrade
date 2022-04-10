package com.opencryptotrade.cryptocurrencyservice.domain.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class CryptoCurrencyDaemonSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private String host;
    private Integer port;
    private String userName;
    private String password;

}
