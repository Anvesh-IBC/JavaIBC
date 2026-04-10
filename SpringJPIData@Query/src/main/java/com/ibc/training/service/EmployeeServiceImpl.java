package com.ibc.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.training.business.bean.EmployeeBean;
import com.ibc.training.dao.EmployeeDAOWrapper;

@SuppressWarnings("rawtypes")
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAOWrapper employeeDAOWrapper;

	public List<EmployeeBean> getAllEmployeesBySalary(Double salary) throws Exception {
		return employeeDAOWrapper.getAllEmployeesBySalary(salary);
	}

	public List<EmployeeBean> getAllEmployeesBySalary2(Double salary) throws Exception {
		return employeeDAOWrapper.getAllEmployeesBySalary2(salary);
	}

	public List getRolesAndCountOfEmployeesInEachRole() throws Exception {
		return employeeDAOWrapper.getRolesAndCountOfEmployeesInEachRole();
	}

}