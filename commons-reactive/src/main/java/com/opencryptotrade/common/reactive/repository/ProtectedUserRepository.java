package com.opencryptotrade.common.reactive.repository;

import com.opencryptotrade.common.reactive.model.entity.mongo.ProtectedUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProtectedUserRepository extends ReactiveCrudRepository<ProtectedUser, String> {

    Mono<ProtectedUser> findByUsername(String username);
}
