package com.opencryptotrade.client.controller;

import com.opencryptotrade.client.service.EmployeeService;
import com.opencryptotrade.client.web.model.RegisterEmployee;
import com.opencryptotrade.common.reactive.model.entity.mongo.Employee;
import com.opencryptotrade.common.user.security.annotation.PreAuthorizeAnyAdmin;
import com.opencryptotrade.common.user.security.annotation.PreAuthorizeAnyEmployeeAndAdmin;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Register employee
    @PostMapping("/register")
    public Mono<ResponseEntity<Employee>> registerEmployee(@RequestBody RegisterEmployee employee) {
        return employeeService.employeeRegistration(employee, EmployeeService.defaultEmployeeRole)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/register/admin")
    @PreAuthorizeAnyEmployeeAndAdmin
    public Mono<ResponseEntity<Employee>> registerEmployeeAdmin(@RequestBody RegisterEmployee employee) {
        return employeeService.employeeRegistration(employee, EmployeeService.defaultAdminRole)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/register/super")
    @PreAuthorizeAnyAdmin
    public Mono<ResponseEntity<Employee>> registerEmployeeSuper(@RequestBody RegisterEmployee employee) {
        return employeeService.employeeRegistration(employee, EmployeeService.defaultSuperAdminRole)
                .map(ResponseEntity::ok);
    }

}
