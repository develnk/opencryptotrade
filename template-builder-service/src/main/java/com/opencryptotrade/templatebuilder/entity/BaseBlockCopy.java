package com.opencryptotrade.templatebuilder.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Document
public class BaseBlockCopy {

    @Id
    @Setter @Getter
    private ObjectId id;

    @Setter @Getter
    @Valid
    @NotNull
    private String html;

}
