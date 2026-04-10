package com.ibc.training.dao;

import java.sql.SQLException;

import com.ibc.training.businessbean.EmployeeBean;

public interface EmployeeDAO {
	public int insertEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException;

	public void readEmployee() throws ClassNotFoundException, SQLException;

	public void updateEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException;

	public void deleteEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException;
}
