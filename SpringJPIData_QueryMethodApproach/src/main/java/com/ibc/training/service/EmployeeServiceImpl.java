package com.ibc.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.training.business.bean.EmployeeBean;
import com.ibc.training.dao.EmployeeDAOWrapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAOWrapper employeeDAOWrapper;

	public List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqual(double salary) throws Exception {
		return employeeDAOWrapper.findAllEmployeesBySalaryGreaterThanEqual(salary);
	}

	public List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqualOrderByIdDesc(double salary) throws Exception {
		return employeeDAOWrapper.findAllEmployeesBySalaryGreaterThanEqualOrderByIdDesc(salary);
	}

	public List<EmployeeBean> findAllEmployeesByNameContainingOrderBySalaryDesc(String pattern) throws Exception {
		return employeeDAOWrapper.findAllEmployeesByNameContainingOrderBySalaryDesc(pattern);
	}

	public List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqualAndRole(Double param1, String param2)
			throws Exception {
		return employeeDAOWrapper.findAllEmployeesBySalaryGreaterThanEqualAndRole(param1, param2);
	}

	public List<EmployeeBean> findAllEmployeesBySalaryBetween(Double param1, Double param2) throws Exception {
		return employeeDAOWrapper.findAllEmployeesBySalaryBetween(param1, param2);
	}

}