package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info implements Serializable {

    private static final long serialVersionUID = 2353528370345499815L;

    @Getter @Setter
    private int version;

    @Getter @Setter
    private int protocolversion;

    @Getter @Setter
    private int walletversion;

    @Getter @Setter
    private float balance;

    @Getter @Setter
    private int blocks;

    @Getter @Setter
    private int timeoffset;

    @Getter @Setter
    private int connections;

    @Getter @Setter
    private String proxy;

    @Getter @Setter
    private float difficulty;

    @Getter @Setter
    private boolean testnet;

    @Getter @Setter
    private int keypoololdest;

    @Getter @Setter
    private int keypoolsize;

    @Getter @Setter
    private float paytxfee;

    @Getter @Setter
    private float relayfee;

    @Getter @Setter
    private String errors;

}
