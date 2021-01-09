package com.opencryptotrade.commons.user.repository;

import com.opencryptotrade.commons.user.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
}
