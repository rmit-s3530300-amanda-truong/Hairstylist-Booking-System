import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	
	public static void getConnection()
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
	
	public static void createCustTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS CUSTINFO ("
					+ "fname text NOT NULL, "
					+ "lname text NOT NULL, "
					+ "password text NOT NULL, "
					+ "gender text NOT NULL, "
					+ "mobile text NOT NULL, "
					+ "address text NOT NULL)";
			
			stmt.executeUpdate(sql);
			stmt.close();
			
			// testing to see if values are added
			PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?);");
			prep.setString(1,"john");	
			prep.setString(2,"poop");
			prep.setString(3,"password");
			prep.setString(4,"boi");
			prep.setString(5,"0412123123");
			prep.setString(6,"1 happy street, happy surburb, 3000");
			prep.execute();
			prep.close();
			
			PreparedStatement prep2 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?);");
			prep2.setString(1,"girly");
			prep2.setString(2,"poop");
			prep2.setString(3,"password1");
			prep2.setString(4,"girl");
			prep2.setString(5,"0469123123");
			prep2.setString(6,"1 sad street, sad surburb, 2000");
			prep2.execute();
			prep2.close();
			
			closeConn();
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
//		System.out.println("Table CUSTINFO created successfully");
	}
	
	// add all the values into a record
	public static void addCustInfo(String fname, String lname, String pw, String gender, String mobile, String address)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			//conn.setAutoCommit(false);
			
			PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?);");
			prep.setString(1, fname);
			prep.setString(2, lname);
			prep.setString(3, pw);
			prep.setString(4, gender);
			prep.setString(5, mobile);
			prep.setString(6, address);
			
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
			result = stmt.executeQuery("SELECT * FROM CUSTINFO");
			while (result.next())
			{
				System.out.println(result.getString("fname") + " " + result.getString("lname") + " "
						+ result.getString("password") + " " + result.getString("gender") + " " + result.getString("mobile")
						+ result.getString("address"));
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
			stmt.close();
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
			String sql = "DELETE FROM " + tableName + " WHERE rowid = " + id;
			stmt.execute(sql);
			stmt.close();
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
			//conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			String sql = "UPDATE CUSTINFO SET ? = ?, WHERE rowid = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, update);
			pstmt.setString(2, value);
			pstmt.setInt(3, id);
			stmt.executeUpdate(sql);
			conn.commit();
			 
			rs = stmt.executeQuery("SELECT * FROM CUSTINFO");

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
	
//	public static void main(String[] args) {
//		getConnection();
//		createCustTable();
//		displayCustTable();
//		deleteCust("CUSTINFO",1);
//		displayCustTable();
//		addCustInfo("mary","lamb","123","girl","12301203","238012as sdfjlsdfj");
//		displayCustTable();
//		deleteCust("CUSTINFO",1);
//		displayCustTable();
//		deleteAllR("CUSTINFO");
//		closeConn();
//	}

}
