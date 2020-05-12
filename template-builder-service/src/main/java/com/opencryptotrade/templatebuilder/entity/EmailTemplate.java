package com.opencryptotrade.templatebuilder.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Document
public class EmailTemplate {

    @Id
    @Setter @Getter
    private ObjectId id;

    @Setter @Getter
    @Valid
    @NotNull
    private String name;

    @Setter @Getter
    @Valid
    @NotNull
    private String subject;

    @Setter @Getter
    @Valid
    @NotNull
    @DBRef
    private Folder folder;

    @Setter @Getter
    @Valid
    @NotNull
    private Integer trigger;

    @Setter @Getter
    @Valid
    @NotNull
    @DBRef
    private Set<BaseBlockLink> baseBlockLinks = new HashSet<>();

    public void addBaseBlockLink(BaseBlockLink baseBlockLink) {
        baseBlockLinks.add(baseBlockLink);
    }

    public void removeBaseBlockLink(BaseBlockLink baseBlockLink) {
        baseBlockLinks.remove(baseBlockLink);
    }

}
