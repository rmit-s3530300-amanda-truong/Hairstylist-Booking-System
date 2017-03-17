package Team8.src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	
	private static void getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			//System.out.println("Opened database successfully.");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private static void createCustTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS CUSTINFO (id integer PRIMARY KEY AUTOINCREMENT, "
					+ "fname text NOT NULL, "
					+ "lname text NOT NULL, "
					+ "password text NOT NULL, "
					+ "gender text NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			
			// testing to see if values are added
			PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?);");
			prep.setString(2,"john");	
			prep.setString(3,"poop");
			prep.setString(4,"password");
			prep.setString(5,"boi");
			prep.execute();
			prep.close();
			
			PreparedStatement prep2 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?);");
			prep2.setString(2,"girly");
			prep2.setString(3,"poop");
			prep2.setString(4,"password1");
			prep2.setString(5,"girl");
			prep2.execute();
			prep2.close();
			
			closeConn();
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table CUSTINFO created successfully");
	}
	
	// add all the values into a record
	public static void addCustInfo(String fname, String lname, String pw, String gender)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			conn.setAutoCommit(false);
			
			PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?;");
			prep.setString(2, fname);
			prep.setString(3, lname);
			prep.setString(4, pw);
			prep.setString(5, gender);
			
			prep.execute();
			prep.close();
			
			closeConn();
		}
		catch( Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	// displaying the values in customer table
	public static ResultSet displayCustTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT id, fname, lname, password, gender FROM CUSTINFO");

			while (result.next())
			{
				System.out.println(result.getInt("id") +  " " + result.getString("fname") + " " + result.getString("lname") + " "
						+ result.getString("password") + " " + result.getString("gender"));
			}
			
			stmt.close();
			result.close();
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return result;
	}
	
	//deletes all rows in the table for testing
	public static void deleteAllR(String tableName)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			stmt = conn.createStatement();
			String sql = "DELETE FROM " + tableName;
			stmt.execute(sql);
			
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static void deleteCust(String tableName,int id)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			stmt = conn.createStatement();
			String sql = "DELETE FROM " + tableName + " WHERE id = " + id;
			stmt.execute(sql);
			
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static void updateCust(String update, String value, Integer id)
	{
		ResultSet rs = null;
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			String sql = "UPDATE CUSTINFO SET ? = ?, WHERE id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, update);
			pstmt.setString(2, value);
			pstmt.setInt(3, id);
			stmt.executeUpdate(sql);
			conn.commit();
			 
			rs = stmt.executeQuery("SELECT id, fname, lname, password, gender FROM CUSTINFO");

			while (rs.next())
			{
				System.out.println(rs.getString("fname") + " " + rs.getString("lname") + " "
						+ rs.getString("password") + " " + rs.getString("gender"));
			}
		      
			stmt.close();
			rs.close();
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private static void closeConn()
	{
		try
		{
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		getConnection();
		createCustTable();
		displayCustTable();
		//updateCust("fname","johnny",1);
		//deleteAllR("CUSTINFO");
		deleteCust("CUSTINFO",2);
		displayCustTable();
		deleteAllR("CUSTINFO");
		closeConn();
	}

}
