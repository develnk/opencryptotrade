package com.opencryptotrade.accountservice.service;

import com.opencryptotrade.accountservice.domain.Account;
import com.opencryptotrade.accountservice.dto.AccountUser;
import com.opencryptotrade.accountservice.dto.User;

import java.util.List;

public interface AccountService {

	/**
	 * Finds account by given name
	 *
	 * @param accountName
	 * @return found account
	 */
	Account findByName(String accountName);

	/**
	 * Checks if account with the same name already exists
	 * Invokes Auth Service user creation
	 * Creates new account with default parameters
	 *
	 * @param user
	 * @return created account
	 */
	Account create(User user);

	/**
	 * Validates and applies incoming account updates
	 * Invokes Statistics Service update
	 *
	 * @param name
	 * @param update
	 */
	void saveChanges(String name, Account update);

	List<AccountUser> allAccounts();

	AccountUser updateAccountUser(AccountUser accountUser);
}
