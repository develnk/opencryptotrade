package com.opencryptotrade.smtpservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class TriggerDTO implements Serializable {

    @Setter @Getter
    private String value;

    @Setter @Getter
    private String name;

    @Setter @Getter
    private Integer id;

    public TriggerDTO(String value, String name, Integer id) {
        this.value = value;
        this.name = name;
        this.id = id;
    }
}
