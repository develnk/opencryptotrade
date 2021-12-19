package com.opencryptotrade.commons.user.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.annotation.PostConstruct;
import java.security.Security;

@Getter
@Setter
@Slf4j
public class JwtConfigProperties {


    /**
     * The header content prefix that describes the authentication scheme/type; Token Authentication (aka Bearer Authentication) is denoted by the
     * "Bearer" prefix in the header content string, while Basic Authentication uses the prefix "Basic".
     */
    private String prefix = "Bearer";

    @PostConstruct
    private void initLog() {
        Security.addProvider(new BouncyCastleProvider());
        LOGGER.info("JWT Configuration instantiated as: {}", this);
    }
}
