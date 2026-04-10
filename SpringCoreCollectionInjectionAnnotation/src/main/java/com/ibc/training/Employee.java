package com.ibc.training;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Employee {
	@Value("10001")
	private int empid;
	@Value("Guru")
	private String ename;
	@Value("2500000")
	private double salary;
	private Address address;

	private List<String> phoneNumbers;

	private Set<String> skills;

	private Map<String, String> courses;

	private Properties additionalInfo;

	public Employee() {
	}

	public Employee(Address address) {
		this.address = address;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	@Autowired
	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public Set<String> getSkills() {
		return skills;
	}

	@Autowired
	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}

	public Map<String, String> getCourses() {
		return courses;
	}

	@Autowired
	public void setCourses(Map<String, String> courses) {
		this.courses = courses;
	}

	public Properties getAdditionalInfo() {
		return additionalInfo;
	}

	@Autowired
	public void setAdditionalInfo(Properties additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Address getAddress() {
		return address;
	}

	@Autowired
	public void setAddress(Address address) {
		System.out.println("From Setter of Employee Address..");
		this.address = address;

	}

	public void display() {
		System.out.println("\nEmployee Details are:");
		System.out.println("Employee Name: " + ename);

		System.out.println("Employee ID: " + empid);

		System.out.println("Salary: " + salary);

		System.out.println("Address Line 1: " + (address != null ? address.getAddressLine1() : "No Address"));

		System.out.println("Address Line 2: " + (address != null ? address.getAddressLine2() : "No Address"));

		System.out.println("Phone Numbers: " + phoneNumbers);

		System.out.println("Skills: " + skills);

		System.out.println("Courses: " + courses);

		System.out.println("Additional Info: " + additionalInfo);

	}

}