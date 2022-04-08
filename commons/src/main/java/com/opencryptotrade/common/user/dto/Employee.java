package com.opencryptotrade.common.user.dto;

import lombok.Data;

@Data
public class Employee extends Person {

    private static final long serialVersionUID = 1L;

    private String id;

    private Long authTime;

    private String sessionId;

}
