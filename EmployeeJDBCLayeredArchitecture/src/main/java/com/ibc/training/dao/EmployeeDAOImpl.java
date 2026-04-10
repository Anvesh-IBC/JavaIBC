package com.ibc.training.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.utility.DbUtility;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static Connection con = null;
	private static Statement stmt = null;

	@Override
	public int insertEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException {

		int rowsUpdateCount = 0;
		int id = employeeBean.getEmployeeId();
		String ename = employeeBean.getEmployeeName();
		String role = employeeBean.getRole();
		Date insertTime = new Date(employeeBean.getInsertTime().getTime());
		double salary = employeeBean.getSalary();

		String insertData = "INSERT INTO employee (employeeId, employeeName, role, insertTime, salary) VALUES " + "('"
				+ id + "', '" + ename + "', '" + role + "', '" + insertTime + "', '" + salary + "')";

		con = DbUtility.getConnection();
		stmt = con.createStatement();
		rowsUpdateCount = stmt.executeUpdate(insertData);
		if (rowsUpdateCount > 0) {
			System.out.println("Employee Registered Successfully:" + rowsUpdateCount);
		} else {
			System.out.println("Oops Something went wrong|| we are on process...");
		}
		return rowsUpdateCount;
	}

	@Override
	public void readEmployee() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String query = "Select * from employee";
		con = DbUtility.getConnection();
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			int id = rs.getInt("employeeId");
			String name = rs.getString("employeeName");
			String role = rs.getString("role");
			Date insertTime = rs.getDate("insertTime");
			double salary = rs.getDouble("salary");
			System.out.println(id + "|" + name + "|" + role + "|" + insertTime + "|" + salary);
		}

	}

	@Override
	public void updateEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		int id = employeeBean.getEmployeeId();
		double modifiedSalary = employeeBean.getSalary();
		String updateeData = "update employee set salary=" + modifiedSalary + "where employeeid='" + id + "'";
		con = DbUtility.getConnection();
		stmt = con.createStatement();
		int rowsUpdateCount = stmt.executeUpdate(updateeData);
		if (rowsUpdateCount > 0) {
			System.out.println("Employee Updated successfully: " + rowsUpdateCount);
		} else {
			System.out.println("Oops some thing went wrong|| we are on process..");
		}

	}

	@Override
	public void deleteEmployee(EmployeeBean employeeBean) throws ClassNotFoundException, SQLException {
		int id = employeeBean.getEmployeeId();
		String deleteData = "Delete from employee where employeeId='" + id + "'";
		con = DbUtility.getConnection();
		stmt = con.createStatement();
		int deleteRowsUpdateCount = stmt.executeUpdate(deleteData);
		if (deleteRowsUpdateCount > 0) {
			System.out.println("Employee Deleted Successfully" + deleteRowsUpdateCount);

		} else {
			System.out.println("Oops something went wrong|| we are on process");
		}

	}

}
