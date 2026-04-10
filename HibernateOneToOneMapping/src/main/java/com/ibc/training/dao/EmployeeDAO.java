package com.ibc.training.dao;

import com.ibc.training.businessbean.AssetBean;
import com.ibc.training.businessbean.EmployeeBean;

public interface EmployeeDAO {
	public Integer insertAssetWithEmployee(EmployeeBean employeeBean, AssetBean assetBean) throws Exception;
}
