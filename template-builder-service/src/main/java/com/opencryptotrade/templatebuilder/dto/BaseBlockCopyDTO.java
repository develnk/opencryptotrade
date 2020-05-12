package com.opencryptotrade.templatebuilder.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

public class BaseBlockCopyDTO implements Serializable {

    @Setter @Getter
    @Valid
    private String id;

    @Setter @Getter
    @Valid
    @NotNull
    private String html;

}
