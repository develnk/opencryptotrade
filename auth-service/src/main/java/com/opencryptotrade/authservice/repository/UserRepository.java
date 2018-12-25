package com.opencryptotrade.authservice.repository;

import com.opencryptotrade.authservice.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findOneByUsername(String name);
}
