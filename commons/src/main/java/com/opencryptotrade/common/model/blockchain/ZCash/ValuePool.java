package com.opencryptotrade.common.model.blockchain.ZCash;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Setter
@Getter
public class ValuePool implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Boolean monitored;
    private Float chainValue;
    private Long chainValueZat;
    private Float valueDelta;
    private Long valueDeltaZat;

}
