package com.ibc.training.dto;

import jakarta.validation.constraints.*;

public class EmployeeCreateDto {
	@NotBlank(message = "name is required")
	private String name;

	@Email
	@NotBlank
	private String email;

	@NotNull
	@Positive
	private Double salary;

	private Long departmentId; // optional
	private String street; // optional
	private String city; // optional
	
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
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
		
}