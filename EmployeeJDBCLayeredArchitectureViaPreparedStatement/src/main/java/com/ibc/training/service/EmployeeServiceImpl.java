package com.ibc.training.service;

import java.util.List;

import com.ibc.training.business.EmployeeBean;
import com.ibc.training.dao.EmployeeDao;
import com.ibc.training.dao.EmployeeDaoImpl;

public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeDao employeeDao;

	public EmployeeServiceImpl() {
		this.employeeDao = new EmployeeDaoImpl();
	}

	@Override
	public void addEmployee(EmployeeBean employee) {
		employeeDao.addEmployee(employee);
	}

	@Override
	public List<EmployeeBean> getEmployeeList() {
		return employeeDao.getEmployeeList();
	}

	@Override
	public EmployeeBean getEmployeeById(int empId) {
		return employeeDao.getEmployeeById(empId);
	}

	@Override
	public void updateEmployee(EmployeeBean employee) {
		employeeDao.updateEmployee(employee);
	}

	@Override
	public void deleteEmployee(int empId) {
		employeeDao.deleteEmployee(empId);
	}
}
