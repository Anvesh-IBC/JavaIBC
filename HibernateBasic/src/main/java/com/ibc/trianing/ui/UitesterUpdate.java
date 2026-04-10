package com.ibc.trianing.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibc.training.entity.EmployeeEntity;
import com.ibc.training.util.HibernateUtil;

public class UitesterUpdate {
	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		EmployeeEntity employee = (EmployeeEntity) session.get(EmployeeEntity.class, 1002);
		if (employee != null) {
			session.beginTransaction();
			employee.setSalary(900000.0);
			session.update(employee);
			session.getTransaction().commit();
			System.out.println("Employee Updted Successfully!");
		} else {
			System.out.println("Employee is not found!");
		}
		session.close();
		HibernateUtil.getSessionFactory().close();
	}

}