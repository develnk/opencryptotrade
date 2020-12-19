package com.opencryptotrade.authservice.service;

import com.opencryptotrade.authservice.domain.User;
import com.opencryptotrade.authservice.dto.UserAccount;
import com.opencryptotrade.authservice.dto.UserDto;

import java.util.List;

public interface UserService {

	UserDto create(UserDto user);
	User update(UserDto user);
	List<UserAccount> findAll();
	User findOne(long id);
	void delete(long id);
}
