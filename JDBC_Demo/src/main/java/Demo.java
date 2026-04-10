import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded sucessfully");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not loaded sucessfully");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcdemo", "root", "root");
			System.out.println("Connection established sucessfully");
			stmt = conn.createStatement();
//			   String cs = "CREATE TABLE EMP(Empid int,Empname char(20),Empsal int)";
//			   stmt.execute(cs);
			
//			String cs = "INSERT INTO EMP VALUES(1001,'Shobha',45000)";
//			stmt.executeUpdate(cs);
//			cs = "INSERT INTO EMP VALUES(1002,'Sheela',55000)";
//			stmt.executeUpdate(cs);
//			cs = "INSERT INTO EMP VALUES(1003,'Shaila',65000)";
//			stmt.executeUpdate(cs);
			String cs = "SELECT * FROM EMP";
			   
			   ResultSet rs = stmt.executeQuery(cs);
			   ResultSetMetaData rsmd = rs.getMetaData();
			   
			   int cols = rsmd.getColumnCount();
			   while(rs.next())
			   {
			    for(int i=1;i<=cols;i++)
			    {
			     String s = rs.getString(i);
			     System.out.print(s+" ");
			    }
			    System.out.println();
			   }
			stmt.close();
		} catch (SQLException e) {
			System.out.println("Connection not established sucessfully");
		}

	}

}
