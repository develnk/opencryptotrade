package com.opencryptotrade.common.reactive.repository;

import com.opencryptotrade.common.reactive.model.entity.mongo.Customer;
import com.opencryptotrade.common.reactive.model.entity.mongo.ProtectedUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {

    Mono<Customer> findCustomerByEmail(String email);
    Mono<Customer> findCustomerByProtectedUser(ProtectedUser protectedUser);

}
