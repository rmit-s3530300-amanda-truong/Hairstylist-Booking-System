package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AvailabilityDatabase {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	private static PreparedStatement prep = null;
	
	public AvailabilityDatabase() {
		this.initialise();
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
							+ "date text NOT NULL		,"
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
	
	public ResultSet displayTable()
	{
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
				System.out.println(result.getString("employeeID") + " " + result.getString("date") 
				+ " " + result.getString("startTime") + " " + result.getString("endTime"));
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
		return result;
	}
	
	// add employee availability to a record
	public void addAvailabilityInfo(String employeeID, String date, String startTime, String endTime)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
			prep.setString(1, employeeID);
			prep.setString(2, date);
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
				String dateStr = result.getString("date");
				String key = employeeID + ":" + dateStr;
				String startTimeStr = result.getString("startTime");
				String endTimeStr = result.getString("endTime");
				timeList.add(dateStr);
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
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("SELECT " + col + " FROM AVAILABILITY WHERE " + col + " = '" + value + "';");
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
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return checkExists;
	}
	
	public void deleteAvail(String id, String date)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("DELETE FROM AVAILABILITY WHERE employeeID = '" +id+ 
					"' AND date = '" + date + "'");
			result = prep.executeQuery();
			prep.close();
			result.close();
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}