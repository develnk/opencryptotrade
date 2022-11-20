package com.opencryptotrade.client.service;

import com.opencryptotrade.client.util.UserCommonUtil;
import com.opencryptotrade.client.web.model.RegisterEmployee;
import com.opencryptotrade.common.model.Role;
import com.opencryptotrade.common.reactive.model.entity.mongo.Employee;
import com.opencryptotrade.common.reactive.model.entity.mongo.ProtectedUser;
import com.opencryptotrade.common.reactive.repository.EmployeeRepository;
import com.opencryptotrade.common.reactive.repository.ProtectedUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final UserCommonUtil userCommonUtil;

    private final ProtectedUserRepository protectedUserRepository;

    private final EmployeeRepository employeeRepository;

    public static final Set<Role> defaultEmployeeRole = Set.of(Role.ROLE_COMPANY_EMPLOYEE);
    public static final Set<Role> defaultAdminRole = Set.of(Role.ROLE_ADMIN, Role.ROLE_COMPANY_ADMIN);
    public static final Set<Role> defaultSuperAdminRole = Set.of(Role.ROLE_SUPER);

    @Transactional
    public Mono<Employee> employeeRegistration(RegisterEmployee registerEmployee, final Set<Role> roles) {
        ProtectedUser protectedUser = userCommonUtil.generateProtectedUser(registerEmployee.getEmail(), registerEmployee.getPassword());
        Employee employee = employeeGeneration(registerEmployee.getEmail());
        employee.setRoles(roles);
        return save(protectedUser, employee);
    }

    private static Employee employeeGeneration(final String email) {
        Employee employee = new Employee();
        employee.setActive(true);
        employee.setEmail(email);
        return employee;
    }

    private Mono<Employee> save(ProtectedUser protectedUser, Employee employee) {
        return protectedUserRepository.save(protectedUser)
                .flatMap(savedProtectedUser -> {
                    employee.setProtectedUser(savedProtectedUser);
                    return employeeRepository.save(employee);
                });
    }

}
