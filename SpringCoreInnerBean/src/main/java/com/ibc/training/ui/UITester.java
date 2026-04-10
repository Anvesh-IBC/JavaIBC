package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibc.training.Employee;

public class UITester {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("my_springbean.xml");
		Employee employee = (Employee) applicationContext.getBean("empObject");
		employee.display();

		// cannot access inner bean directly even if the name is given
		// uncomment the below and line and run the code
		// System.out.println(applicationContext.getBean("test"));
	}

}