package com.opencryptotrade.accountservice.confguration;

import com.opencryptotrade.common.user.security.KeycloakSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class AccountSecurityConfiguration extends KeycloakSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().mvcMatchers("/api2/anonymous").permitAll();
    }

}
