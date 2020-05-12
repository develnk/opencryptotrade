package com.opencryptotrade.templatebuilder.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;

public class TriggerDTO implements Serializable {

    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String value;

    @Setter @Getter
    private String name;

    public TriggerDTO(String value, String name, Long id) {
        this.value = value;
        this.name = name;
        this.id = id;
    }
}
