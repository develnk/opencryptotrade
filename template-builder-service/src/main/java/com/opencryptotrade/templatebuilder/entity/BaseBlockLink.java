package com.opencryptotrade.templatebuilder.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Document
public class BaseBlockLink {

    @Id
    @Setter @Getter
    private ObjectId id;

    @Setter @Getter
    @Valid
    @NotNull
    private short weight;

    @Setter @Getter
    @Valid
    @NotNull
    @DBRef
    private BaseBlock baseBlock;

    @Setter @Getter
    @Valid
    @DBRef
    private BaseBlockCopy baseBlockCopy;

    @Setter @Getter
    @Valid
    @NotNull
    private boolean editFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseBlockLink )) return false;
        return id != null && id.equals(((BaseBlockLink) o).getId());
    }

    public boolean equalsFull(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseBlockLink )) return false;
        return (weight == ((BaseBlockLink) o).getWeight())
                && (editFlag == ((BaseBlockLink) o).isEditFlag())
                && (baseBlock != null && baseBlock.equals(((BaseBlockLink) o).getBaseBlock()));
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
