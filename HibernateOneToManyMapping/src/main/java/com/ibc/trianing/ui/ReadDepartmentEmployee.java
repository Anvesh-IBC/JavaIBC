package com.ibc.trianing.ui;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibc.training.entity.Department;
import com.ibc.training.entity.Employee;
import com.ibc.training.utility.HibernateUtil;

public class ReadDepartmentEmployee {
	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		Department dept = (Department) session.get(Department.class, 2); // 2 = deptId
		if (dept != null) {
			System.out.println("Department: " + dept.getDeptName());
			for (Employee employee : dept.getEmployees()) {
				System.out.println("Employee: " + employee.getEmpName());
			}
		} else {
			System.out.println("Department not found.");
		}

		session.close();

	}

}
