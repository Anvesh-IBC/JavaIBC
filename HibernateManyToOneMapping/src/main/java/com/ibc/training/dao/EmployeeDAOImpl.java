package com.ibc.training.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibc.training.businessbean.DepartmentBean;
import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.entity.DepartmentEntity;
import com.ibc.training.entity.EmployeeEntity;
import com.ibc.training.utility.HibernateUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public Integer insertEmployeeAndDepartment(EmployeeBean employee1, EmployeeBean employee2, DepartmentBean d)
			throws Exception {

		Transaction tx = null;
		Session session = null;
		Integer employeeId = 0;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			DepartmentEntity dEntity = convertDepartmentBeanToEntity(d);
			EmployeeEntity e1 = convertEmployeeBeanToEntity(employee1);
			EmployeeEntity e2 = convertEmployeeBeanToEntity(employee2);

			// set relationship
			e1.setDepartment(dEntity);
			e2.setDepartment(dEntity);

			// Option A: Save Department explicitly, then Employees
			session.save(dEntity);
			session.save(e1);
			session.save(e2);

			// Option B (alternative with cascade on Department.employees):
			// dEntity.getEmployees().add(e1);
			// dEntity.getEmployees().add(e2);
			// e1.setDepartment(dEntity);
			// e2.setDepartment(dEntity);
			// session.save(dEntity); // cascades employees

			tx.commit();
			employeeId = e1.getEmployeeId();

		} catch (Exception ex) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ignore) {
				}
			}
			throw ex; // rethrow to let service/UI handle/log
		} finally {
			if (session != null && session.isOpen()) {
				try {
					session.close();
				} catch (Exception ignore) {
				}
			}
		}

		return employeeId;
	}

	// --- Converters stay the same ---
	public static DepartmentEntity convertDepartmentBeanToEntity(DepartmentBean departmentBean) {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		departmentEntity.setDepartmentName(departmentBean.getDepartmentName());
		departmentEntity.setCity(departmentBean.getCity());
		return departmentEntity;
	}

	public static EmployeeEntity convertEmployeeBeanToEntity(EmployeeBean employeeBean) {
		EmployeeEntity e1 = new EmployeeEntity();
		e1.setEmployeeName(employeeBean.getEmployeeName());
		e1.setRole(employeeBean.getRole());
		return e1;
	}
}