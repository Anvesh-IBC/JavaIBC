package com.ibc.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibc.training.bean.EmployeeBean;
import com.ibc.training.dao.EmployeeDAO;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDAO employeeDAO;

	@Override
	@Transactional
	public Integer addEmployee(EmployeeBean employeeBean) throws Exception {
		// TODO Auto-generated method stub
		return employeeDAO.addEmployee(employeeBean);
	}

	@Override
	public EmployeeBean getEmployeeDetails(Integer id) throws Exception {
		EmployeeBean emp = employeeDAO.getEmployeeDetails(id);
		return emp;
	}

	@Override
	public EmployeeBean updateEmployeeDetails(EmployeeBean employeeBean) throws Exception {
		EmployeeBean emp = employeeDAO.updateEmployeeDetails(employeeBean);
		return emp;
	}

	@Override
	public EmployeeBean deleteEmployeeDetails(Integer id) throws Exception {
		EmployeeBean emp = employeeDAO.deleteEmployeeDetails(id);
		return emp;
	}

	@Override
	public List<EmployeeBean> getEmployeeList() throws Exception {

		return employeeDAO.getEmployeeList();
	}
}