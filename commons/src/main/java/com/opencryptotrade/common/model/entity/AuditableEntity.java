package com.opencryptotrade.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditableEntity extends AuditableDeletableEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Signifies whether the entity has been soft-deleted.
     */
    @JsonIgnore // Client never sees deleted=true, so sending this is wasteful/clutter
    @Getter(AccessLevel.PUBLIC)
    protected boolean deleted;

}
