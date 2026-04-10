package com.ibc.training.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private Double salary;

	@ManyToOne
	@JoinColumn(name = "department_id")
	@JsonManagedReference
	private Department department;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;

	@ManyToMany
	@JoinTable(name = "employee_projects", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	private List<Project> projects = new ArrayList<Project>();

	public Employee() {
	}

	public Employee(String name, String email, Double salary) {
		this.name = name;
		this.email = email;
		this.salary = salary;
	}

	// getters/setters
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		if (address != null)
			address.setEmployee(this);
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void addProject(Project p) {
		if (p != null && !projects.contains(p)) {
			projects.add(p);
			if (!p.getEmployees().contains(this))
				p.getEmployees().add(this);
		}
	}

	public void removeProject(Project p) {
		if (p != null) {
			projects.remove(p);
			p.getEmployees().remove(this);
		}
	}
}