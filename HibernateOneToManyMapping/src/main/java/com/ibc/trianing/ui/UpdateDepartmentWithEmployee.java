package com.ibc.trianing.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibc.training.entity.Department;
import com.ibc.training.entity.Employee;
import com.ibc.training.utility.HibernateUtil;

public class UpdateDepartmentWithEmployee {
	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();

		// Fetch department
		Department dept = (Department) session.get(Department.class, 2);
		if (dept != null) {
			dept.setDeptName("Civil Department"); // Update name

			// Add a new employee
			Employee emp3 = new Employee();
			emp3.setEmpName("Murgesh");
			dept.addEmployee(emp3);

			session.getTransaction().commit();
			System.out.println("Department and Employees updated.");
		} else {
			System.out.println("Department not found.");
		}

		session.close();
	}

}
