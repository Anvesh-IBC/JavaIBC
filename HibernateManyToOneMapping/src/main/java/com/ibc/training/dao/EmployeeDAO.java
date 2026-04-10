package com.ibc.training.dao;

import com.ibc.training.businessbean.DepartmentBean;
import com.ibc.training.businessbean.EmployeeBean;

public interface EmployeeDAO {
	Integer insertEmployeeAndDepartment(EmployeeBean employee1, EmployeeBean employee2, DepartmentBean d)
			throws Exception;
}
