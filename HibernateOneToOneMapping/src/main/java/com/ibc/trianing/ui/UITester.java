package com.ibc.trianing.ui;

import java.util.Date;

import com.ibc.training.businessbean.AssetBean;
import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.service.EmployeeService;
import com.ibc.training.utility.Factory;
import com.ibc.training.utility.HibernateUtil;

public class UITester {

	public static void main(String[] args) {
		try {
			insertAssetWithEmployee();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			HibernateUtil.shutdown();
		}
	}

	static void insertAssetWithEmployee() {
		int employeeId = 0;
		try {
			EmployeeService service = Factory.createEmployeeService();
			EmployeeBean employee = new EmployeeBean();
			employee.setEmployeeName("Anvesh");
			employee.setInsertTime(new Date());
			employee.setRole("Sr.Developer");
			employee.setSalary(200000.00);

			AssetBean asset = new AssetBean();
			asset.setAssetName("Laptop");
			asset.setAssetBrandName("Dell");
			asset.setValidityYears(3);

			employeeId = service.insertAssetWithEmployee(employee, asset);
			System.out.println("Employee inserted successfully!!" + employeeId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
