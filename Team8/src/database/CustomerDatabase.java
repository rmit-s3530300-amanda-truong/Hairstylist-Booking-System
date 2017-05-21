package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Logger;
import java.sql.ResultSet;

public class CustomerDatabase{
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static Boolean hasData = false;
	private static PreparedStatement prep = null;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public CustomerDatabase() {
		this.initialise();
		this.addTest();
	}
	
	//get initial connection and create the table
	public Connection initialise()
	{
		Connection connInit = getConnection();
		createCustTable();
		return connInit;
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
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
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
							+ "compName text REFERENCES BUSINESS(compName) NOT NULL	,"
							+ "fname text NOT NULL		,"
							+ "lname text NOT NULL		,"
							+ "password text NOT NULL	,"
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
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	// add customer info into a record
	public void addCustInfo(String username, String cname, String fname, String lname, String pw, 
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
			prep.setString(2, cname);
			prep.setString(3, fname);
			prep.setString(4, lname);
			prep.setString(5, pw);
			prep.setString(6, mobile);
			prep.setString(7, address);
			prep.execute();
			prep.close();
			conn.close();
		}
		catch( Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
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
				String compName = result.getString("compName");
				String fName = result.getString("fname");
				String lName = result.getString("lname");
				custInfo.put("compName", compName);
				custInfo.put("fname", fName);
				custInfo.put("lname", lName);
				custValues.put(id, custInfo);
			}
			stmt.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return custValues;
	}
	
	//check if user is authenticated
	public Boolean checkLogin(String username, String password, String business)
	{
		Boolean checkAuthen = null;
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("SELECT username,password FROM CUSTINFO WHERE username = ? AND password = ? AND compName = ?;");
			prep.setString(1, username);
			prep.setString(2, password);
			prep.setString(3, business);
			result = prep.executeQuery();
			if(result.next())
			{
				checkAuthen = true;
			}
			else
			{
				checkAuthen = false;
			}
			prep.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return checkAuthen;
	}
	
	//adding initial records to custinfo table
	public void addTest()
	{
		try
		{
			//making sure no duplicates are added when program restarts
			if((!checkValueExists("username","jbrown") && !checkValueExists("compName","ABC HAIRSTYLIST")) || 
					(!checkValueExists("username","rgeorge") && !checkValueExists("compName","ABC HAIRSTYLIST")) || 
					(!checkValueExists("username","tswizzle") && !checkValueExists("compName","ABC HAIRSTYLIST")))
			{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
				prep.setString(1,"jbrown");
				prep.setString(2, "ABC HAIRSTYLIST");
				prep.setString(3,"John");	
				prep.setString(4,"Brown");
				prep.setString(5,"password");
				prep.setString(6,"0412123123");
				prep.setString(7,"1 Happy Street, Happyville, NSW, 3000");
				prep.execute();
				prep.close();
				PreparedStatement prep2 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
				prep2.setString(1,"rgeorge");
				prep2.setString(2, "ABC HAIRSTYLIST");
				prep2.setString(3,"Regina");
				prep2.setString(4,"George");
				prep2.setString(5,"password1");
				prep2.setString(6,"0469123123");
				prep2.setString(7,"1 Sad street, Sadville, VIC, 3000");
				prep2.execute();
				prep2.close();
				PreparedStatement prep3 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
				prep3.setString(1,"tswizzle");
				prep3.setString(2,"ABC HAIRSTYLIST");
				prep3.setString(3,"Taylor");
				prep3.setString(4,"Swift");
				prep3.setString(5,"password2");
				prep3.setString(6,"0469999999");
				prep3.setString(7,"1 Sing Street, Singville, VIC, 3000");
				prep3.execute();
				prep3.close();
			}
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}	
	}
	
	//check if value exists in table
	public Boolean checkValueExists(String col, String value)
	{
		Boolean checkExists = null;
		String colName = null;
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			if(col.equals("username"))
			{
				colName = "username";
			}
			else if(col.equals("compName"))
			{
				colName = "compName";
			}
			else if(col.equals("fname"))
			{
				colName = "fname";
			}
			else if(col.equals("lname"))
			{
				colName = "lname";
			}
			else if(col.equals("password"))
			{
				colName = "password";
			}
			else if(col.equals("mobile"))
			{
				colName = "mobile";
			}
			else if(col.equals("address"))
			{
				colName = "address";
			}
			prep = conn.prepareStatement("SELECT * FROM CUSTINFO WHERE "+colName+" = ?;");
			prep.setString(1, value);
			result = prep.executeQuery();
			if(result.next())
			{
				checkExists = true;
			}
			else
			{
				checkExists = false;
			}
			prep.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return checkExists;
	}
	
	public String getName(String username){
		String fullname = null;
		try{
			if(conn.isClosed()){
				getConnection();
			}
			prep = conn.prepareStatement("SELECT fname,lname FROM CUSTINFO WHERE username = ?;");
			prep.setString(1, username);
			result = prep.executeQuery();
			
			fullname = result.getString("fname") + " " + result.getString("lname"); 
					
			prep.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return fullname;
	}
	
	public boolean checkUsername(String username, String compName)
	{
		boolean check = false;
		try{
			if(conn.isClosed()){
				getConnection();
			}
			prep = conn.prepareStatement("SELECT * FROM CUSTINFO WHERE username = ? AND compName = ?;");
			prep.setString(1, username);
			prep.setString(2, compName);
			result = prep.executeQuery();
			if(result.next())
			{
				check = true;
			}
			prep.close();
			result.close();
			conn.close();	
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return check;
	}
}
