package com.ibc.training.service;

import com.ibc.training.businessbean.AssetBean;
import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.dao.EmployeeDAO;
import com.ibc.training.utility.Factory;

public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public Integer insertAssetWithEmployee(EmployeeBean employeeBean, AssetBean assetBean) throws Exception {
		int employeeId = 0;
		EmployeeDAO employeeDAO = Factory.createEmployeeDAO();
		try {
			employeeId = employeeDAO.insertAssetWithEmployee(employeeBean, assetBean);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return employeeId;
	}
}
