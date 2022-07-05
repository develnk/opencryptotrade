package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockSubsidy implements Serializable {

    private static final long serialVersionUID = 2353528370345499825L;

    @Getter @Setter
    private float miner;

    @Getter @Setter
    private float founders;

}
