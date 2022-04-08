package com.opencryptotrade.common.user.dto;

import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;
import java.util.Collection;

public interface SystemUser extends Serializable {

    String getFirstName();
    String getLastName();
    String getFullName();
    Collection<? extends GrantedAuthority> getAuthorities();
}
