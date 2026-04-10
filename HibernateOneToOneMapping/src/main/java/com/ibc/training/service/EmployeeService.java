package com.ibc.training.service;

import com.ibc.training.businessbean.AssetBean;
import com.ibc.training.businessbean.EmployeeBean;

public interface EmployeeService {
	public Integer insertAssetWithEmployee(EmployeeBean employeeBean, AssetBean assetBean) throws Exception;

}
