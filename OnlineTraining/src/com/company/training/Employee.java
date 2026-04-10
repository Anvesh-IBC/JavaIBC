package com.company.training;

public class Employee {

	private int employeeId;
	private String name;
	private double salary;
	private boolean active;
	private Department department; // One-to-One
	private Training[] trainings; // Many-to-Many
	private int trainingCount;

	public Employee() {
		trainings = new Training[5]; // fixed array
	}

	public Employee(int employeeId, String name) {
		this();
		this.employeeId = employeeId;
		this.name = name;
		this.active = true;
	}

	// Encapsulation
	public int getEmployeeId() {
		return employeeId;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getSalary() {
		return salary;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	// Many-to-Many mapping
	public void addTraining(Training training) {
		trainings[trainingCount++] = training;
	}

	public void display() {
		System.out.println("Employee ID: " + employeeId);
		System.out.println("Name: " + name);
		System.out.println("Salary: " + salary);
		System.out.println("Department: " + (department != null ? department.getName() : "None"));
		System.out.println("----------------------");
	}
}