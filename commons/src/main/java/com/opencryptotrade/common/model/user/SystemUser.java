package com.opencryptotrade.common.model.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.opencryptotrade.common.model.Role;
import com.opencryptotrade.common.user.UserConfigurationException;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.apache.commons.collections4.CollectionUtils;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(
        value = {"id", "username", "password", "salt", "firstName", "lastName", "roles"},
        alphabetic = true)
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;
    private String password;
    private String salt;
    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;
    private Set<Role> roles;


    public void addRole(Role role) {
        if (CollectionUtils.isEmpty(roles)) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public Role getSingleRole() {
        if (CollectionUtils.isEmpty(roles)) {
            throw new UserConfigurationException(String.format("Expected a user (ID=%d) to have 1 role, found none.", id));
        }
        if (roles.size() != 1) {
            throw new UserConfigurationException(String.format("Expected a user (ID=%d) to have 1 role, found many : %s", id, roles));
        }
        return roles.iterator().next();
    }


    public void setSingleRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        } else {
            roles.clear();
        }
        if (role != null) {
            roles.add(role);
        }
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (CollectionUtils.isNotEmpty(this.roles)) {
            for (Role role : this.roles) {
                authorities.add(new SimpleGrantedAuthority(role.name()));
            }
        }
        return authorities;
    }

    public Set<String> getAuthorityNames() {
        return getGrantedAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthorityNames().contains(authorityName.toUpperCase(Locale.US));
    }

    public boolean hasAnyOfAuthorities(String... authorityNames) {
        Set<String> allAuthorities = getAuthorityNames();
        return Arrays.stream(authorityNames).anyMatch(allAuthorities::contains);
    }

    public boolean isAdmin() {
        return hasAnyOfAuthorities(Role.ROLE_ADMIN.name(), Role.ROLE_COMPANY_ADMIN.name());
    }

    public boolean isCustomer() {
        return hasAnyOfAuthorities(Role.ROLE_CUSTOMER_R.name(), Role.ROLE_CUSTOMER_RW.name(), Role.ROLE_CUSTOMER_SUSPENDED.name());
    }

    public UserDto toDto() {
        UserDto dto = new UserDto();
        dto.setId(id);
        dto.setUsername(username);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        if (CollectionUtils.isNotEmpty(roles)) {
            dto.setRoles(roles);
        }
        return dto;
    }

    public UserDto toDtoIncludePassword() {
        UserDto dto = toDto();
        dto.setPassword(password);
        dto.setSalt(salt);
        return dto;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}
