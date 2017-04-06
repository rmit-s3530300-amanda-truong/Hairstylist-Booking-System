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
	
	//get initial connection and create the table
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
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbName+".db");
			//System.out.println("Opened database successfully.");
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	//create the custinfo table
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
				ResultSet res = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type= 'table' AND name='user'");
				
				if(!res.next())
				{
					stmt = conn.createStatement();
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
				getConnection("customer");
			}
			
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
	
	// displaying the values in table
	public ResultSet displayTable(String dbName)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection(dbName);
			}
			stmt = conn.createStatement();
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
	
	//check if user is authenticated with user input
	public Boolean checkLogin(String dbName, String username, String password)
	{
		Boolean authen = null;
		Boolean check = null;
		ResultSet rs = null;
		
		check = checkAuthen(dbName, authen, rs, username,password);

		closeConn();

		return check;
	}
	
	//actual authentication method
	public Boolean checkAuthen(String dbName, Boolean authen, ResultSet rs, String username, String password)
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
			PreparedStatement prep = conn.prepareStatement("SELECT username,password FROM "+tableName+" WHERE username = ? AND password = ?;");
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
		try
		{
			if(dbName == "customer")
			{
				if(!checkExists("customer","username","jpoop") || 
						!checkExists("customer","username","gpoop") || !checkExists("customer","username","hithere"))
				{
					if(conn.isClosed())
					{
						getConnection("customer");
					}
					PreparedStatement prep = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep.setString(1,"jpoop");
					prep.setString(2,"john");	
					prep.setString(3,"poop");
					prep.setString(4,"password");
					prep.setString(5,"male");
					prep.setString(6,"0412123123");
					prep.setString(7,"1 happy street, happy surburb, 3000, nsw");
					prep.execute();
					prep.close();
					
					PreparedStatement prep2 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep2.setString(1,"gpoop");
					prep2.setString(2,"girly");
					prep2.setString(3,"poop1");
					prep2.setString(4,"password1");
					prep2.setString(5,"female");
					prep2.setString(6,"0469123123");
					prep2.setString(7,"1 sad street, sad surburb, 2000, vic");
					prep2.execute();
					prep2.close();
					
					PreparedStatement prep3 = conn.prepareStatement("INSERT INTO CUSTINFO values(?,?,?,?,?,?,?);");
					prep3.setString(1,"hithere");
					prep3.setString(2,"hi");
					prep3.setString(3,"there");
					prep3.setString(4,"password2");
					prep3.setString(5,"female");
					prep3.setString(6,"0469999999");
					prep3.setString(7,"1 angry street, angry surburb, 3333, vic");
					prep3.execute();
					prep3.close();
				}
			}
			else
			{
				if(!checkExists("company","username","bigboi1") || !checkExists("company","username","e0001"))
				{
					if(conn.isClosed())
					{
						getConnection("company");
					}
					PreparedStatement prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?,?);");
					prep.setString(1,"bigboi1");
					prep.setString(2,"ABC");
					prep.setString(3,"john");
					prep.setString(4,"bishop");
					prep.setString(5,"haireverywhere");
					prep.setString(6,"male");
					prep.setString(7,"0430202101");
					prep.setString(8,"1 haircut street, haircut surburb, 3000");
					prep.setString(9,null);
					prep.setString(10,"owner");
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
					
					PreparedStatement prep3 = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?,?);");
					prep3.setString(1,"e0001");
					prep3.setString(2,"ABC");
					prep3.setString(3,"Elissa");
					prep3.setString(4,"Smith");
					prep3.setString(5,null);
					prep3.setString(6,"female");
					prep3.setString(7,"0469899898");
					prep3.setString(8,"1 choparoo street, choparoo surburb, 3333");
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
	
	//check if value exists in table with user input
	public Boolean checkExists(String dbName, String col, String value)
	{
		Boolean check = null;
		Boolean cExists = null;
		ResultSet rs = null;
		
		check = cValue(dbName, cExists, rs, col, value);
		
		return check;
	}
	
	//check value exists actual implementation
	public Boolean cValue(String dbName, Boolean cExists, ResultSet rs, String col, String value)
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
			
			PreparedStatement prep = conn.prepareStatement("SELECT " + col + " FROM "+tableName+" WHERE " + col + " = '" + value + "';");
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
	
	//check if table exists
	public Boolean checkTable(String dbName)
	{
		Boolean tableE = false;
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
			ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
			while(rs.next()){
				String name = rs.getString("TABLE_NAME");
				if(name.equals(tableName))
				{
					tableE = true;
				}
			}
			rs.close();
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
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM " +tableName;
			ResultSet result = stmt.executeQuery(sql);
			if(result.next())
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
	
	// add all the values into a record
		public void addBusiness(String username, String cname, String bFname, String bLname, String pw, String gender, 
				String mobile, String address, String service, String busStatus)
		{		
			try
			{
				if(conn.isClosed())
				{
					getConnection("company");
				}
				PreparedStatement prep = conn.prepareStatement("INSERT INTO COMPANY values(?,?,?,?,?,?,?,?,?,?);");
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
	public int checkEmployees()
	{
		int counter = 0;
		ResultSet rs;
		try
		{
			if(conn.isClosed())
			{
				getConnection("company");
			}
			stmt = conn.createStatement();
			String sql = "SELECT * FROM COMPANY WHERE busStatus = 'employee';";
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				counter++;
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
		return counter;
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
