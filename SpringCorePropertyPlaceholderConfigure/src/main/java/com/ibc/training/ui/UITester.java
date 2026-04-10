package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibc.training.Employee;

public class UITester {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("my_springbean.xml");
		Employee employee = (Employee) context.getBean("empobject");
		employee.display();
	}
}