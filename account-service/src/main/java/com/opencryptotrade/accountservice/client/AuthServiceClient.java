package com.opencryptotrade.accountservice.client;

import com.opencryptotrade.accountservice.dto.User;
import com.opencryptotrade.accountservice.dto.UserAuth;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/uaa/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	UserAuth createUser(User user);

	@RequestMapping(method = RequestMethod.PUT, value = "/uaa/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	UserAuth updateUser(User user);

	@RequestMapping(method = RequestMethod.GET, value = "/uaa/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	List<UserAuth> getUsers();
}
