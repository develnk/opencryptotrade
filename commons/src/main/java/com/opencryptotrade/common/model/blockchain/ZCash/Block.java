package com.opencryptotrade.common.model.blockchain.ZCash;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class Block implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long height;
    private String hash;
    private Long confirmations;
    private String blockcommitments;
    private String authdataroot;
    private String chainhistoryroot;
    private String finalsaplingroot;
    private Long size;
    private Integer version;
    private String merkleroot;
    private String[] tx;
    private Integer time;
    private String nonce;
    private String bits;
    private Float difficulty;
    private String previousblockhash;
    private String nextblockhash;
    private String solution;
    private String chainwork;
    private String anchor;
    private ValuePool[] valuePools;

}
