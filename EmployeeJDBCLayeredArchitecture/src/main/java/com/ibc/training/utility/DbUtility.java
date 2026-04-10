package com.ibc.training.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtility {

	private static final String url = "jdbc:mysql://localhost:3306/employeejdbc";
	private static final String userName = "root";
	private static final String password = "root";
	private static Connection con = null;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Loading the Driver Successfully");
		con = DriverManager.getConnection(url, userName, password);
		System.out.println("Connection to database successfully");
		return con;
	}

	public static Connection close() throws SQLException {
		con.close();
		return con;
	}

}
