package com.ibc.training.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ibc.training.dto.AddressRequest;
import com.ibc.training.dto.EmployeeRequest;
import com.ibc.training.dto.EmployeeResponse;
import com.ibc.training.entity.Address;
import com.ibc.training.entity.Department;
import com.ibc.training.exception.ResourceNotFoundException;
import com.ibc.training.repository.DepartmentRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AddressServiceTests {

	@Autowired
	AddressService addressService;
	
	@Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentRepository departmentRepo;

	@Test
	void createAddress_employeeNotFound() {

		AddressRequest req = new AddressRequest();
		req.setStreet("HSR");
		req.setCity("BLR");
		req.setEmployeeId(999L);

		assertThatThrownBy(() -> addressService.create(req)).isInstanceOf(ResourceNotFoundException.class);
	}
	
	@Test
	void updateAddress_success() {

		// create department first
        Department d = departmentRepo.save(new Department("IT"));

        // create employee
        EmployeeRequest empReq = new EmployeeRequest();
        empReq.setName("Ravi");
        empReq.setEmail("ravi@test.com");
        empReq.setSalary(50000.0);
        empReq.setDepartmentId(d.getId());

        EmployeeResponse employee = employeeService.create(empReq);

        // create address
        AddressRequest req = new AddressRequest();
        req.setStreet("MG Road");
        req.setCity("Hyderabad");
        req.setEmployeeId(employee.getId());

        Address a = addressService.create(req);

        // update address
        req.setCity("Bangalore");

        Address updated = addressService.update(a.getId(), req);

        assertThat(updated.getCity()).isEqualTo("Bangalore");
	}

}
