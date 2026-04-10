package com.ibc.training.service;

import com.ibc.training.dto.EmployeeRequest;
import com.ibc.training.dto.EmployeeResponse;
import com.ibc.training.entity.Department;
import com.ibc.training.entity.Project;
import com.ibc.training.repository.DepartmentRepository;
import com.ibc.training.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeServiceFlowTests {

	@Autowired
	EmployeeService employeeService;
	@Autowired
	DepartmentRepository departmentRepo;
	@Autowired
	ProjectRepository projectRepo;

	private Department dept;
	private Project p1, p2;

	@BeforeEach
	void init() {
		dept = departmentRepo.save(new Department("Engineering"));
		p1 = projectRepo.save(new Project("Project A"));
		p2 = projectRepo.save(new Project("Project B"));
	}

	@Test
	void create_assignInlineAddressAndProjects_thenGet_andDelete() {
		EmployeeRequest req = new EmployeeRequest();
		req.setName("Jyothi");
		req.setEmail("jyothi@example.com");
		req.setSalary(95000.0);
		req.setDepartmentId(dept.getId());
		req.setAddressStreet("HSR Layout");
		req.setAddressCity("Bengaluru");
		req.setProjectIds(Arrays.asList(p1.getId(), p2.getId()));

		EmployeeResponse created = employeeService.create(req);
		assertThat(created.getId()).isNotNull();
		assertThat(created.getDepartmentId()).isEqualTo(dept.getId());
		assertThat(created.getProjectNames()).containsExactlyInAnyOrder("Project A", "Project B");
		assertThat(created.getAddressCity()).isEqualTo("Bengaluru");

		// assign more projects
		EmployeeResponse afterAssign = employeeService.assignProjects(created.getId(), Arrays.asList(p1.getId()));
		assertThat(afterAssign.getProjectNames()).contains("Project A");

		// remove one
		EmployeeResponse afterRemove = employeeService.removeProjects(created.getId(), Arrays.asList(p2.getId()));
		assertThat(afterRemove.getProjectNames()).doesNotContain("Project B");

		// exists by email
		assertThat(employeeService.existsByEmail("jyothi@example.com")).isTrue();

		// fetch
		EmployeeResponse got = employeeService.get(created.getId());
		assertThat(got.getName()).isEqualTo("Jyothi");

		// delete (also removes address + join rows)
		employeeService.delete(created.getId());
		assertThrows(RuntimeException.class, () -> employeeService.get(created.getId()));
	}
}