package com.opencryptotrade.templatebuilder.dto;

import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class BaseBlockDTO implements Serializable {

    @Setter @Getter
    private Long id;

    @Setter @Getter
    private BaseBlockType type;

    @Setter @Getter
    private String html;

}
