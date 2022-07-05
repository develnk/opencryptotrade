package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionVout implements Serializable {

    private static final long serialVersionUID = 235352837034549981L;

    @Getter @Setter
    private UUID id;

    @Getter @Setter
    private String txid;

    @Getter @Setter
    private Float value;

    @Getter @Setter
    private long valueZat;

    @Getter @Setter
    private Integer n;

    @Getter @Setter
    private ScriptPubKey scriptPubKey;

    @Getter @Setter
    private String asm;

    @Getter @Setter
    private String hex;

    @Getter @Setter
    private Integer reqSigs;

    @Getter @Setter
    private String type;

    @Getter @Setter
    private String[] addresses;

}
