package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static Boolean hasData = false;
	private static PreparedStatement prep = null;
	
	//get initial connection and create desired table
	public void initialise(String dbName)
	{
		getConnection(dbName);
		createTable(dbName);
	}
		
	//get connection to jdbc sqlite
	private void getConnection(String dbName)
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			}
		}
		
	//create the desired table
	private void createTable(String dbName)
	{
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
				result = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name='user'");
				if(!result.next())
				{
					stmt = conn.createStatement();
					if(dbName == "customer")
					{
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
						result.close();
					}
					else
					{
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
						result.close();
					}
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
	
	// add all customer values into a record
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

	// add business owner or employee into a record
	public void addBusInfo(String username, String cname, String bFname, String bLname, String pw, String gender, 
			String mobile, String address, String busStatus)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection("company");
			}
			prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
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
	
	// displaying the values in table
	public ResultSet displayTable(String dbName)
	{
		try
		{
			String tableName = null;
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			if(dbName == "customer")
			{
				tableName = "CUSTINFO";
			}
			else if(dbName == "company")
			{
				tableName = "COMPANY";
			}
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM " + tableName);
			while (result.next())
			{
				if(dbName == "customer")
				{
					System.out.println(result.getString("username") + " " + result.getString("fname") 
							+ " " + result.getString("lname") + " " + result.getString("password") 
							+ " " + result.getString("gender") + " " + result.getString("mobile") 
							+ " " + result.getString("address"));
				}
				else
				{
					System.out.println(result.getString("username") + " " + result.getString("cName") 
							+ " " + result.getString("bFname") + " " + result.getString("bLname") + " " + result.getString("password") 
							+ " " + result.getString("gender") + " " + result.getString("mobile") + result.getString("address")
							+ " " + result.getString("busStatus"));
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
	
	//deletes all rows in the table
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
	
	//check if user is authenticated with user input
	public Boolean checkLogin(String dbName, String username, String password)
	{
		Boolean authen = null;
		Boolean check = null;
		check = checkAuthen(dbName, authen, result, username,password);
		closeConn();
		return check;
	}
	
	//actual authentication method
	public Boolean checkAuthen(String dbName, Boolean authen, ResultSet rs, String username, String password)
	{
		String tableName = null;
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
			else if(dbName == "company")
			{
				tableName = "COMPANY";
			}
			prep = conn.prepareStatement("SELECT username,password FROM " +tableName+ " WHERE username = ? AND password = ?;");
			prep.setString(1, username);
			prep.setString(2, password);
			
			rs = prep.executeQuery();
			
			if(rs.next())
			{
				authen = true;
			}
			else
			{
				authen = false;
			}
			prep.close();
			rs.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return authen;
	}
	
	//adding initial records to custinfo table
	public void addTest(String dbName)
	{
		PreparedStatement prep2;
		PreparedStatement prep3;
		try
		{
			if(dbName == "customer")
			{
				if(!checkExists("customer","username","jpoop") || 
						!checkExists("customer","username","rgeorge") || !checkExists("customer","username","bstar"))
				{
					if(conn.isClosed())
					{
						getConnection("customer");
					}
					
					prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep.setString(1,"jpoop");
					prep.setString(2,"John");	
					prep.setString(3,"Poop");
					prep.setString(4,"password");
					prep.setString(5,"male");
					prep.setString(6,"0412123123");
					prep.setString(7,"1 Happy Street, Happyville, 3000, nsw");
					prep.execute();
					prep.close();
					
					prep2 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep2.setString(1,"rgeorge");
					prep2.setString(2,"Regina");
					prep2.setString(3,"George");
					prep2.setString(4,"password1");
					prep2.setString(5,"female");
					prep2.setString(6,"0469123123");
					prep2.setString(7,"1 Sad street, Sadville, 2000, vic");
					prep2.execute();
					prep2.close();
					
					prep3 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep3.setString(1,"bstar");
					prep3.setString(2,"Bob");
					prep3.setString(3,"Star");
					prep3.setString(4,"password2");
					prep3.setString(5,"female");
					prep3.setString(6,"0469999999");
					prep3.setString(7,"1 Angry Street, Angryville, 3333, vic");
					prep3.execute();
					prep3.close();
				}
			}
			else
			{
				if(!checkExists("company","username","bigboss1") || !checkExists("company","username","e1")
						|| !checkExists("company","username","e2"))
				{
					if(conn.isClosed())
					{
						getConnection("company");
					}
					prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
					prep.setString(1,"bigboss1");
					prep.setString(2,"ABC");
					prep.setString(3,"John");
					prep.setString(4,"Bishop");
					prep.setString(5,"haireverywhere");
					prep.setString(6,"male");
					prep.setString(7,"0430202101");
					prep.setString(8,"1 Haircut street, Hairville, 3000");
					prep.setString(9,"owner");
					prep.execute();
					prep.close();
					
					prep2 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
					prep2.setString(1,"e1");
					prep2.setString(2,"ABC");
					prep2.setString(3,"Elissa");
					prep2.setString(4,"Smith");
					prep2.setString(5,null);
					prep2.setString(6,"female");
					prep2.setString(7,"0469899898");
					prep2.setString(8,"1 Choppachoppa Street, Choparoo, 3333");
					prep2.setString(9,"employee");
					prep2.execute();
					prep2.close();
					
					prep3 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?);");
					prep3.setString(1,"e2");
					prep3.setString(2,"ABC");
					prep3.setString(3,"Paul");
					prep3.setString(4,"Bot");
					prep3.setString(5,null);
					prep3.setString(6,"male");
					prep3.setString(7,"0469123123");
					prep3.setString(8,"1 Work Street, Workhard, 3333");
					prep3.setString(9,"employee");
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
	
	//check if value exists in table with user input
	public Boolean checkExists(String dbName, String col, String value)
	{
		Boolean check = null;
		Boolean cExists = null;
		check = cValue(dbName,cExists, result, col, value);
		return check;
	}
	
	//check value exists actual implementation
	public Boolean cValue(String dbName,Boolean cExists, ResultSet rs, String col, String value)
	{
		String tableName = null;
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
			prep = conn.prepareStatement("SELECT " + col + " FROM "+ tableName +" WHERE " + col + " = '" + value + "';");
			rs = prep.executeQuery();
			
			if(rs.next())
			{
				cExists = true;
			}
			else
			{
				cExists = false;
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
		return cExists;
	}
	
	//check if table exists in database
	public Boolean checkTable(String dbName)
	{
		Boolean tableE = false;
		String name = null;
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			if(dbName == "customer")
			{
				result = conn.getMetaData().getTables(null, null, "CUSTINFO", null);
				while(result.next()){
					name = result.getString("TABLE_NAME");
					if(name.equals("CUSTINFO"))
					{
						tableE = true;
					}
				}
				result.close();
			}
			else
			{
				result = conn.getMetaData().getTables(null, null, "COMPANY", null);
				while(result.next()){
					name = result.getString("TABLE_NAME");
					if(name.equals("COMPANY"))
					{
						tableE = true;
					}
				}
				result.close();
			}
			closeConn();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return tableE;
	}
	
	//check if rows exists in table
	public Boolean checkRows(String dbName)
	{
		String tableName = null;
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
			String sql = "SELECT * FROM " + tableName;
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
	
	//check if connection is valid
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
	
	//close connection
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
