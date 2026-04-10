package com.ibc.training.ui;

import java.util.List;
import java.util.Scanner;

import com.ibc.training.business.EmployeeBean;
import com.ibc.training.service.EmployeeService;
import com.ibc.training.service.EmployeeServiceImpl;

public class UITester {

	public static final Scanner scanner = new Scanner(System.in);
	private static final EmployeeService employeeService = new EmployeeServiceImpl();

	public static void main(String[] args) {
		while (true) {
			System.out.println("\n---Employee Management System Menu---");
			System.out.println("1.Add Employee");
			System.out.println("2.List Employees");
			System.out.println("3.Search Employee by ID");
			System.out.println("4.Update Employee");
			System.out.println("5.Delete Employee");
			System.out.println("6.Exit your choice:");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				addEmployee();
				break;
			case 2:
				listEmployees();
				break;
			case 3:
				searchEmployee();
				break;
			case 4:
				updateEmployee();
				break;
			case 5:
				deleteEmployee();
				break;
			case 6:
				System.out.println("Exiting application.");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Choice. Try again.");
			}
		}
	}

	private static void addEmployee() {
		EmployeeBean emp = new EmployeeBean();
		System.out.println("Enter Employee ID:");
		emp.setEmpId(scanner.nextInt());
		scanner.nextLine();
		System.out.println("Enter Employee Name:");
		emp.setEmpName(scanner.nextLine());
		System.out.println("Enter Email:");
		emp.setEmail(scanner.nextLine());
		System.out.println("Enter Role:");
		emp.setRole(scanner.nextLine());
		System.out.println("Enter Salary:");
		emp.setSalary(scanner.nextDouble());
		scanner.nextLine();

		employeeService.addEmployee(emp);
		System.out.println("Employee added successfully.");
	}

	private static void listEmployees() {
		List<EmployeeBean> employees = employeeService.getEmployeeList();
		for (EmployeeBean emp : employees) {
			System.out.println(emp.getEmpId() + "|" + emp.getEmpName() + "|" + emp.getEmail() + "|" + emp.getRole()
					+ "|" + emp.getSalary());
		}
	}

	private static void searchEmployee() {
		System.out.println("Enter Employee ID to search:");
		int id = scanner.nextInt();
		scanner.nextLine();

		EmployeeBean emp = employeeService.getEmployeeById(id);
		if (emp != null) {
			System.out.println("Found:" + emp.getEmpName() + ",Email:" + emp.getEmail());
		} else {
			System.out.println("Employee not found");
		}
	}

	private static void updateEmployee() {
		System.out.println("Enter Employee Id to Update");
		int id = scanner.nextInt();
		scanner.nextLine();

		EmployeeBean emp = employeeService.getEmployeeById(id);
		if (emp == null) {
			System.out.println("Employeenot found.");
			return;
		}
		System.out.print("Enter new Employee Name:");
		emp.setEmpName(scanner.nextLine());
		System.out.print("Enter new Email:");
		emp.setEmail(scanner.nextLine());
		System.out.print("Enter new Role:");
		emp.setRole(scanner.nextLine());
		System.out.print("Enter new Salary:");
		emp.setSalary(scanner.nextDouble());
		scanner.nextLine();

		employeeService.updateEmployee(emp);
		System.out.println("Employee updated successfully");

	}

	private static void deleteEmployee() {
		System.out.println("Enter Employee ID to delete");
		int id = scanner.nextInt();
		scanner.nextLine();

		employeeService.deleteEmployee(id);
		System.out.println("Employee deleted if existed.");
	}
}
