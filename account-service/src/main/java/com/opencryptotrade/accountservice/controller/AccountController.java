package com.opencryptotrade.accountservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AccountController {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @GetMapping("/anonymous")
    public String getAnonymousInfo() {
        return "Anonymous";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String getUserInfo() {
        return "user info";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminInfo() {
        return "admin info";
    }

	@RequestMapping(path = "/me", method = RequestMethod.GET)
	public String getCurrentAccount(Principal principal) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
	}

}
