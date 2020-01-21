package com.opencryptotrade.accountservice.service.impl;

import com.opencryptotrade.accountservice.client.AuthServiceClient;
import com.opencryptotrade.accountservice.client.StatisticsServiceClient;
import com.opencryptotrade.accountservice.domain.Account;
import com.opencryptotrade.accountservice.dto.AccountUser;
import com.opencryptotrade.accountservice.dto.User;
import com.opencryptotrade.accountservice.dto.UserAuth;
import com.opencryptotrade.accountservice.repository.AccountRepository;
import com.opencryptotrade.accountservice.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private StatisticsServiceClient statisticsClient;

	@Autowired
	private AuthServiceClient authClient;

	@Autowired
	private AccountRepository repository;

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

		statisticsClient.updateStatistics(login, account);
	}

	@Override
	public List<AccountUser> allAccounts() {
		List<Account> accountList = (List<Account>) repository.findAll();
		List<AccountUser> accounts = authClient.getUsers().stream().map(user -> {
			Account account = accountList.stream().filter(account1 -> user.getLogin().equals(account1.getLogin())).findFirst().orElse(null);
			AccountUser accountUser = new AccountUser();
			accountUser.setEmail(user.getEmail());
			accountUser.setCreated(user.getCreated());
			accountUser.setUpdated(user.getUpdated());
			accountUser.setLogin(user.getLogin());
			accountUser.setRole(user.getRole());
			accountUser.setFirstName(account.getFirstName());
			accountUser.setLastName(account.getLastName());
			accountUser.setLastSeen(account.getLastSeen());
			accountUser.setId(account.getId());
			return accountUser;
		}).collect(Collectors.toList());

		return accounts;
	}
}
