package com.ibc.training.service;

import java.util.List;

import com.ibc.training.business.EmployeeBean;

public interface EmployeeService {
	void addEmployee(EmployeeBean employee);

	List<EmployeeBean> getEmployeeList();

	EmployeeBean getEmployeeById(int empId);

	void updateEmployee(EmployeeBean employee);

	void deleteEmployee(int empId);
}
