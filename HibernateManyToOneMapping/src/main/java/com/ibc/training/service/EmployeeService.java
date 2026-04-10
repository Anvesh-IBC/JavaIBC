package com.ibc.training.service;

import com.ibc.training.businessbean.DepartmentBean;
import com.ibc.training.businessbean.EmployeeBean;

public interface EmployeeService {
	Integer insertEmployeeAndDepartment(EmployeeBean employee1, EmployeeBean employee2, DepartmentBean d)
			throws Exception;
}
