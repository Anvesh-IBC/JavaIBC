package com.ibc.training.jpa;

import com.ibc.training.entity.*;
import com.ibc.training.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

//import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class MappingTests {

	@Autowired
	DepartmentRepository departmentRepo;
	@Autowired
	EmployeeRepository employeeRepo;
	@Autowired
	ProjectRepository projectRepo;
	@Autowired
	AddressRepository addressRepo;

	@Test
	void oneToMany_manyToOne_Department_Employee() {
		Department d = new Department("IT");
		// d = departmentRepo.save(d);

		Employee e = new Employee("Ravi", "ravi@test.com", 50000.0);
		// e.setDepartment(d);
		d.addEmployee(e);
		// e = employeeRepo.save(e);
		d = departmentRepo.saveAndFlush(d);

		Employee fetched = departmentRepo.findById(d.getId()).orElseThrow().getEmployees().get(0);
		assertThat(fetched.getDepartment().getId()).isEqualTo(d.getId());

		// orphanRemoval when employee removed from department's collection
		Department depFetched = departmentRepo.findById(d.getId()).orElseThrow();
		assertThat(depFetched.getEmployees()).contains(fetched);

		depFetched.removeEmployee(fetched);
		departmentRepo.saveAndFlush(depFetched);
		// employee should now be detached from department; depending on orphanRemoval
		// semantics,
		// with your mapping orphanRemoval=true on Department.employees, removing from
		// the collection
		// deletes the Employee row:
		assertThat(employeeRepo.findById(e.getId())).isEmpty();
	}

	@Test
	void oneToOne_Employee_Address_owningSideIsAddress() {
		Employee e = new Employee("Ravi", "ravi@test.com", 50000.0);
		Address a = new Address("MG Road", "Hyderabad");
		e.setAddress(a); // sets both sides
		e = employeeRepo.save(e); // cascade should persist Address too

		Employee got = employeeRepo.findById(e.getId()).orElseThrow();
		assertThat(got.getAddress()).isNotNull();
		Address persistedAddress = addressRepo.findById(got.getAddress().getId()).orElseThrow();
		assertThat(persistedAddress.getEmployee().getId()).isEqualTo(e.getId());

		// delete employee -> address removed due to orphanRemoval
		employeeRepo.delete(got);
		assertThat(addressRepo.findById(persistedAddress.getId())).isEmpty();
	}

	@Test
	void manyToMany_Employee_Project_joinTable() {
		Project p1 = projectRepo.save(new Project("Alpha"));
		Project p2 = projectRepo.save(new Project("Beta"));

		Employee e = new Employee("Mary", "mary@example.com", 70000.0);
		e.addProject(p1);
		e.addProject(p2);
		e = employeeRepo.save(e);

		Employee got = employeeRepo.findById(e.getId()).orElseThrow();
		assertThat(got.getProjects()).extracting(Project::getName).containsExactlyInAnyOrder("Alpha", "Beta");

		// remove one project and verify join table change
		got.removeProject(p1);
		employeeRepo.save(got);
		Employee after = employeeRepo.findById(e.getId()).orElseThrow();
		assertThat(after.getProjects()).extracting(Project::getName).containsExactly("Beta");
	}
}