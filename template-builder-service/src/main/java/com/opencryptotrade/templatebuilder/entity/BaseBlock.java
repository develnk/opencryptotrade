package com.opencryptotrade.templatebuilder.entity;

import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Document
public class BaseBlock {

    @Id
    @Setter @Getter
    private ObjectId id;

    @Setter @Getter
    @Valid
    @NotNull
    private BaseBlockType type;

    @Setter @Getter
    @Valid
    @NotNull
    private String html;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseBlock )) return false;
        return (id != null && id.equals(((BaseBlock) o).getId()))
                && (type != null && type.equals(((BaseBlock) o).getType()))
                && (html != null && html.equals(((BaseBlock) o).getHtml()));
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
