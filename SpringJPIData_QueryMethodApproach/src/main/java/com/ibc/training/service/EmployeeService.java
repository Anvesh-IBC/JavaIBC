package com.ibc.training.service;

import java.util.List;

import com.ibc.training.business.bean.EmployeeBean;

public interface EmployeeService {
	List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqual(double salary) throws Exception;

	List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqualOrderByIdDesc(double salary) throws Exception;

	List<EmployeeBean> findAllEmployeesByNameContainingOrderBySalaryDesc(String pattern) throws Exception;

	List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqualAndRole(Double param1, String param2) throws Exception;

	List<EmployeeBean> findAllEmployeesBySalaryBetween(Double param1, Double param2) throws Exception;

}
