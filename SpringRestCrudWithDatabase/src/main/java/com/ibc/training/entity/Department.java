package com.ibc.training.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private List<Employee> employees = new ArrayList<Employee>();

	public Department() {
	}

	public Department(String name) {
		this.name = name;
	}

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee e) {
		if (e != null) {
			employees.add(e);
			e.setDepartment(this);
		}
	}

	public void removeEmployee(Employee e) {
		if (e != null) {
			employees.remove(e);
			e.setDepartment(null);
		}
	}
}