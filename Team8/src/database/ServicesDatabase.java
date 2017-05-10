package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServicesDatabase {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	private static PreparedStatement prep = null;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public ServicesDatabase() {
		this.initialise();
		this.addTimes();
	}
	
	//get initial connection and create the table
	public Connection initialise()
	{
		Connection connInit = getConnection();
		createServicesTable();
		return connInit;
	}
	
	//create connection to JDBC sqlite
	private Connection getConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:company.db");
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return conn;
	}
	
	private void createServicesTable()
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
					String sql = "CREATE TABLE IF NOT EXISTS SERVICES ("
							+ "business text REFERENCES COMPANY(compName) NOT NULL,"
							+ "service text REFERENCES COMPANY(service) NOT NULL ,"	
							+ "time text NOT NULL   );";
					stmt.executeUpdate(sql);
					stmt.close();
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
	
	//add service and a time for a business
	public void addServices(String busName, String service, String time)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
			prep.setString(1, busName);
			prep.setString(2, service);
			prep.setString(3, time);
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
	
	// displaying the values in service table
	public ResultSet displayTable()
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM SERVICES");
			while (result.next())
			{
				System.out.println(result.getString("business") + " " + result.getString("service") + " " + result.getString("time"));
			}
			stmt.close();
			result.close();
			conn.close();;
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return result;
	}
	
	public HashMap<String,String> storeServiceValues()
	{
		HashMap<String, String> serviceMap = new HashMap<String, String>();
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM SERVICES");
			while (result.next())
			{
				String business = result.getString("business");
				String service = result.getString("service");
				String time = result.getString("time");
				String key = business + ":" + service;
				serviceMap.put(key, time);
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
		return serviceMap;
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
			if(col.equals("business"))
			{
				colName = "business";
			}
			if(col.equals("service"))
			{
				colName = "service";
			}
			else if(col.equals("time"))
			{
				colName = "time";
			}
			prep = conn.prepareStatement("SELECT * FROM SERVICES WHERE " + colName+ " = ?;");
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
	
	public void addTimes()
	{
		Boolean check = false;
		try
		{
			//making sure no duplicates are added when program restarts
			if(check == false)
			{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep.setString(1,"abc");
				prep.setString(2,"maleCut");
				prep.setString(3,"1");
				prep.execute();
				prep.close();
				PreparedStatement prep2 = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep2.setString(1,"abc");
				prep2.setString(2,"femaleCut");
				prep2.setString(3,"2");
				prep2.execute();
				prep2.close();
				PreparedStatement prep3 = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep3.setString(1,"abc");
				prep3.setString(2,"maleDye");
				prep3.setString(3,"3");
				prep3.execute();
				prep3.close();
				PreparedStatement prep4 = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep4.setString(1,"abc");
				prep4.setString(2,"femaleDye");
				prep4.setString(3,"4");
				prep4.execute();
				prep4.close();
				PreparedStatement prep5 = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep5.setString(1,"abc");
				prep5.setString(2,"malePerm");
				prep5.setString(3,"3");
				prep5.execute();
				prep5.close();
				PreparedStatement prep6 = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep6.setString(1,"abc");
				prep6.setString(2,"femalePerm");
				prep6.setString(3,"4");
				prep6.execute();
				prep6.close();
				PreparedStatement prep7 = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep7.setString(1,"abc");
				prep7.setString(2,"maleWash");
				prep7.setString(3,"1");
				prep7.execute();
				prep7.close();
				PreparedStatement prep8 = conn.prepareStatement("INSERT INTO SERVICES values(?,?,?);");
				prep8.setString(1,"abc");
				prep8.setString(2,"femaleWash");
				prep8.setString(3,"1");
				prep8.execute();
				prep8.close();
				check = true;
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
	
}