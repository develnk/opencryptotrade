package com.opencryptotrade.common.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencryptotrade.common.user.model.RoleName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
public abstract class Person implements SystemUser {

    private static final long serialVersionUID = 1L;

    private String fullName;

    private String firstName;

    private String lastName;

    private String email;

    private boolean active;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<RoleName> roles;

}
