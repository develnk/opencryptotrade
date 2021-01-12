package com.opencryptotrade.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class AccountDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long id;

    @NotNull
    private String login;

    private String firstName;

    private String lastName;

    private String password;

    private OffsetDateTime lastSeen;

    private String note;

    @NotNull
    private String email;

    @NotNull
    private List<String> role;

    private OffsetDateTime created;

    private OffsetDateTime updated;
}
