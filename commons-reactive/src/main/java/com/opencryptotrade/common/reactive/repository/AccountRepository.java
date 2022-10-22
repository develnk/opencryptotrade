package com.opencryptotrade.common.reactive.repository;

import com.opencryptotrade.common.reactive.model.entity.mongo.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccountRepository extends ReactiveCrudRepository<Account, String> {
}
