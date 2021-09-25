package com.opencryptotrade.accountservice.service.impl;

import com.opencryptotrade.accountservice.domain.Account;
import com.opencryptotrade.accountservice.dto.AccountDto;
import com.opencryptotrade.accountservice.repository.AccountRepository;
import com.opencryptotrade.accountservice.service.AccountService;
import com.opencryptotrade.authservice.service.impl.UserServiceImpl;
import com.opencryptotrade.commons.user.dto.UserDto;
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

	private final UserServiceImpl authClient;

	private final AccountRepository accountRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account findByName(String login) {
		Assert.hasLength(login);
		return accountRepository.findByLogin(login);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account create(UserDto user) {
		Account existing = accountRepository.findByLogin(user.getLogin());
		Assert.isNull(existing, "account already exists: " + user.getLogin());

		UserDto userAuth = authClient.create(user);
		Account account = new Account();
		account.setLogin(userAuth.getLogin());
		account.setFirstName(user.getFirstName());
		account.setLastName(user.getLastName());
		account.setLastSeen(OffsetDateTime.now());
		account.setNote(user.getNote());
		accountRepository.save(account);

		log.info("New account has been created: " + account.getLogin());
		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveChanges(String login, AccountDto accountDto) {
		Account account = accountRepository.findByLogin(login);
		Assert.notNull(account, "Can't find account with login: " + login);

		account.setNote(accountDto.getNote());
		account.setLastSeen(OffsetDateTime.now());
		accountRepository.save(account);

		log.debug("Account {} changes has been saved", login);
	}

	@Override
	public List<AccountDto> allAccounts() {
		List<Account> accountList = (List<Account>) accountRepository.findAll();
		List<AccountDto> accounts = authClient.findAll().stream().map(user -> {
			Account account = accountList.stream().filter(account1 -> user.getLogin().equals(account1.getLogin())).findFirst().orElse(null);
			if (account == null) {
				return null;
			}
			AccountDto accountDto = new AccountDto();
			accountDto.setId(account.getId());
			accountDto.setEmail(user.getEmail());
			accountDto.setCreated(user.getCreated());
			accountDto.setUpdated(user.getUpdated());
			accountDto.setLogin(user.getLogin());
			accountDto.setRole(user.getRole());
			accountDto.setFirstName(account.getFirstName());
			accountDto.setLastName(account.getLastName());
			accountDto.setNote(account.getNote());
			accountDto.setLastSeen(account.getLastSeen());
			return accountDto;
		}).collect(Collectors.toList());

		accounts.removeAll(Collections.singleton(null));
		return accounts;
	}

	@Override
	public AccountDto updateAccount(AccountDto accountDto) {
		OffsetDateTime dateTime = OffsetDateTime.now();
		Account account = accountRepository.findAccountById(accountDto.getId());
		Assert.notNull(account, "Can't find account with account id: " + accountDto.getId());

		account.setFirstName(accountDto.getFirstName());
		account.setLastName(accountDto.getLastName());
//		account.setLogin(accountUser.getLogin());
		account.setNote(accountDto.getNote());
		account.setLastSeen(dateTime);
		accountRepository.save(account);
		accountDto.setUpdated(dateTime);

		UserDto user = new UserDto();
		user.setPassword(accountDto.getPassword());
		user.setLogin(accountDto.getLogin());
		user.setNote(account.getNote());
		user.setEmail(accountDto.getEmail());
		user.setRole(accountDto.getRole());
		authClient.update(user);
		return accountDto;
	}
}
