package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.sql.ResultSet;

public class CustomerDatabase{
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static Boolean hasData = false;
	private static PreparedStatement prep = null;
	
	public CustomerDatabase() {
		this.initialise();
		this.addTest();
	}
	
	//get initial connection and create the table
	public Connection initialise()
	{
		Connection conn = getConnection();
		createCustTable();
		return conn;
	}
	
	//create connection to JDBC sqlite
	private Connection getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:customer.db");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return conn;
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
				//checking if there is already a table created in the database
				result = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name='user'");
				if(!result.next())
				{
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
					result.close();
					conn.close();
				}
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	// add customer info into a record
	public void addCustInfo(String username, String fname, String lname, String pw, String gender, 
			String mobile, String address)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
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
			conn.close();
		}
		catch( Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	//check if user is authenticated with user input
	public Boolean checkLogin(String username, String password)
	{
		Boolean authen = null;
		Boolean check = null;
		check = checkAuthen(authen, username,password);
		return check;
	}
	
	public HashMap<String, HashMap<String,String>> storeCustomerValues()
	{
		HashMap<String, HashMap<String,String>> custValues = new HashMap<String, HashMap<String,String>>();
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
				HashMap<String,String> custInfo = new HashMap<String,String>();
				String id = result.getString("username");
				String fName = result.getString("fname");
				String lName = result.getString("lname");
				String gender = result.getString("gender");
				custInfo.put("fName", fName);
				custInfo.put("lName", lName);
				custInfo.put("gender", gender);
				custValues.put(id, custInfo);
			}
			stmt.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return custValues;
	}
	
	//authentication method
	public Boolean checkAuthen(Boolean authen, String username, String password)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("SELECT username,password FROM CUSTINFO WHERE username = ? AND password = ?;");
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
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return authen;
	}
	
	//adding initial records to custinfo table
	public void addTest()
	{
		try
		{
			//making sure no duplicates are added when program restarts
			if(!checkExists("username","jbrown") || 
					!checkExists("username","rgeorge") || !checkExists("username","tswizzle"))
			{
				if(conn.isClosed())
				{
					getConnection();
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
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}	
	}
	
	//check if value exists in table with user input
	public Boolean checkExists(String col, String value)
	{
		Boolean check = null;
		Boolean cExists = null;
		check = cValue(cExists, col, value);
		return check;
	}
	
	//check value exists implementation
	public Boolean cValue(Boolean cExists, String col, String value)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			prep = conn.prepareStatement("SELECT " + col + " FROM CUSTINFO WHERE " + col + " = '" + value + "';");
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
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return cExists;
	}
}
