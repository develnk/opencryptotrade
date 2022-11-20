package com.opencryptotrade.common.user.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasAnyAuthority('ROLE_SUPER')")
public @interface PreAuthorizeSuper {
}
