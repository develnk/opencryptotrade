package com.opencryptotrade.accountservice.controller;

import com.opencryptotrade.accountservice.domain.Account;
import com.opencryptotrade.accountservice.dto.AccountDto;
import com.opencryptotrade.accountservice.service.AccountService;
import com.opencryptotrade.commons.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AccountController {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	private final AccountService accountService;

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
	public void saveCurrentAccount(Principal principal, @Valid @RequestBody AccountDto account) {
		accountService.saveChanges(principal.getName(), account);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public Account createNewAccount(@Valid @RequestBody UserDto user) {
		return accountService.create(user);
	}

	@PreAuthorize("#oauth2.hasScope('ui')")
	@Secured({ROLE_ADMIN})
	@RequestMapping(path = "/getAll", method = RequestMethod.GET)
	public List<AccountDto> listAccounts() {
		return accountService.allAccounts();
	}

	@PreAuthorize("#oauth2.hasScope('ui')")
	@Secured({ROLE_ADMIN})
	@RequestMapping(path = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object updateAccountUser(@Valid @RequestBody AccountDto accountDto) {
		return accountService.updateAccount(accountDto);
	}
}
