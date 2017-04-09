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
	
	public void addTest()
	{
		try
		{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
				prep.setString(1,"e1");
				prep.setString(2,"2017-05-10");
				prep.setString(3,"08:15");
				prep.setString(4,"10:00");
				prep.execute();
				prep.close();
				PreparedStatement prep2 = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
				prep2.setString(1,"e2");
				prep2.setString(2,"2017-05-08");
				prep2.setString(3,"09:15");
				prep2.setString(4,"15:00");
				prep2.execute();
				prep2.close();
				PreparedStatement prep3 = conn.prepareStatement("INSERT INTO AVAILABILITY values(?,?,?,?);");
				prep3.setString(1,"e2");
				prep3.setString(2,"2017-05-07");
				prep3.setString(3,"09:15");
				prep3.setString(4,"15:00");
				prep3.execute();
				prep3.close();
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public HashMap<String, ArrayList<String>> storeAvailValues()
	{
		HashMap<String, ArrayList<String>> availabilityMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> timeList = new ArrayList<String>();
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
				String employeeID = result.getString("employeeID");
				String dateStr = result.getString("date");
				String startTimeStr = result.getString("startTime");
				String endTimeStr = result.getString("endTime");
				timeList.add(dateStr);
				timeList.add(startTimeStr);
				timeList.add(endTimeStr);
				availabilityMap.put(employeeID,timeList);
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

}
