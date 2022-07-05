package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {

    private static final long serialVersionUID = 2353528370345499816L;

    @Getter @Setter
    private String address;

    @Getter @Setter
    private String txid;

    @Getter @Setter
    private String txidVin;

    @Getter @Setter
    private int value;

    @Getter @Setter
    private int vin;

    @Getter @Setter
    private int spent;

    @Getter @Setter
    private int n;

    @Getter @Setter
    private long height;

    @Getter @Setter
    private long vinHeight;

    @Getter @Setter
    private String hex;

}
