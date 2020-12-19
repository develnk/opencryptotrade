package com.opencryptotrade.authservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class UserAccount implements Serializable {

    private static final long serialVersionUID = 2L;

    @Setter @Getter
    private long id;

    @Setter @Getter
    private String login;

    @Setter @Getter
    private String email;

    @Setter @Getter
    private List<String> role;

    @Setter @Getter
    private OffsetDateTime created;

    @Setter @Getter
    private OffsetDateTime updated;

}
