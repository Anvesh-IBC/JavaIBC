package com.ibc.training.ui;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibc.training.bean.EmployeeBean;
import com.ibc.training.service.EmployeeService;

public class UITester {
	public static void main(String[] args) {
		EmployeeService employeeService = null;
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"cst_hibernate_spring_config.xml");
		employeeService = (EmployeeService) applicationContext.getBean("employeeServiceImpl");
		addEmployee(employeeService);
//		getEmployeeDetails(employeeService);
//		updateEmployee(employeeService);
//		deleteEmployeeDetails(employeeService);
//		getEmployeeList(employeeService);
	}

	public static void getEmployeeList(EmployeeService serviceImpl) {
		try {
			List<EmployeeBean> employeeList = serviceImpl.getEmployeeList();
			System.out.println("Employee List");
			for (EmployeeBean bean : employeeList) {
				System.out.println("Id:" + bean.getId() + ",Name:" + bean.getName() + ",Role:" + bean.getRole()
						+ ",Salary:" + bean.getSalary());
				;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void deleteEmployeeDetails(EmployeeService employeeService) {
		try {
			EmployeeBean employeeBean = employeeService.deleteEmployeeDetails(1);
			if (employeeBean == null) {
				System.out.println("Invalid Employee Id");
			} else {
				System.out.println("Employee Deleted successfully with employeeId:" + employeeBean.getId());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void updateEmployee(EmployeeService employeeService) {
		try {
			EmployeeBean foundEmployeeBean = employeeService.getEmployeeDetails(1);
			if (foundEmployeeBean == null) {
				System.out.println("Invalid Employee Id");
			} else {
				foundEmployeeBean.setSalary(17000.0);
				EmployeeBean updatedBean = employeeService.updateEmployeeDetails(foundEmployeeBean);
				System.out.println("Updated Employee Details");
				System.out.println("=========================");
				System.out.println("Name:" + updatedBean.getName());
				System.out.println("Salary:" + updatedBean.getSalary());
				System.out.println("Role:" + updatedBean.getRole());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void getEmployeeDetails(EmployeeService employeeService) {
		try {
			EmployeeBean employeeBean = employeeService.getEmployeeDetails(1);
			if (employeeBean == null) {
				System.out.println("Invalid Employee Id");
			} else {
				System.out.println("Employee Details");
				System.out.println("================");
				System.out.println("Name:" + employeeBean.getName());
				System.out.println("Salary:" + employeeBean.getSalary());
				System.out.println("Role:" + employeeBean.getRole());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void addEmployee(EmployeeService employeeService) {
		EmployeeBean bean = new EmployeeBean();
		bean.setInsertTime(new Date());
		bean.setName("Anvesh");
		bean.setSalary(45000.0);
		bean.setRole("Backend Developer");

		try {
			int id = employeeService.addEmployee(bean);
			System.out.println("Employee Registered Successfully:" + id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}