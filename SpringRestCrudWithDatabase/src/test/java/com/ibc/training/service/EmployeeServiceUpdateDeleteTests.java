package com.ibc.training.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ibc.training.dto.EmployeeRequest;
import com.ibc.training.dto.EmployeeResponse;
import com.ibc.training.entity.Department;
import com.ibc.training.exception.ResourceNotFoundException;
import com.ibc.training.repository.DepartmentRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceUpdateDeleteTests {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentRepository departmentRepo;

    @Test
    void updateEmployee_success() {

        Department d = departmentRepo.save(new Department("Finance & Accounting"));

        EmployeeRequest req = new EmployeeRequest();
        req.setName("Kumar");
        req.setEmail("Kumar@example.com");
        req.setSalary(10000.0);
        req.setDepartmentId(d.getId());

        EmployeeResponse created = employeeService.create(req);

        req.setSalary(10000.0);

        EmployeeResponse updated = employeeService.update(created.getId(), req);

        assertThat(updated.getSalary()).isEqualTo(10000.0);
    }
    
    @Test
    void deleteEmployee_success() {

        Department d = departmentRepo.save(new Department("HR"));

        EmployeeRequest req = new EmployeeRequest();
        req.setName("Mike");
        req.setEmail("mike@test.com");
        req.setSalary(45000.0);
        req.setDepartmentId(d.getId());

        EmployeeResponse created = employeeService.create(req);

        employeeService.delete(created.getId());

        assertThatThrownBy(() -> employeeService.get(created.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
