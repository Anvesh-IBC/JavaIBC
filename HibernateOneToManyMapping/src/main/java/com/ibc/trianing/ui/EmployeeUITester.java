package com.ibc.trianing.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibc.training.entity.Department;
import com.ibc.training.entity.Employee;
import com.ibc.training.utility.HibernateUtil;

public class EmployeeUITester {

	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		Department dept = new Department();
		dept.setDeptName("Mech department");

		Employee emp1 = new Employee();
		emp1.setEmpName("Rajesh");

		Employee emp2 = new Employee();
		emp2.setEmpName("Magesh");

		// Set up bi-directional relationships
		dept.addEmployee(emp1);
		dept.addEmployee(emp2);

		session.save(dept); // Cascade will save employees too

		session.getTransaction().commit();
		session.close();

		System.out.println("Department and Employees saved successfully.");

	}

}
