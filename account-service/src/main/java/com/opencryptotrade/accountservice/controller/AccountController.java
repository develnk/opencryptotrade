package com.opencryptotrade.accountservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    @GetMapping("/user")
    @PreAuthorize("hasRole('MANAGER')")
    public String getUserInfo() {
        return "user info";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('SUPER')")
    public String getAdminInfo() {
        return "admin info";
    }

}
