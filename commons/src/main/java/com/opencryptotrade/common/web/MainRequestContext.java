package com.opencryptotrade.common.web;

import com.opencryptotrade.common.user.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class MainRequestContext {

    private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<>();

    private static final String adminRoles = "ROLE_SUPER";

    private MainRequestContext() {}

    public static User getUser() {
        return CURRENT_USER.get();
    }

    public static void setCurrentUser() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            AccessToken accessToken = ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken();
            User user = User.builder()
                    .name(accessToken.getPreferredUsername())
                    .firstName(accessToken.getGivenName())
                    .secondName(accessToken.getFamilyName())
                    .email(accessToken.getEmail())
                    .id(accessToken.getId())
                    .authorities(authentication.getAuthorities())
                    .active(accessToken.isActive())
                    .build();
            CURRENT_USER.set(user);
        }
    }

    public static boolean isAdminUser() {
        User user = CURRENT_USER.get();
        return user != null && user.getAuthorities().stream().filter(role -> role.getAuthority().equalsIgnoreCase(adminRoles)).findAny().isPresent();
    }

    public static boolean isCustomerUser() {
        return false;
    }

}
