package com.ibc.trianing.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibc.training.entity.Department;
import com.ibc.training.utility.HibernateUtil;

public class DeleteDepartment {
	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		// Fetch department
		Department dept = (Department) session.get(Department.class, 2);
		if (dept != null) {
			session.delete(dept); // CascadeType.ALL deletes employees too
			session.getTransaction().commit();
			System.out.println("Department and all Employees deleted.");
		} else {
			System.out.println("Department not found.");
		}

		session.close();

	}

}