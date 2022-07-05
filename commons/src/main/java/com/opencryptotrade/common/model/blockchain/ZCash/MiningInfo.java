package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MiningInfo implements Serializable {

    private static final long serialVersionUID = 2353528370345492815L;

    @Getter @Setter
    private int blocks;

    @Getter @Setter
    private int currentblocksize;

    @Getter @Setter
    private int currentblocktx;

    @Getter @Setter
    private BigDecimal difficulty;

    @Getter @Setter
    private String errors;

    @Getter @Setter
    private int genproclimit;

    @Getter @Setter
    private int localsolps;

    @Getter @Setter
    private float networksolps;

    @Getter @Setter
    private float networkhashps;

    @Getter @Setter
    private int pooledtx;

    @Getter @Setter
    private boolean testnet;

    @Getter @Setter
    private String chain;

    @Getter @Setter
    private boolean generate;

}
