package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TxOut implements Serializable {

    private static final long serialVersionUID = 235352837034549982L;

    @Getter @Setter
    private String bestblock;

    @Getter @Setter
    private int confirmations;

    @Getter @Setter
    private float value;

    @Getter @Setter
    private ScriptPubKey scriptPubKey;

    @Getter @Setter
    private int version;

    @Getter @Setter
    private boolean coinbase;

}
