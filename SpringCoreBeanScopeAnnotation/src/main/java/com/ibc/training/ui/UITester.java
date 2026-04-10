package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ibc.training.Employee;

@Configuration
@ComponentScan("com.ibc.training")

public class UITester {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(UITester.class);
		Employee employee = (Employee) context.getBean(Employee.class);

		System.out.println(employee);
		Employee employee1 = (Employee) context.getBean(Employee.class);
		System.out.println(employee1);
		Employee employee2 = (Employee) context.getBean(Employee.class);
		System.out.println(employee2);
		Employee employee3 = (Employee) context.getBean(Employee.class);
		System.out.println(employee3);
		Employee employee4 = (Employee) context.getBean(Employee.class);
		System.out.println(employee4);
		employee.display();

	}

}