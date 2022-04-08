package com.opencryptotrade.common.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
@Builder
public class User {

    private String id;

    private String name;

    private String firstName;

    private String secondName;

    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    private Long authTime;

    private String sessionId;

    private boolean active;

}
