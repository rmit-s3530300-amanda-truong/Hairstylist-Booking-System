package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static Boolean hasData = false;
	private static PreparedStatement prep = null;
	
	//get connection and create desired table
	public void initialise(String dbName)
	{
		getConnection(dbName);
		createTable(dbName);
	}
	
	//create database file with connection to JDBC sqlite
	private void getConnection(String dbName)
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	private void createTable(String dbName)
	{
		String sql = null;
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
									
			if(!hasData)
			{
				hasData = true;
				stmt = conn.createStatement();
				//checking if there is already a table created in the database
				result = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name='user'");	
				if(!result.next())
				{
					stmt = conn.createStatement();
					//create customer table 
					if(dbName == "customer")
					{
						sql = "CREATE TABLE IF NOT EXISTS CUSTINFO ("
								+ "username text NOT NULL	,"
								+ "fname text NOT NULL		,"
								+ "lname text NOT NULL		,"
								+ "password text NOT NULL	,"
								+ "gender text NOT NULL		,"
								+ "mobile text NOT NULL		, "
								+ "address text NOT NULL	);";
					}
					//create company table
					else
					{
						sql = "CREATE TABLE IF NOT EXISTS COMPANY ("
								+ "username text NOT NULL	,"
								+ "cName text NOT NULL		,"
								+ "bFname text NOT NULL		,"
								+ "bLname text NOT NULL		,"
								+ "password text 			,"
								+ "gender text NOT NULL		,"
								+ "mobile text NOT NULL		,"
								+ "address text NOT NULL	,"
								+ "service text				,"
								+ "busStatus text NOT NULL);";
					}
					stmt.executeUpdate(sql);
					stmt.close();
					result.close();
					closeConn();
				}
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	// add a record for customer table
	public void addCustInfo(String username, String fname, String lname, String pw, String gender, 
			String mobile, String address)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection("customer");
			}
			prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
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
	
	public ResultSet displayTable(String dbName)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			stmt = conn.createStatement();
			//display each value in each record of customer table
			if(dbName == "customer")
			{
				result = stmt.executeQuery("SELECT * FROM CUSTINFO");
				while (result.next())
				{
					System.out.println(result.getString("username") + " " + result.getString("fname") 
					+ " " + result.getString("lname") + " " + result.getString("password") 
					+ " " + result.getString("gender") + " " + result.getString("mobile") 
					+ " " + result.getString("address"));
				}
			}
			//display each value in each record of company table
			else
			{
				result = stmt.executeQuery("SELECT * FROM COMPANY");
				while (result.next())
				{
					System.out.println(result.getString("username") + " " + result.getString("cName") 
					+ " " + result.getString("bFname") + " " + result.getString("bLname") + " " + result.getString("password") 
					+ " " + result.getString("gender") + " " + result.getString("mobile") + result.getString("address")
					+ " " + result.getString("service") + " " + result.getString("busStatus"));
				}
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
	
	//deletes all rows in the table (clearing table for testing)
	public void deleteAllR(String dbName, String tableName)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
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
		closeConn();
	}
	
	//check if user is authenticated with user input
	public Boolean checkLogin(String dbName, String username, String password)
	{
		Boolean authen = null;
		Boolean check = null;
		check = checkAuthen(dbName, authen, username, password);
		closeConn();
		return check;
	}
	
	//authentication implementation
	public Boolean checkAuthen(String dbName, Boolean authen, String username, String password)
	{
		String tableName;
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			if(dbName == "customer")
			{
				tableName = "CUSTINFO";
			}
			else
			{
				tableName = "COMPANY";
			}
			prep = conn.prepareStatement("SELECT username,password FROM "+tableName+" WHERE username = ? AND password = ?;");
			prep.setString(1, username);
			prep.setString(2, password);
			result = prep.executeQuery();
			if(result.next())
			{
				authen = true;
			}
			else
			{
				authen = false;
			}
			prep.close();
			result.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return authen;
	}
	
	//adds 3 initial test records for each database
	public void addTest(String dbName)
	{
		try
		{
			if(dbName == "customer")
			{
				//making sure no duplicates are added when program restarts
				if(!checkExists("customer","username","jbrown") || 
						!checkExists("customer","username","rgeorge") || !checkExists("customer","username","tswizzle"))
				{
					if(conn.isClosed())
					{
						getConnection("customer");
					}
					prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep.setString(1,"jbrown");
					prep.setString(2,"John");	
					prep.setString(3,"Brown");
					prep.setString(4,"password");
					prep.setString(5,"male");
					prep.setString(6,"0412123123");
					prep.setString(7,"1 Happy Street, Happyville, 3000, nsw");
					prep.execute();
					prep.close();
					
					PreparedStatement prep2 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep2.setString(1,"rgeorge");
					prep2.setString(2,"Regina");
					prep2.setString(3,"George");
					prep2.setString(4,"password1");
					prep2.setString(5,"female");
					prep2.setString(6,"0469123123");
					prep2.setString(7,"1 Sad street, Sadville, 2000, vic");
					prep2.execute();
					prep2.close();
					
					PreparedStatement prep3 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep3.setString(1,"tswizzle");
					prep3.setString(2,"Taylor");
					prep3.setString(3,"Swift");
					prep3.setString(4,"password2");
					prep3.setString(5,"female");
					prep3.setString(6,"0469999999");
					prep3.setString(7,"1 Sing Street, Singville, 3333, vic");
					prep3.execute();
					prep3.close();
				}
			}
			else
			{
				//making sure no duplicates are added when program restarts
				if(!checkExists("company","username","abcboss") || !checkExists("company","username","e1")
						|| !checkExists("company","username","e2"))
				{
					if(conn.isClosed())
					{
						getConnection("company");
					}
					prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?,?);");
					prep.setString(1,"abcboss");
					prep.setString(2,"ABC");
					prep.setString(3,"John");
					prep.setString(4,"Bishop");
					prep.setString(5,"password");
					prep.setString(6,"male");
					prep.setString(7,"0430202101");
					prep.setString(8,"1 Bossy Street, Bossville, 3000");
					prep.setString(9,null);
					prep.setString(10,"owner");
					prep.execute();
					prep.close();
					
					PreparedStatement prep2 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?,?);");
					prep2.setString(1,"e1");
					prep2.setString(2,"ABC");
					prep2.setString(3,"Bob");
					prep2.setString(4,"Lee");
					prep2.setString(5,null);
					prep2.setString(6,"male");
					prep2.setString(7,"0400123000");
					prep2.setString(8,"1 Hair Street, Hairy, 2000");
					prep2.setString(9,"femaleCut, maleCut, femaleDye");
					prep2.setString(10,"employee");
					prep2.execute();
					prep2.close();
					
					PreparedStatement prep3 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?,?);");
					prep3.setString(1,"e2");
					prep3.setString(2,"ABC");
					prep3.setString(3,"Elissa");
					prep3.setString(4,"Smith");
					prep3.setString(5,null);
					prep3.setString(6,"female");
					prep3.setString(7,"0469899898");
					prep3.setString(8,"1 ChoppaChoppa Street, Choparoo, 3333");
					prep3.setString(9,"femaleCut");
					prep3.setString(10,"employee");
					prep3.execute();
					prep3.close();
				}
			}
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}	
	}
	
	//check if value exists in table getting user input
	public Boolean checkExists(String dbName, String col, String value)
	{
		Boolean check = null;
		Boolean cExists = null;
		check = checkValue(dbName, cExists, col, value);
		return check;
	}
	
	//check value exists implementation
	public Boolean checkValue(String dbName, Boolean cExists, String col, String value)
	{
		String tableName;
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			if(dbName == "customer")
			{
				tableName = "CUSTINFO";
			}
			else
			{
				tableName = "COMPANY";
			}
			prep = conn.prepareStatement("SELECT " + col + " FROM "+tableName+" WHERE " + col + " = '" + value + "';");
			result = prep.executeQuery();
			if(result.next())
			{
				cExists = true;
			}
			else
			{
				cExists = false;
			}
			prep.close();
			result.close();
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return cExists;
	}
	
	//check if table exists in database
	public Boolean checkTable(String dbName)
	{
		Boolean tableE = false;
		String tableName;
		String checkName;
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			if(dbName == "customer")
			{
				tableName = "CUSTINFO";
			}
			else
			{
				tableName = "COMPANY";
			}
			result = conn.getMetaData().getTables(null, null, tableName, null);
			while(result.next()){
				checkName = result.getString("TABLE_NAME");
				if(checkName.equals(tableName))
				{
					tableE = true;
				}
			}
			result.close();
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return tableE;
	}
	
	//check if rows exists in table (for testing if tests for added)
	public Boolean checkRows(String dbName)
	{
		String tableName;
		Boolean check = false;;
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			if(dbName == "customer")
			{
				tableName = "CUSTINFO";
			}
			else
			{
				tableName = "COMPANY";
			}
			stmt = conn.createStatement();
			String sql = "SELECT * FROM " +tableName;
			result = stmt.executeQuery(sql);
			if(result.next())
			{
				check = true;
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
		return check;
	}
	
	//add either employee or business owner record
		public void addBusiness(String username, String cname, String bFname, String bLname, String pw, String gender, 
				String mobile, String address, String service, String busStatus)
		{		
			try
			{
				if(conn.isClosed())
				{
					getConnection("company");
				}
				prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?,?);");
				prep.setString(1, username);
				prep.setString(2, cname);
				prep.setString(3, bFname);
				prep.setString(4, bLname);
				prep.setString(5, pw);
				prep.setString(6, gender);
				prep.setString(7, mobile);
				prep.setString(8, address);
				prep.setString(9, service);
				prep.setString(10, busStatus);
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
		
	//return how many employees in the database
	public int checkEmployees()
	{
		int counter = 0;
		try
		{
			if(conn.isClosed())
			{
				getConnection("company");
			}
			stmt = conn.createStatement();
			String sql = "SELECT * FROM COMPANY WHERE busStatus = 'employee';";
			result = stmt.executeQuery(sql);
			while(result.next())
			{
				counter++;
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
		return counter;
	}
	
	public Boolean checkConnection()
	{
		Boolean check = null;
		try
		{
			if(conn.isClosed())
			{
				check = false;
			}
			else
			{
				check = true;
			}
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return check;
	}
	
	public void closeConn()
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
