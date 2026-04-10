package com.ibc.training;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("empobject")
public class Employee {

	@Value("#{myAddress}") // SpEL===> Spring expression language
	private Address address;

	@Value("1001")
	private Integer employeeId;

	@Value("1111")
	private Double salary;

	@Value("MSD")
	private String employeeName;

	public Employee() {
		System.out
				.println("From Constructor of Employee: Created the Employee Object and injected the Address Object\n");
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		System.out.println("From setter of adderss");
		this.address = address;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		System.out.println("FRom setter of EmployeeId");
		this.employeeId = employeeId;
	}

	public Double getSalary() {
		return salary;
	}

	// @Value("200000")
	public void setSalary(Double salary) {
		System.out.println("From setter of Salary" + salary);
		this.salary = salary;
	}

	// @Value("Changed MSD from getter") //does not works
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void display() {
		System.out.println("\nEmployee Details are:");
		System.out.println("Employee ID:" + this.employeeId);
		System.out.println("Emloyee Name: " + this.employeeName);
		System.out.println("Employee Salaray:" + this.salary);
		System.out.println("\nAddress line1:" + this.address.getAddressLine1());
		System.out.println("Address line2:" + this.address.getAddressLine2());
	}
}