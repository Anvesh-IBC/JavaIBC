package com.ibc.training.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer deptId;

	private String deptName;

	// One-To-Many mapping (department to employees)
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	// Utility methods for bi-directional relationship management
	public void addEmployee(Employee employee) {
		employees.add(employee);
		employee.setDepartment(this);
	}

	public void removeEmployee(Employee employee) {
		employees.remove(employee);
		employee.setDepartment(null);
	}

}
