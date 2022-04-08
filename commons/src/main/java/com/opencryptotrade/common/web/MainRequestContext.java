package com.opencryptotrade.common.web;

import com.opencryptotrade.common.user.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Collection;

@Slf4j
public class MainRequestContext {

    private static final ThreadLocal<SystemUser> CURRENT_USER = new ThreadLocal<>();

    private static final String EMPLOYEE_ROLE = "ROLE_SUPER";

    private static final String CUSTOMER_ROLE = "ROLE_REGULAR";

    private MainRequestContext() {}

    public static SystemUser getUser() {
        return CURRENT_USER.get();
    }

    @SuppressWarnings("unchecked")
    public static void setCurrentUser() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            AccessToken accessToken = ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken();

            if (findUserRole(authentication.getAuthorities(), EMPLOYEE_ROLE)) {
                Employee employee = new Employee();
                employee.setFullName(accessToken.getPreferredUsername());
                employee.setFirstName(accessToken.getGivenName());
                employee.setLastName(accessToken.getFamilyName());
                employee.setEmail(accessToken.getEmail());
                employee.setActive(accessToken.isActive());
                employee.setAuthorities(authentication.getAuthorities());
                employee.setId(accessToken.getId());
                employee.setAuthTime(accessToken.getAuth_time());
                employee.setSessionId(accessToken.getSessionId());
                CURRENT_USER.set(employee);
            } else {
                Customer customer = new Customer();
                customer.setFullName(accessToken.getPreferredUsername());
                customer.setFirstName(accessToken.getGivenName());
                customer.setLastName(accessToken.getFamilyName());
                customer.setEmail(accessToken.getEmail());
                customer.setActive(accessToken.isActive());
                CURRENT_USER.set(customer);
            }
        }
    }

    public static boolean isAdminUser() {
        SystemUser systemUser = CURRENT_USER.get();
        return systemUser != null && findUserRole(systemUser.getAuthorities(), EMPLOYEE_ROLE);
    }

    public static boolean isCustomerUser() {
        SystemUser systemUser = CURRENT_USER.get();
        return systemUser != null && findUserRole(systemUser.getAuthorities(), CUSTOMER_ROLE);
    }

    private static boolean findUserRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream().filter(r -> r.getAuthority().equalsIgnoreCase(role)).findAny().isPresent();
    }
}
