package com.opencryptotrade.templatebuilder.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class FolderDTO implements Serializable  {

    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String name;

}
