package com.opencryptotrade.accountservice.repository;

import com.opencryptotrade.accountservice.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {

	Account findByLogin(String login);

	Account findAccountById(Long id);

}
