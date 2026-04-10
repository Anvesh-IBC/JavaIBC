package com.ibc.training.service;

import java.util.List;

import com.ibc.training.business.bean.EmployeeBean;

@SuppressWarnings("rawtypes")
public interface EmployeeService {
	List<EmployeeBean> getAllEmployeesBySalary(Double salary) throws Exception;

	List<EmployeeBean> getAllEmployeesBySalary2(Double salary) throws Exception;

	List getRolesAndCountOfEmployeesInEachRole() throws Exception;

}
