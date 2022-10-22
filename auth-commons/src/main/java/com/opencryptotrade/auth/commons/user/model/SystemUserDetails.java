package com.opencryptotrade.auth.commons.user.model;

import com.opencryptotrade.common.model.user.SystemUser;
import com.opencryptotrade.common.model.user.UserDto;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
public class SystemUserDetails extends UserDto implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    public SystemUserDetails(SystemUser user) {
        super(user.getId(), user.getUsername(), user.getPassword(), user.getSalt(), user.getFirstName(), user.getLastName(), user.getRoles());
        this.grantedAuthorities.addAll(user.getGrantedAuthorities());
    }


    public Set<String> getGrantedAuthorityNames() {
        return grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toCollection(() -> new HashSet<>(grantedAuthorities.size())));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(grantedAuthorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
