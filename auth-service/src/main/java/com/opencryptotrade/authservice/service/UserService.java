package com.opencryptotrade.authservice.service;

import com.opencryptotrade.commons.user.domain.User;
import com.opencryptotrade.commons.user.dto.AccountDto;
import com.opencryptotrade.commons.user.dto.UserDto;

import java.util.List;

public interface UserService {

	UserDto create(UserDto user);
	User update(UserDto user);
	List<AccountDto> findAll();
	User findOne(long id);
	void delete(long id);
}
