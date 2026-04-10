package com.ibc.training.ui;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibc.training.business.bean.EmployeeBean;
import com.ibc.training.dao.EmployeeDAO;
import com.ibc.training.resource.JavaConfig;
import com.ibc.training.service.EmployeeService;

//name of the method is translated into query

@SuppressWarnings("rawtypes")
public class UITester {

	public static void main(String[] args) {

		EmployeeService employeeServiceIMPL = null;
		try {

//			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//					"com/accenture/lkm/resources/cst-main-config.xml");
//			employeeServiceIMPL = (EmployeeService) applicationContext.getBean("employeeServiceImpl");

			ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);
			employeeServiceIMPL = (EmployeeService) applicationContext.getBean("employeeServiceImpl");

			// getAllEmployeeDetails1(employeeServiceIMPL);

			// getAllEmployeeDetails2(employeeServiceIMPL);

			getAllEmployeeDetails3(employeeServiceIMPL);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getAllEmployeeDetails1(EmployeeService serviceImpl) {

		try {

			List<EmployeeBean> listEmployeeBean = serviceImpl.getAllEmployeesBySalary(50000.0);
			for (EmployeeBean employeeBean : listEmployeeBean) {
				System.out.println("Name: " + employeeBean.getName() + ",Salary: " + employeeBean.getSalary()
						+ ",Role: " + employeeBean.getRole());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void getAllEmployeeDetails2(EmployeeService serviceImpl) {

		try {

			List<EmployeeBean> listEmployeeBean = serviceImpl.getAllEmployeesBySalary2(50000.0);
			for (EmployeeBean employeeBean : listEmployeeBean) {
				System.out.println("Name: " + employeeBean.getName() + ",Salary: " + employeeBean.getSalary()
						+ ",Role: " + employeeBean.getRole());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void getAllEmployeeDetails3(EmployeeService serviceImpl) {

		try {

			List list = serviceImpl.getRolesAndCountOfEmployeesInEachRole();
			for (Object obj : list) {
				Object[] objArray = (Object[]) obj;
				System.out.println(objArray[0] + "," + objArray[1]);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
