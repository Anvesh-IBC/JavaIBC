package com.ibc.training.ui;

import com.ibc.training.businessbean.DepartmentBean;
import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.service.EmployeeService;
import com.ibc.training.utility.Factory;
import com.ibc.training.utility.HibernateUtil;

public class UITester {
	public static void main(String[] args) {
		try {
			insertEmployeeAndDepartment();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			HibernateUtil.shutdown();
		}
	}

	static void insertEmployeeAndDepartment() {
		try {
			EmployeeService service = Factory.createEmployeeService();

			DepartmentBean department = new DepartmentBean();
			department.setDepartmentName("LKM");
			department.setCity("Hyderabad");

			EmployeeBean employee1 = new EmployeeBean();
			employee1.setEmployeeName("Anvesh");
			employee1.setRole("Sr.Developer");

			EmployeeBean employee2 = new EmployeeBean();
			employee2.setEmployeeName("Kumar");
			employee2.setRole("Sr.Analyst");

			service.insertEmployeeAndDepartment(employee1, employee2, department);
			System.out.println("success- done!!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
