package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ibc.training.Employee;

@Configuration
@ComponentScan(basePackages = "com.ibc.training")
public class UITester {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(UITester.class);
		Employee employee = (Employee) context.getBean(Employee.class);
		employee.display();

	}

}