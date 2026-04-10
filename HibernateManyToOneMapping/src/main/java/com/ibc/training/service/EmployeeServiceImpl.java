package com.ibc.training.service;

import com.ibc.training.businessbean.DepartmentBean;
import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.dao.EmployeeDAO;
import com.ibc.training.utility.Factory;

public class EmployeeServiceImpl implements EmployeeService {
	@Override
	public Integer insertEmployeeAndDepartment(EmployeeBean employee1, EmployeeBean employee2, DepartmentBean d)
			throws Exception {
		try {
			EmployeeDAO employeeDAO = Factory.createEmployeeDAO();
			return employeeDAO.insertEmployeeAndDepartment(employee1, employee2, d);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}
