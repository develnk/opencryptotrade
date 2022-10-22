package com.opencryptotrade.common.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Employee extends Person {

    @ToString.Include
    private boolean active = true;

}
