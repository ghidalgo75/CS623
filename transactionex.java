package databasedesignproj2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//George Hidalgo & Anna Spreitzer

public class transactionex {
	
	public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {
		
		//connect to db
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","6855dua11y");
		if (conn != null)
		{
			System.out.println("Connection OK");
		}
		else
		{
			System.out.println("Connection Failed");
		}
		
		conn.setAutoCommit(false); //For atomicicty
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); //for isolation
		
		Statement stmt = null;
		
		//delete from stock
		try {stmt = conn.createStatement();
		stmt.execute("DELETE FROM Stock where dep_id = 'd1'");
		System.out.println("success deleting from stock");
		conn.commit();
		stmt.close();
		}catch (SQLException e)	{
			System.out.println(e);
			conn.rollback();
			stmt.close();
			conn.close();
			return;
		}
		
		//delete from depot
		try {stmt = conn.createStatement();
		String instring = "DELETE FROM depot where dep_id = 'd1'";
		stmt.executeUpdate(instring);
		conn.commit();
		stmt.close();
		System.out.println("success deleting from depot");
		} catch (SQLException e) {
			System.out.println("catch Exception");
			// For atomicity
			conn.rollback();
			stmt.close();
			return;
		}
		conn.close();
	}
}