package com.ibc.training.service;

import java.sql.SQLException;

import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.dao.EmployeeDAO;
import com.ibc.training.dao.EmployeeDAOImpl;

public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDAO dao = new EmployeeDAOImpl();

	@Override
	public int insertEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException {
		int rowsUpdateCount = 0;
		rowsUpdateCount = dao.insertEmployee(employeeBean);
		return rowsUpdateCount;
	}

	@Override
	public void readEmployee() throws ClassNotFoundException, SQLException {
		dao.readEmployee();

	}

	@Override
	public void updateEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException {
		dao.updateEmployee(employeeBean);

	}

	@Override
	public void deleteEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException {
		dao.deleteEmployee(employeeBean);

	}

}
