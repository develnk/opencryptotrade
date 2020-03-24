package com.opencryptotrade.templatebuilder.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class EmailTemplateDTO  implements Serializable {

    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String name;

    @Setter  @Getter
    private String subject;

    @Setter @Getter
    private String body;

    @Setter @Getter
    private Long folder;

    @Setter @Getter
    private String trigger;

}
