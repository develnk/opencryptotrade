package com.opencryptotrade.accountservice.service.impl;

import com.opencryptotrade.accountservice.client.AuthServiceClient;
import com.opencryptotrade.accountservice.domain.Account;
import com.opencryptotrade.accountservice.dto.AccountUser;
import com.opencryptotrade.accountservice.dto.User;
import com.opencryptotrade.accountservice.dto.UserAuth;
import com.opencryptotrade.accountservice.repository.AccountRepository;
import com.opencryptotrade.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AuthServiceClient authClient;

	private final AccountRepository repository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByName(String login) {
		Assert.hasLength(login);
		return repository.findByLogin(login);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account create(User user) {
		Account existing = repository.findByLogin(user.getLogin());
		Assert.isNull(existing, "account already exists: " + user.getLogin());

		UserAuth userAuth = authClient.createUser(user);
		Account account = new Account();
		account.setLogin(userAuth.getLogin());
		account.setFirstName(user.getFirstName());
		account.setLastName(user.getLastName());
		account.setLastSeen(OffsetDateTime.now());
		account.setNote(user.getNote());
		repository.save(account);

		log.info("New account has been created: " + account.getLogin());
		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveChanges(String login, Account update) {
		Account account = repository.findByLogin(login);
		Assert.notNull(account, "Can't find account with login: " + login);

		account.setNote(update.getNote());
		account.setLastSeen(OffsetDateTime.now());
		repository.save(account);

		log.debug("Account {} changes has been saved", login);
	}

	@Override
	public List<AccountUser> allAccounts() {
		List<Account> accountList = (List<Account>) repository.findAll();
		List<AccountUser> accounts = authClient.getUsers().stream().map(user -> {
			Account account = accountList.stream().filter(account1 -> user.getLogin().equals(account1.getLogin())).findFirst().orElse(null);
			if (account == null) {
				return null;
			}
			AccountUser accountUser = new AccountUser();
			accountUser.setId(account.getId());
			accountUser.setEmail(user.getEmail());
			accountUser.setCreated(user.getCreated());
			accountUser.setUpdated(user.getUpdated());
			accountUser.setLogin(user.getLogin());
			accountUser.setRole(user.getRole());
			accountUser.setFirstName(account.getFirstName());
			accountUser.setLastName(account.getLastName());
			accountUser.setNote(account.getNote());
			accountUser.setLastSeen(account.getLastSeen());
			return accountUser;
		}).collect(Collectors.toList());

		accounts.removeAll(Collections.singleton(null));
		return accounts;
	}

	@Override
	public AccountUser updateAccountUser(AccountUser accountUser) {
		Account account = repository.findAccountById(accountUser.getId());
		Assert.notNull(account, "Can't find account with account id: " + accountUser.getId());

		account.setFirstName(accountUser.getFirstName());
		account.setLastName(accountUser.getLastName());
//		account.setLogin(accountUser.getLogin());
		account.setNote(accountUser.getNote());
		account.setLastSeen(OffsetDateTime.now());
		repository.save(account);

		// Update account user object.
		accountUser.setLastSeen(account.getLastSeen());

		User user = new User();
		user.setLogin(accountUser.getLogin());
		user.setEmail(accountUser.getEmail());
		user.setRole(accountUser.getRole());
		UserAuth updatedUserAuth = authClient.updateUser(user);
		accountUser.setUpdated(updatedUserAuth.getUpdated());
		return accountUser;
	}
}
