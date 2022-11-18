package com.opencryptotrade.common.model;

import java.util.EnumSet;
import java.util.Set;

public enum Role {

    ROLE_VISITOR,
    ROLE_SUPER,
    ROLE_ADMIN,
    ROLE_COMPANY_ADMIN,
    ROLE_COMPANY_EMPLOYEE,
    ROLE_CUSTOMER_R,
    ROLE_CUSTOMER_RW,
    ROLE_CUSTOMER_SUSPENDED;

    private static final Set<Role> adminOrEmployees = EnumSet.of(
            ROLE_SUPER,
            ROLE_ADMIN,
            ROLE_COMPANY_ADMIN,
            ROLE_COMPANY_EMPLOYEE
    );

    private static final Set<Role> customerRoles = EnumSet.of(
            ROLE_CUSTOMER_R, ROLE_CUSTOMER_RW, ROLE_CUSTOMER_SUSPENDED
    );

    /**
     * Indicates whether this role is a company admin.
     */
    public boolean isAdminRole() {
        return this == ROLE_ADMIN || this == ROLE_COMPANY_ADMIN || this == ROLE_SUPER;
    }

    public boolean isAdminOrEmployeeRole() {
        return adminOrEmployees.contains(this);
    }

    public static boolean isAdminOrEmployeeRole(String name) {
        return adminOrEmployees.stream().anyMatch(role -> role.name().equals(name));
    }

    public static boolean isCustomerRole(String name) {
        return customerRoles.stream().map(Role::name).anyMatch(roleName -> roleName.equals(name));
    }

    public static String[] getAdminRoleNames() {
        return new String[] { ROLE_COMPANY_ADMIN.name(), ROLE_ADMIN.name() };
    }
}
