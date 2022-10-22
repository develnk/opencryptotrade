package com.opencryptotrade.auth.service;

import com.opencryptotrade.common.reactive.repository.CustomerRepository;
import com.opencryptotrade.common.reactive.repository.EmployeeRepository;
import com.opencryptotrade.common.reactive.repository.ProtectedUserRepository;
import com.opencryptotrade.auth.commons.user.model.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemUserDetailsService implements ReactiveUserDetailsService {

    private final ProtectedUserRepository protectedUserRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return customerRepository.findCustomerByEmail(username)
                .map(customer -> new SystemUserDetails(customer.toSystemUserWithPassword()))
                .switchIfEmpty(Mono.defer(() -> employeeRepository.findEmployeeByEmail(username)
                        .switchIfEmpty(Mono.error(new UsernameNotFoundException("Unable to find user")))
                        .map(employee -> new SystemUserDetails(employee.toSystemUserWithPassword()))))
                .cast(UserDetails.class);
    }

}
