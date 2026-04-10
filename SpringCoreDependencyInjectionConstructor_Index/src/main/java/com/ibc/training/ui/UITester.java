package com.ibc.training.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibc.training.Contact;

public class UITester {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("my_springbean.xml");
		Contact primaryContact = (Contact) applicationContext.getBean("primaryContact");
		System.out.println(primaryContact);

	}

}