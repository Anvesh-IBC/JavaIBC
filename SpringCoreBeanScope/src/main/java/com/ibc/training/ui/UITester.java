package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibc.training.Employee;

public class UITester {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("my_springbean.xml");
		Employee employee = (Employee) context.getBean("empobject");
		System.out.println(employee);
		Employee employee1 = (Employee) context.getBean("empobject");
		System.out.println(employee1);
		Employee employee2 = (Employee) context.getBean("empobject");
		System.out.println(employee2);
		Employee employee3 = (Employee) context.getBean("empobject");
		System.out.println(employee3);
		Employee employee4 = (Employee) context.getBean("empobject");
		System.out.println(employee4);
		employee.display();

	}

}