package com.ibc.training.utility;

import com.ibc.training.dao.EmployeeDAO;
import com.ibc.training.dao.EmployeeDAOImpl;
import com.ibc.training.service.EmployeeService;
import com.ibc.training.service.EmployeeServiceImpl;

public class Factory {
	public static EmployeeDAO createEmployeeDAO() {
		return new EmployeeDAOImpl();
	}

	public static EmployeeService createEmployeeService() {
		return new EmployeeServiceImpl();
	}
}
