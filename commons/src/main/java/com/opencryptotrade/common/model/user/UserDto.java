package com.opencryptotrade.common.model.user;

import com.fasterxml.jackson.annotation.*;
import com.opencryptotrade.common.model.Role;
import lombok.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.security.Principal;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        value = {"id", "username", "password", "salt", "firstName", "lastName", "roles"},
        alphabetic = true)
public class UserDto implements Principal, Serializable {

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

    /**
     * Password used during login process.
     */
    @NotEmpty
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private String password;

    @NotEmpty
    private String salt;

    /**
     * First name.
     */
    private String firstName;

    /**
     * Last name.
     */
    private String lastName;

    @Getter @Setter
    private Set<Role> roles;

    public String getFullName() {
        return String.format("%s %s",
                StringUtils.isEmpty(this.firstName) ? "" :  this.firstName,
                StringUtils.isEmpty(this.lastName) ? "" :  this.lastName);
    }

    @JsonIgnore
    public boolean isCustomer() {
        return !CollectionUtils.isEmpty(this.roles) && this.roles.contains(Role.ROLE_CUSTOMER_R);
    }

    @JsonIgnore
    public boolean isAdminOrEmployee() {
        return !CollectionUtils.isEmpty(this.roles) && this.roles.stream().anyMatch(Role::isAdminOrEmployeeRole);
    }

    @Override
    public String getName() {
        return this.username;
    }

}
