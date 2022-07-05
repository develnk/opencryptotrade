package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionVin implements Serializable {
    private static final long serialVersionUID = 2353528370345499815L;

    @Getter @Setter
    private String txid;

    @Getter @Setter
    private int vout;

    @Getter @Setter
    private String coinbase;

    @Getter @Setter
    private Integer sequence;

    @Getter @Setter
    private ScriptSig scriptSig;

}

class ScriptSig {

    @Getter @Setter
    private String asm;

    @Getter @Setter
    private String hex;

}
