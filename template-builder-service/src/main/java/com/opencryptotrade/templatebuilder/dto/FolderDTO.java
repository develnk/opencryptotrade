package com.opencryptotrade.templatebuilder.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Null;
import java.io.Serializable;

public class FolderDTO implements Serializable  {

    @Setter @Getter
    @Null
    private String id;

    @Setter @Getter
    private String name;

}
