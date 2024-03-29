package com.opencryptotrade.client.controller;

import com.opencryptotrade.client.service.ClientService;
import com.opencryptotrade.client.service.EmployeeService;
import com.opencryptotrade.client.web.model.RegisterClient;
import com.opencryptotrade.client.web.model.RegisterEmployee;
import com.opencryptotrade.common.reactive.model.entity.mongo.Customer;
import com.opencryptotrade.common.reactive.model.entity.mongo.Employee;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/register")
    public Mono<ResponseEntity<Customer>> register(@RequestBody RegisterClient client) {
        return clientService.clientRegistration(client)
                .map(ResponseEntity::ok);
    }

}
