package com.company.training;

public class Department {

	private String name;
	private Employee[] employees;
	private int count;

	public Department(String name) {
		this.name = name;
		employees = new Employee[10];
	}

	public void addEmployee(Employee e) {
		employees[count++] = e;
		e.setDepartment(this);
	}

	public String getName() {
		return name;
	}

	public void showEmployees() {
		for (int i = 0; i < count; i++) {
			employees[i].display();
		}
	}
}