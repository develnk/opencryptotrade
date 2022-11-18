package com.opencryptotrade.common.user.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasAnyAuthority('ROLE_SUPER', 'ROLE_ADMIN', 'ROLE_COMPANY_ADMIN', 'ROLE_COMPANY_EMPLOYEE')")
public @interface PreAuthorizeAnyEmployee {
}
