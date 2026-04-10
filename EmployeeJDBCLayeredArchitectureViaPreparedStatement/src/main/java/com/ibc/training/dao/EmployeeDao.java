package com.ibc.training.dao;

import java.util.List;

import com.ibc.training.business.EmployeeBean;

public interface EmployeeDao {
	void addEmployee(EmployeeBean employee);

	List<EmployeeBean> getEmployeeList();

	EmployeeBean getEmployeeById(int empId);

	void updateEmployee(EmployeeBean employee);

	void deleteEmployee(int empId);
}
