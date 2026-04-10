package com.ibc.training.business.bean;

import java.util.Date;

public class EmployeeBean {

	private int id;
	private String name;

	private String role;

	private Date insertTime;

	private Double salary;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "EmployeeEntityBean [id=" + id + ", name=" + name + ", role=" + role + ", insertTime=" + insertTime
				+ ", salary=" + salary + "]\n";
	}

	public EmployeeBean(String name, String role, Date insertTime, Double salary) {
		this.name = name;
		this.role = role;
		this.insertTime = insertTime;
		this.salary = salary;
	}

	public EmployeeBean() {}

}
