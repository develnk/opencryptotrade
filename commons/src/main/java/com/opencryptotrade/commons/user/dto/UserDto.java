package com.opencryptotrade.commons.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private List<String> role;

    private OffsetDateTime created;

    private OffsetDateTime updated;

    private String note;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
