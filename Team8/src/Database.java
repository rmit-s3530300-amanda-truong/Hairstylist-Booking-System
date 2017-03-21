import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	
	public void initialise()
	{
		getConnection();
		createCustTable();
	}
	
	private void getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:customer.db");
			//System.out.println("Opened database successfully.");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private void createCustTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
									
			if(!hasData)
			{
				hasData = true;
				stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name='user'");
				
				if(!res.next())
				{
					//System.out.println("Building CUSTINFO table");
					stmt = conn.createStatement();
					String sql = "CREATE TABLE IF NOT EXISTS CUSTINFO ("
							+ "username text NOT NULL	,"
							+ "fname text NOT NULL		,"
							+ "lname text NOT NULL		,"
							+ "password text NOT NULL	,"
							+ "gender text NOT NULL		,"
							+ "mobile text NOT NULL		, "
							+ "address text NOT NULL	);";
					
					stmt.executeUpdate(sql);
					stmt.close();
					
					Statement state = conn.createStatement();
					String sql2 = "SELECT * FROM CUSTINFO";
					ResultSet result = state.executeQuery(sql2);
					if(result.next())
					{
						deleteAllR("CUSTINFO");
					}
					
					/*
					// testing to see if values are added
					PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep.setString(1,"jpoop");
					prep.setString(2,"john");	
					prep.setString(3,"poop");
					prep.setString(4,"password");
					prep.setString(5,"boi");
					prep.setString(6,"0412123123");
					prep.setString(7,"1 happy street, happy surburb, 3000");
					prep.execute();
					prep.close();
					
					PreparedStatement prep2 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep2.setString(1,"gpoop");
					prep2.setString(2,"girly");
					prep2.setString(3,"poop");
					prep2.setString(4,"password1");
					prep2.setString(5,"girl");
					prep2.setString(6,"0469123123");
					prep2.setString(7,"1 sad street, sad surburb, 2000");
					prep2.execute();
					prep2.close();
					*/
					closeConn();
					//System.out.println("Table CUSTINFO created successfully");
				}
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	// add all the values into a record
	public void addCustInfo(String username, String fname, String lname, String pw, String gender, 
			String mobile, String address)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			//conn.setAutoCommit(false);
			
			PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
			prep.setString(1, username);
			prep.setString(2, fname);
			prep.setString(3, lname);
			prep.setString(4, pw);
			prep.setString(5, gender);
			prep.setString(6, mobile);
			prep.setString(7, address);
			
			
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
	public ResultSet displayCustTable()
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
				System.out.println(result.getString("username") + " " + result.getString("fname") 
				+ " " + result.getString("lname") + " " + result.getString("password") 
				+ " " + result.getString("gender") + " " + result.getString("mobile") 
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
	public void deleteAllR(String tableName)
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
	
	public void deleteCust(String tableName,int id)
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
	
	public boolean checkValue(String value)
	{
		Boolean check = null;
		PreparedStatement prep;
		ResultSet rs;
		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			prep = conn.prepareStatement("SELECT username FROM CUSTINFO WHERE username = ?;");
			prep.setString(1, value);
			rs = prep.executeQuery();
			
			if(rs.next())
			{
				check = true;
			}
			else
			{
				check = false;
			}
			prep.close();
			rs.close();
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return check;
	}
	
	public void addTest()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
			prep.setString(1,"jpoop");
			prep.setString(2,"john");	
			prep.setString(3,"poop");
			prep.setString(4,"password");
			prep.setString(5,"boi");
			prep.setString(6,"0412123123");
			prep.setString(7,"1 happy street, happy surburb, 3000");
			prep.execute();
			prep.close();
			
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		
	}
	public void updateCust(String update, String value, Integer id)
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
			//String sql = "UPDATE CUSTINFO SET ? = ?, WHERE rowid = ?";
			String sql = "UPDATE CUSTINFO SET " + update + " = '" + value + "' WHERE rowid = " + id;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "update");
			pstmt.setString(2, "value");
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
	
	private void closeConn()
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

}
