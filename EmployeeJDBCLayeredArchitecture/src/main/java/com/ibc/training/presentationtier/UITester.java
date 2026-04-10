package com.ibc.training.presentationtier;

import java.sql.SQLException;
import java.util.Date;

import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.service.EmployeeService;
import com.ibc.training.service.EmployeeServiceImpl;

public class UITester {

	public static void main(String[] args) {
		// insertEmployee();
		 updateEmployee();
		//readEmployee();
		//deleteEmployee();
	}

	private static void readEmployee() {
		// TODO Auto-generated method stub
		EmployeeService employeeService = new EmployeeServiceImpl();
		try {
			employeeService.readEmployee();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void updateEmployee() {
		try {
			EmployeeService employeeService = new EmployeeServiceImpl();
			EmployeeBean bean = new EmployeeBean();
			bean.setEmployeeId(1002);
			bean.setSalary(50000.00);
			employeeService.updateEmployee(bean);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void insertEmployee() {
		try {
			EmployeeService employeeService = new EmployeeServiceImpl();
			EmployeeBean bean = new EmployeeBean();
			bean.setEmployeeId(1004);
			bean.setEmployeeName("Anvesh");
			bean.setRole("Senior Developer");
			bean.setSalary(30000.00);
			bean.setInsertTime(new Date());
			employeeService.insertEmployee(bean);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private static void deleteEmployee() {
		EmployeeService employeeService = new EmployeeServiceImpl();
		EmployeeBean bean = new EmployeeBean();
		bean.setEmployeeId(1003);
		try {
			employeeService.deleteEmployee(bean);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
