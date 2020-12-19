package com.opencryptotrade.authservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleType name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    @JsonIgnore
    private Long createdOn;

    @Column(name = "modified_on")
    @JsonIgnore
    private Long modifiedOn;

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public Long getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }
}
