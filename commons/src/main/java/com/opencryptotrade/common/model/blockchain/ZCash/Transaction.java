package com.opencryptotrade.common.model.blockchain.ZCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 235352837034549981L;

    @Getter @Setter
    private String hex;

    @Getter @Setter
    private String txid;

    @Getter @Setter
    private boolean overwintered;

    @Getter @Setter
    private Integer version;

    @Getter @Setter
    private String versiongroupid;

    @Getter @Setter
    private Integer locktime;

    @Getter @Setter
    private Integer expiryheight;

    @Getter @Setter
    private TransactionVin[] vin;

    @Getter @Setter
    private TransactionVout[] vout;

    @Getter @Setter
    private Vjoinsplit[] vjoinsplit;

    @Getter @Setter
    private Float valueBalance;

    @Getter @Setter
    private Object[] vShieldedSpend;

    @Getter @Setter
    private Object[] vShieldedOutput;

    @Getter @Setter
    private String blockhash;

    @Getter @Setter
    private Integer confirmations;

    @Getter @Setter
    private String time;

    @Getter @Setter
    private String blocktime;

}
