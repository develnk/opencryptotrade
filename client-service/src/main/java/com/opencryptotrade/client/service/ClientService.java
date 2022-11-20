package com.opencryptotrade.client.service;

import com.opencryptotrade.client.util.UserCommonUtil;
import com.opencryptotrade.client.web.model.RegisterClient;
import com.opencryptotrade.common.model.Role;
import com.opencryptotrade.common.reactive.model.entity.mongo.Account;
import com.opencryptotrade.common.reactive.model.entity.mongo.Customer;
import com.opencryptotrade.common.reactive.model.entity.mongo.ProtectedUser;
import com.opencryptotrade.common.reactive.repository.AccountRepository;
import com.opencryptotrade.common.reactive.repository.CustomerRepository;
import com.opencryptotrade.common.reactive.repository.ProtectedUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClientService {

    private final ProtectedUserRepository protectedUserRepository;

    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    private final UserCommonUtil userCommonUtil;

    private final Set<Role> customerRole = Set.of(Role.ROLE_CUSTOMER_RW);

    @Transactional
    public Mono<Customer> clientRegistration(RegisterClient client) {
        ProtectedUser protectedUser = userCommonUtil.generateProtectedUser(client.getEmail(), client.getPassword());
        Customer customer = new Customer();
        customer.setEmail(client.getEmail());
        customer.setFirstName(client.getFirstName());
        customer.setLastName(client.getLastName());
        customer.setRoles(customerRole);

        return protectedUserRepository.save(protectedUser)
                .flatMap(savedProtectedUser -> {
                    customer.setProtectedUser(savedProtectedUser);
                    return customerRepository.save(customer)
                            .doOnNext(savedCustomer -> {
                                // Todo need to remove this account creation to another service
                                Account account = new Account();
                                account.setCustomer(savedCustomer);
                                accountRepository.save(account).subscribe();
                            });
        });
    }

}
