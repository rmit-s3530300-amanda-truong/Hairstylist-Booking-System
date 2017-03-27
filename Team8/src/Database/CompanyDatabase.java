package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class CompanyDatabase {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	
	public void initialise()
	{
		getConnection();
		createCompanyTable();
	}
	
	private void getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:company.db");
			//System.out.println("Opened database successfully.");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private void createCompanyTable()
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
					//System.out.println("Building COMPANY table");
					stmt = conn.createStatement();
					String sql = "CREATE TABLE IF NOT EXISTS COMPANY ("
							+ "username text NOT NULL	,"
							+ "cName text NOT NULL		,"
							+ "bFname text NOT NULL		,"
							+ "bLname text NOT NULL		,"
							+ "password text 			,"
							+ "gender text NOT NULL		,"
							+ "mobile text NOT NULL		,"
							+ "address text NOT NULL	,"
							+ "busStatus text NOT NULL);";
					
					stmt.executeUpdate(sql);
					stmt.close();
					
					Statement state = conn.createStatement();
					String sql2 = "SELECT * FROM COMPANY";
					ResultSet result = state.executeQuery(sql2);
					if(result.next())
					{
						deleteAllR("COMPANY");
					}

					closeConn();
					//System.out.println("Table COMPANY created successfully");
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
	public void addBusiness(String username, String cname, String bFname, String bLname, String pw, String gender, 
			String mobile, String address, String busStatus)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			//conn.setAutoCommit(false);
			
			PreparedStatement prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
			prep.setString(1, username);
			prep.setString(2, cname);
			prep.setString(3, bFname);
			prep.setString(4, bLname);
			prep.setString(5, pw);
			prep.setString(6, gender);
			prep.setString(7, mobile);
			prep.setString(8, address);
			prep.setString(9, busStatus);
			
			
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
	
	//check if value exists in table
		public boolean checkExists(String col, String value)
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
				
				prep = conn.prepareStatement("SELECT " + col + " FROM COMPANY WHERE " + col + " = '" + value + "';");
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
	
	// displaying the values in customer table
	public ResultSet displayCompanyTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM COMPANY");
			while (result.next())
			{
				System.out.println(result.getString("username") + " " + result.getString("cName") 
				+ " " + result.getString("bFname") + " " + result.getString("bLname") + " " + result.getString("password") 
				+ " " + result.getString("gender") + " " + result.getString("mobile") + result.getString("address")
				+ " " + result.getString("busStatus"));
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

	public boolean checkLogin(String username, String password)
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
			
			prep = conn.prepareStatement("SELECT username,password FROM COMPANY WHERE "
					+ "username = ? AND password = ?;");
			prep.setString(1, username);
			prep.setString(2, password);
			
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
			PreparedStatement prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
			prep.setString(1,"bigboi1");
			prep.setString(2,"ABC");
			prep.setString(3,"john");
			prep.setString(4,"bishop");
			prep.setString(5,"haireverywhere");
			prep.setString(6,"male");
			prep.setString(7,"0430202101");
			prep.setString(8,"1 haircut street, haircut surburb, 3000");
			prep.setString(9,"owner");
			prep.execute();
			prep.close();
			
//			PreparedStatement prep2 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?);");
//			prep2.setString(1,"bigboi2");
//			prep2.setString(2,"bob");
//			prep2.setString(3,"CutCut");
//			prep2.setString(4,"hairpass");
//			prep2.setString(5,"0400123000");
//			prep2.setString(6,"1 hair street, hair surburb, 2000");
//			prep2.setString(7,"222");
//			prep2.execute();
//			prep2.close();
			
			PreparedStatement prep3 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
			prep3.setString(1,"e0001");
			prep3.setString(2,"ABC");
			prep3.setString(3,"Elissa");
			prep3.setString(4,"Smith");
			prep3.setString(5,null);
			prep3.setString(6,"female");
			prep3.setString(7,"0469899898");
			prep3.setString(8,"1 choparoo street, choparoo surburb, 3333");
			prep3.setString(9,"employee");
			prep3.execute();
			prep3.close();
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
