package com.opencryptotrade.authservice.controller;

import com.opencryptotrade.authservice.service.UserService;
import com.opencryptotrade.commons.user.domain.User;
import com.opencryptotrade.commons.user.dto.AccountDto;
import com.opencryptotrade.commons.user.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/users"})
public class UserController {

	private final UserService userService;

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public UserDto createUser(@Valid @RequestBody UserDto user) {
		return userService.create(user);
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public User updateUser(@Valid @RequestBody UserDto user) {
		return userService.update(user);
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@GetMapping(value = "/")
	public List<AccountDto> listUsers() {
		return userService.findAll();
	}
}
