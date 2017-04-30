package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class AvailabilityDatabase {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	private static PreparedStatement prep = null;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public AvailabilityDatabase() {
		this.initialise();
		this.addTest();
	}
	
	//get initial connection and create the table
	public Connection initialise()
	{
		Connection connInit = getConnection();
		createAvailTable();
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
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return conn;
	}
	
	private void createAvailTable()
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
					String sql = "CREATE TABLE IF NOT EXISTS AVAILABILITY ("
							+ "employeeID text REFERENCES COMPANY(username) NOT NULL	,"
							+ "day text NOT NULL		,"
							+ "startTime text NOT NULL	,"
							+ "endTime text NOT NULL   );";
					stmt.executeUpdate(sql);
					stmt.close();
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
	
	// add employee availability to a record
	public void addAvailabilityInfo(String employeeID, String day, String startTime, String endTime)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
			prep.setString(1, employeeID);
			prep.setString(2, day);
			prep.setString(3, startTime);
			prep.setString(4, endTime);
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
	
	public HashMap<String, ArrayList<String>> storeAvailValues()
	{
		HashMap<String, ArrayList<String>> availabilityMap = new HashMap<String, ArrayList<String>>();
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM AVAILABILITY");
			while (result.next())
			{
				ArrayList<String> timeList = new ArrayList<String>();
				String employeeID = result.getString("employeeID");
				String dayStr = result.getString("day");
				String key = employeeID + ":" + dayStr;
				String startTimeStr = result.getString("startTime");
				String endTimeStr = result.getString("endTime");
				timeList.add(dayStr);
				timeList.add(startTimeStr);
				timeList.add(endTimeStr);	
				availabilityMap.put(key, timeList);
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
		return availabilityMap;
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
			if(col.equals("employeeID"))
			{
				colName = "employeeID";
			}
			else if(col.equals("day"))
			{
				colName = "day";
			}
			else if(col.equals("startTime"))
			{
				colName = "startTime";
			}
			else if(col.equals("endTime"))
			{
				colName = "endTime";
			}
			prep = conn.prepareStatement("SELECT * FROM AVAILABILITY WHERE "+colName+" = ?;");
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
		
	//delete records if availability exists for employee already
	public void deleteAvail(String id, String day)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			String sql = "DELETE FROM AVAILABILITY WHERE employeeID = '" +id+ 
					"' AND day = '" + day + "'";
			stmt.execute(sql);
			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public void addTest()
	{
		try
		{
			//making sure no duplicates are added when program restarts
			if((!checkValueExists("employeeID","e1") && !checkValueExists("day","Monday")) || (!checkValueExists("employeeID","e1")
					&& !checkValueExists("day","Tuesday")) || (!checkValueExists("employeeID","e2") && !checkValueExists("day","Wednesday")))
			{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
				prep.setString(1,"e1");
				prep.setString(2,"Monday");
				prep.setString(3,"08:15");
				prep.setString(4,"10:15");
				prep.execute();
				prep.close();
				PreparedStatement prep2 = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
				prep2.setString(1,"e1");
				prep2.setString(2,"Tuesday");
				prep2.setString(3,"09:15");
				prep2.setString(4,"12:15");
				prep2.execute();
				prep2.close();
				PreparedStatement prep3 = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
				prep3.setString(1,"e2");
				prep3.setString(2,"Wednesday");
				prep3.setString(3,"13:00");
				prep3.setString(4,"15:00");
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
}