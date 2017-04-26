package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BookingDatabase {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	private static PreparedStatement prep = null;
	
	public BookingDatabase() {
		this.initialise();
	}
	
	//get initial connection and create the table
	public Connection initialise()
	{
		Connection connInit = getConnection();
		createBookingTable();
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
	
	private void createBookingTable()
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
					String sql = "CREATE TABLE IF NOT EXISTS BOOKING ("
							+ "custUsername text NOT NULL	,"
							+ "service text NOT NULL		,"
							+ "employeeID text NOT NULL		,"
							+ "date text NOT NULL		,"
							+ "time text NOT NULL		,"
							+ "status text NOT NULL   );";
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
	
	// add booking to a record
	public void addBooking(String custUsername, String service, String employeeID, String date, String time, String status)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?);");
			prep.setString(1, custUsername);
			prep.setString(2, service);
			prep.setString(3, employeeID);
			prep.setString(4, date);
			prep.setString(5, time);
			prep.setString(6, status);
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
	
	public HashMap<String, ArrayList<String>> storeBookingValues()
	{
		HashMap<String, ArrayList<String>> bookingMap = new HashMap<String, ArrayList<String>>();
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			result = stmt.executeQuery("SELECT * FROM BOOKING");
			while (result.next())
			{
				//2017-01-16/10:00 is the format for bookingID
				ArrayList<String> info = new ArrayList<String>();
				String customerUsername = result.getString("customerUsername");
				String employeeID = result.getString("employeeID");
				String service = result.getString("service");
				String date = result.getString("date");
				String time = result.getString("time");
				String status = result.getString("status");
				String key = customerUsername + ":" + date + ":" + time;
/*				String startTimeStr = result.getString("startTime");
				String endTimeStr = result.getString("endTime");*/
				info.add(employeeID);
				info.add(service);
				info.add(date);
				info.add(time);
				info.add(status);
				bookingMap.put(key, info);
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
		return bookingMap;
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
			else if(col.equals("customerUsername"))
			{
				colName = "customerUsername";
			}
			else if(col.equals("service"))
			{
				colName = "service";
			}
			else if(col.equals("date"))
			{
				colName = "date";
			}
			else if(col.equals("time"))
			{
				colName = "time";
			}
			else if(col.equals("status"))
			{
				colName = "status";
			}
			prep = conn.prepareStatement("SELECT * FROM BOOKING WHERE "+colName+" = ?;");
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
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return checkExists;
	}
	
	//add test booking into database
}