package com.opencryptotrade.accountservice.controller;

import com.opencryptotrade.accountservice.domain.Account;
import com.opencryptotrade.accountservice.dto.AccountUser;
import com.opencryptotrade.accountservice.dto.User;
import com.opencryptotrade.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class AccountController {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Autowired
	private AccountService accountService;

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public Account getAccountByName(@PathVariable String name) {
		return accountService.findByName(name);
	}

	@RequestMapping(path = "/current", method = RequestMethod.GET)
	public Account getCurrentAccount(Principal principal) {
		return accountService.findByName(principal.getName());
	}

	@RequestMapping(path = "/current", method = RequestMethod.PUT)
	public void saveCurrentAccount(Principal principal, @Valid @RequestBody Account account) {
		accountService.saveChanges(principal.getName(), account);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Account createNewAccount(@Valid @RequestBody User user) {
		return accountService.create(user);
	}

	@PreAuthorize("#oauth2.hasScope('ui')")
	@Secured({ROLE_ADMIN})
	@RequestMapping(path = "/getAll", method = RequestMethod.GET)
	public List<AccountUser> listAccounts() {
		return accountService.allAccounts();
	}
}
