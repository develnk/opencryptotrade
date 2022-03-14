package com.opencryptotrade.accountservice.controller;

import com.opencryptotrade.common.user.dto.User;
import com.opencryptotrade.common.web.MainRequestContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('REGULAR')")
    public String getUserInfo() {
        return "user info";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('SUPER')")
    public String getAdminInfo() {
        return "admin info";
    }

    @GetMapping("/me")
	public User getCurrentAccount() {
        return MainRequestContext.getUser();
	}

}
