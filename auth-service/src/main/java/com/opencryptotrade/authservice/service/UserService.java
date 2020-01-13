package com.opencryptotrade.authservice.service;

import com.opencryptotrade.authservice.domain.User;
import com.opencryptotrade.authservice.dto.UserDto;

import java.util.List;

public interface UserService {

	void create(UserDto user);
	List<UserDto> findAll();
	User findOne(long id);
	void delete(long id);
}
