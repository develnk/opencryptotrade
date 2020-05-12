package com.opencryptotrade.templatebuilder.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;

public class FolderDTO implements Serializable  {

    @Setter @Getter
    private String id;

    @Setter @Getter
    private String name;

}
