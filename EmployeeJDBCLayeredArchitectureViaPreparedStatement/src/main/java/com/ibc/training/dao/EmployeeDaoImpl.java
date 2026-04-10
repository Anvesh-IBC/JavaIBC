package com.ibc.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ibc.training.business.EmployeeBean;
import com.ibc.training.utility.DBUtility;

public class EmployeeDaoImpl implements EmployeeDao {
	private Connection getConnection() throws SQLException {
		return DBUtility.getConnection();
	}

	@Override
	public void addEmployee(EmployeeBean employee) {
		String sql = "insert into employees (empId, empName, email, role, salary) values (?,?,?,?,?)";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, employee.getEmpId());
			pstmt.setString(2, employee.getEmpName());
			pstmt.setString(3, employee.getEmail());
			pstmt.setString(4, employee.getRole());
			pstmt.setDouble(5, employee.getSalary());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<EmployeeBean> getEmployeeList() {
		List<EmployeeBean> employees = new ArrayList<>();
		String sql = "SELECT * From employees";
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				EmployeeBean emp = new EmployeeBean();
				emp.setEmpId(rs.getInt("empId"));
				emp.setEmpName(rs.getString("empName"));
				emp.setEmail(rs.getString("email"));
				emp.setRole(rs.getString("Role"));
				emp.setSalary(rs.getDouble("salary"));
				employees.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public EmployeeBean getEmployeeById(int empId) {
		EmployeeBean emp = null;
		String sql = "select * from employees where empId = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, empId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					emp = new EmployeeBean();
					emp.setEmpId(rs.getInt("empId"));
					emp.setEmpName(rs.getString("empName"));
					emp.setEmail(rs.getString("email"));
					emp.setRole(rs.getString("role"));
					emp.setSalary(rs.getDouble("Salary"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public void updateEmployee(EmployeeBean employee) {
		String sql = "update employees set empName=?, email=?, role=?, salary=? where empId =?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, employee.getEmpName());
			pstmt.setString(2, employee.getEmail());
			pstmt.setString(3, employee.getRole());
			pstmt.setDouble(4, employee.getSalary());
			pstmt.setInt(5, employee.getEmpId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEmployee(int empId) {
		String sql = "delete from employees where empId=?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, empId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
