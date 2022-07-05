package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScriptPubKey implements Serializable {

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
