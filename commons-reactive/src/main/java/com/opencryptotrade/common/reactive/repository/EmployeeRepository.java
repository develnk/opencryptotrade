package com.opencryptotrade.common.reactive.repository;

import com.opencryptotrade.common.reactive.model.entity.mongo.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

    Mono<Employee> findEmployeeByEmail(String email);

}
