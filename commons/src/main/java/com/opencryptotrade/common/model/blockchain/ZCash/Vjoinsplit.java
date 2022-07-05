package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vjoinsplit implements Serializable {

    private static final long serialVersionUID = 2353528370341499815L;

    @Getter @Setter
    private Float vpub_old;

    @Getter @Setter
    private Float vpub_new;

    @Getter @Setter
    private String anchor;

    @Getter @Setter
    private String[] nullifiers;

    @Getter @Setter
    private String[] commitments;

    @Getter @Setter
    private String onetimePubKey;

    @Getter @Setter
    private String randomSeed;

    @Getter @Setter
    private String[] macs;

    @Getter @Setter
    private String proof;

    @Getter @Setter
    private String[] ciphertexts;

}
