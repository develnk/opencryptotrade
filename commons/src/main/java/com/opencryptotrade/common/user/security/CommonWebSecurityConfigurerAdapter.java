package com.opencryptotrade.common.user.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(101)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CommonWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

}
