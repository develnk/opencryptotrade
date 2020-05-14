package com.opencryptotrade.templatebuilder.dto;

import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BaseBlockDTO implements Serializable {

    @Setter @Getter
    @Valid
    @NotNull
    private String id;

    @Setter @Getter
    @Valid
    @NotNull
    private BaseBlockType type;

    @Setter @Getter
    @Valid
    @NotNull
    private String html;

}