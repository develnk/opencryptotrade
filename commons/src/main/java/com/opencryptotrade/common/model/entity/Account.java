package com.opencryptotrade.common.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class Account extends AuditableEntity {

    private static final long serialVersionUID = 1L;

    private String name;

    private String email;

    @ToString.Exclude
    private Customer customer;

    @ToString.Exclude
    private Employee manager;

    private Boolean active = true;


}
