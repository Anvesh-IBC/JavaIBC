package com.ibc.training.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ibc.training.dto.DepartmentRequest;
import com.ibc.training.entity.Department;

@SpringBootTest
@ActiveProfiles("test")
public class DepartmentServiceTests {

	@Autowired
	DepartmentService departmentService;

	@Test
	void createDepartment_success() {

		DepartmentRequest req = new DepartmentRequest();
		req.setName("Marketing");

		Department d = departmentService.create(req);

		assertThat(d.getId()).isNotNull();

		Department fetched = departmentService.get(d.getId());

		assertThat(fetched.getName()).isEqualTo("Marketing");
	}

	@Test
	void listDepartments_success() {

		DepartmentRequest req = new DepartmentRequest();
		req.setName("Finance");

		departmentService.create(req);

		List<Department> list = departmentService.list();

		assertThat(list).isNotEmpty();
	}

}
