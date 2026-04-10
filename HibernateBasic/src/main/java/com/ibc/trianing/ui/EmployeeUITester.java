package com.ibc.trianing.ui;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibc.training.entity.EmployeeEntity;
import com.ibc.training.util.HibernateUtil;

public class EmployeeUITester {

	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmployeeId(1002);
		employee.setEmployeeName("Anvesh");
		employee.setInsertTime(new Date());
		employee.setRole("Full-stack Developer");
		employee.setSalary(800000.0);

		session.beginTransaction();
		session.save(employee);

		session.getTransaction().commit();
		System.out.println("Employee Registered Successfully");
		session.close();

		HibernateUtil.getSessionFactory().close();

	}

}
