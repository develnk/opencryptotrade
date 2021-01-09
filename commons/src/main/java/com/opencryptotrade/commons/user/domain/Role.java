package com.opencryptotrade.commons.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Roles")
@Getter
@Setter
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

}
