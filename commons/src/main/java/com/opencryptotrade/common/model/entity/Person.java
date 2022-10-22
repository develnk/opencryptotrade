package com.opencryptotrade.common.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencryptotrade.common.model.Role;
import com.opencryptotrade.common.model.user.SystemUser;
import lombok.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class Person extends AuditableEntity {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String phoneExtension;
    private LocalDateTime lastLogin;

    private Set<Role> roles;

    @JsonIgnore
    private ProtectedUser protectedUser;

    public SystemUser toSystemUser() {
        Object id = null;
        Class clazz = this.getClass();
        try {
            Field fieldId = clazz.getDeclaredField("id");
            fieldId.setAccessible(true);
            id = fieldId.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Todo need to logging this incident.
        }

        SystemUser systemUser = new SystemUser();
        systemUser.setId(String.valueOf(id));
        systemUser.setUsername(protectedUser.getUsername());
        systemUser.setFirstName(firstName);
        systemUser.setLastName(lastName);
        systemUser.setLastLogin(lastLogin);
        systemUser.setRoles(roles);
        return systemUser;
    }

    public SystemUser toSystemUserWithPassword() {
        SystemUser systemUser = toSystemUser();
        systemUser.setPassword(protectedUser.getPassword());
        systemUser.setSalt(protectedUser.getSalt());
        return systemUser;
    }

}
