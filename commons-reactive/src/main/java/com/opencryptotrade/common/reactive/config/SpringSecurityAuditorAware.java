package com.opencryptotrade.common.reactive.config;

import com.opencryptotrade.common.model.user.SystemUser;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

public class SpringSecurityAuditorAware implements ReactiveAuditorAware<SystemUser> {

    @Override
    public Mono<SystemUser> getCurrentAuditor() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(SystemUser.class::cast);
    }
}
