package com.opencryptotrade.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

@Data
public abstract class AuditableDeletableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The primary ID of this entity.
     */
    @Getter(AccessLevel.PUBLIC)
    protected Object id;

    /**
     * The date/time stamp of when the entity was originally persisted.
     */
    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Getter(AccessLevel.PUBLIC)
    protected Date created;

    /**
     * The date/time stamp of when the entity was latest updated in the data store.
     */
    @LastModifiedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Getter(AccessLevel.PUBLIC)
    protected Date updated;

    /**
     * The user/principal that created this entity; may be null if the entity is created by an anonymous user when there is no logged in principal in
     * thread context.
     */
    @JsonIgnore
    @CreatedBy
    @Getter(AccessLevel.PUBLIC)
    protected Object createdBy;

    /**
     * The the user/principal that updated the entity last; may be null if the entity is created by an anonymous user when there is no logged in
     * principal in thread context.
     */
    @JsonIgnore
    @LastModifiedBy
    @Getter(AccessLevel.PUBLIC)
    protected Object updatedBy;

}
