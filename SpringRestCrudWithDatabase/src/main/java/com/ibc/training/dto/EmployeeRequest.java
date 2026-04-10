package com.ibc.training.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class EmployeeRequest {

	@NotBlank
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotNull
	@DecimalMin("0.0")
	private Double salary;

	@NotNull
	private Long departmentId;

	// Optional - create/update Address inline
	private String addressStreet;
	private String addressCity;

	// Optional project ids to assign
	private List<Long> projectIds;

	public EmployeeRequest() {
	}

	// getters/setters
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

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}
}