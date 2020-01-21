package com.opencryptotrade.accountservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class AccountUser implements Serializable {

    @Setter @Getter
    private long id;

    @Setter @Getter
    private String login;

    @Setter	@Getter
    private String firstName;

    @Setter	@Getter
    private String lastName;

    @Setter	@Getter
    private OffsetDateTime lastSeen;

    @Setter	@Getter
    private String note;

    @Setter @Getter
    private String email;

    @Setter @Getter
    private List<String> role;

    @Setter @Getter
    private OffsetDateTime created;

    @Setter @Getter
    private OffsetDateTime updated;
}
