package com.opencryptotrade.common.user.dto;

import com.fasterxml.jackson.annotation.*;
import com.opencryptotrade.common.user.model.RoleName;
import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.Principal;
import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"attributes"})
@Validated
@JsonPropertyOrder(
        value = {"id", "username", "firstName", "lastName"},
        alphabetic = true)
public class UserDto implements Serializable, OAuth2AuthenticatedPrincipal {

    private static final long serialVersionUID = 1L;

    /**
     * The unique ID (primary) of the person (employee or customer) the security user represents.
     */
    private String id;

    /**
     * Name used during login process.
     */
    @NotEmpty
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    @NotNull
    private Set<RoleName> roles;

    public String getFullName() {
        return String.format("%s %s",
                StringUtils.isEmpty(this.firstName) ? "" :  this.firstName,
                StringUtils.isEmpty(this.lastName) ? "" :  this.lastName);
    }

    @JsonIgnore
    public boolean isCustomer() {
        return !CollectionUtils.isEmpty(this.roles) && this.roles.contains(RoleName.ROLE_CUSTOMER);
    }

    @JsonIgnore
    public boolean isAdminOrEmployee() {
        return !CollectionUtils.isEmpty(this.roles) && this.roles.stream().anyMatch(RoleName::isAdminOrEmployeeRole);
    }

    @Override
    @JsonIgnore
    public String getName() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
