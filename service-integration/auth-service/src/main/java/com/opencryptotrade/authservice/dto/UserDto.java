package com.opencryptotrade.authservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter @Getter
    private long id;

    @Setter @Getter
    private String login;

    @Setter @Getter
    private String password;

    @Setter @Getter
    private String email;

    @Setter @Getter
    private List<String> role;

    @Setter @Getter
    private OffsetDateTime created;

    @Setter @Getter
    private OffsetDateTime updated;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
