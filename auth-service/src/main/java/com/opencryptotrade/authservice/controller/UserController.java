package com.opencryptotrade.authservice.controller;

import com.opencryptotrade.authservice.domain.User;
import com.opencryptotrade.authservice.dto.UserAccount;
import com.opencryptotrade.authservice.dto.UserDto;
import com.opencryptotrade.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(method = RequestMethod.POST)
	public UserDto createUser(@Valid @RequestBody UserDto user) {
		return userService.create(user);
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(method = RequestMethod.PUT)
	public User updateUser(@Valid @RequestBody UserDto user) {
		return userService.update(user);
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@GetMapping
	public List<UserAccount> listUsers() {
		return userService.findAll();
	}
}
