package com.opencryptotrade.smtpservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Smtp implements Serializable {

    private static final long serialVersionUID = 2L;

    @Setter @Getter
    private String hostName;

    @Setter @Getter
    private int port;

    @Setter @Getter
    private String from;

    @Setter @Getter
    private String userName;

    @Setter @Getter
    private String password;

    @Setter @Getter
    private boolean ssl;

    @Setter @Getter
    private boolean tls;

    @Setter @Getter
    private boolean debug;

}
