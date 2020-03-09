package com.opencryptotrade.smtpservice.controller;

import com.opencryptotrade.smtpservice.dto.Smtp;
import com.opencryptotrade.smtpservice.mail.Mail;
import com.opencryptotrade.smtpservice.mail.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SmtpController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    Mail mail;

    @Autowired
    private MailConfig mailConfig;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path = "/test", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createNewAccount() {
        mail.sendSimpleMessage("prorock66@gmail.com", "Test", "Test");
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateSettings(@RequestBody Smtp data) {
        return mailConfig.updateConfiguration(data);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @Secured({ROLE_ADMIN})
    @RequestMapping(path = "/", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Smtp getSettings() {
        return mailConfig.getSettings();
    }

}
