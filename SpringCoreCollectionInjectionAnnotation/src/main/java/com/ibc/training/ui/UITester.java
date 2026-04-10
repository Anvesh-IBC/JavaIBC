package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ibc.training.Address;
import com.ibc.training.Employee;
import com.ibc.training.config.AppConfig;

public class UITester {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, Employee.class, Address.class);
		Employee employee = (Employee) context.getBean(Employee.class);
		employee.display();

	}

}