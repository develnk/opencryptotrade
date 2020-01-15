package com.opencryptotrade.authservice.controller;

import com.opencryptotrade.authservice.dto.UserDto;
import com.opencryptotrade.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public Principal getUser(Principal principal) {
		return principal;
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(method = RequestMethod.POST)
	public void createUser(@Valid @RequestBody UserDto user) {
		userService.create(user);
	}

	@PreAuthorize("#oauth2.hasAnyScope('server', 'ui')")
	@Secured({ROLE_ADMIN})
	@GetMapping
	public List<UserDto> listUsers() {
		return userService.findAll();
	}
}
