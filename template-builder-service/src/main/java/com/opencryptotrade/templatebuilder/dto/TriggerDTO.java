package com.opencryptotrade.templatebuilder.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class TriggerDTO implements Serializable {

    @Setter @Getter
    private String value;

    @Setter @Getter
    private String name;

    public TriggerDTO(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
