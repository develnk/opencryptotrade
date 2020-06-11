package com.opencryptotrade.templatebuilder.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TriggerDTO implements Serializable {

    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String value;

    @Setter @Getter
    private String name;

}
