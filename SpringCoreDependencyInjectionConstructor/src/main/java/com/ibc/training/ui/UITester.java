package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibc.training.Employee;

public class UITester {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("my_springbean.xml");
		Employee employee = (Employee) applicationContext.getBean("empObject");
		System.out.println("\n\n\nOutput is :");
		employee.display();

	}

}