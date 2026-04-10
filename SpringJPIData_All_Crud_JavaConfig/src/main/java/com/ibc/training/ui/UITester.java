package com.ibc.training.ui;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ibc.training.dao.EmployeeDAO;
import com.ibc.training.entity.EmployeeEntityBean;
import com.ibc.training.resource.JavaConfig;

public class UITester {

	public static void main(String[] args) {

		EmployeeDAO employeeDAOIMPL = null;
		try {

			ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
			employeeDAOIMPL = (EmployeeDAO) applicationContext.getBean("employeeDAO");

			// 1 Add Employee
			addEmployee(employeeDAOIMPL);

			// 2 Get Employee Employee
			getEmployeeDetails(employeeDAOIMPL);

			// 3 Update Employee
           //updateEmployeeDetails(employeeDAOIMPL);

			// 4 Delete Employee
            //deleteEmployee(employeeDAOIMPL);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void addEmployee(EmployeeDAO daoimpl) {

		EmployeeEntityBean bean = new EmployeeEntityBean();
		bean.setInsertTime(new Date());
		bean.setName("Anvesh");
		bean.setRole("Sr.Developer");
		bean.setSalary(220.0);

		try {

			int id = daoimpl.save(bean).getId();
			System.out.println("Employee Registered Successfully: " + id);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void getEmployeeDetails(EmployeeDAO daoimpl) {

		try {

			EmployeeEntityBean employeeEntityBean = daoimpl.findOne(1);

			if (employeeEntityBean == null) {
				System.out.println("Invalid Employee Id");
			} else {
				System.out.println("Employee Details");
				System.out.println("================");
				System.out.println("Name: " + employeeEntityBean.getName());
				System.out.println("Salary: " + employeeEntityBean.getSalary());
				System.out.println("Role: " + employeeEntityBean.getRole());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void updateEmployeeDetails(EmployeeDAO daoimpl) {

		try {

			EmployeeEntityBean foundEmployeeEntityBean = daoimpl.findOne(1);

			if (foundEmployeeEntityBean == null) {
				System.out.println("Invalid Employee Id");
			} else {
				foundEmployeeEntityBean.setSalary(1235.4);
				daoimpl.save(foundEmployeeEntityBean); // save the updates back
				System.out.println("Updated Employee Details");
				System.out.println("========================");
				System.out.println("Name: " + foundEmployeeEntityBean.getName());
				System.out.println("Salary: " + foundEmployeeEntityBean.getSalary());
				System.out.println("Role: " + foundEmployeeEntityBean.getRole());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void deleteEmployee(EmployeeDAO daoimpl) {

		try {

			EmployeeEntityBean employeeEntityBean = daoimpl.findOne(1);

			if (employeeEntityBean == null) {
				System.out.println("Invalid Employee Id");
			} else {
				daoimpl.delete(employeeEntityBean);

				System.out.println("Employee Deleted successfully sith employeeId: " + employeeEntityBean.getId());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
