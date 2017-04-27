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
			prep = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?);");
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
	
	public ResultSet displayTable()
	{
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
				System.out.println(result.getString("custUsername") + " " + result.getString("service") 
				+ " " + result.getString("employeeID") + " " + result.getString("date") 
				+ " " + result.getString("time") 
				+ result.getString("status"));
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
				String customerUsername = result.getString("custUsername");
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
			else if(col.equals("custUsername"))
			{
				colName = "custUsername";
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
	
	public void deleteBooking(String id, String date, String time)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			stmt = conn.createStatement();
			String sql = "DELETE FROM BOOKING WHERE custUsername = '" +id+ 
					"' AND date = '" + date + "' AND time = '" + time + "'";
			stmt.execute(sql);
			stmt.close();
			conn.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public void addTest()
	{
		try
		{
			//making sure no duplicates are added when program restarts
			if((!checkValueExists("custUsername","jbrown") && !checkValueExists("date","2017-05-04") && !checkValueExists("time","08:15-10:15")) 
				|| (!checkValueExists("custUsername","rgeorge") && !checkValueExists("date","2017-05-03") && !checkValueExists("time","09:15-12:15")) 
				|| (!checkValueExists("custUsername","tswizzle") && !checkValueExists("date","2017-05-02") && !checkValueExists("time","13:00-15:00")))
			{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?);");
				prep.setString(1,"jbrown");
				prep.setString(2,"maleCut");
				prep.setString(3,"e1");
				prep.setString(4,"2017-05-04");
				prep.setString(5,"08:15-08:30");
				prep.setString(6,"booked");
				prep.execute();
				prep.close();
				PreparedStatement prep2 = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?);");
				prep2.setString(1,"rgeorge");
				prep2.setString(2,"femaleCut");
				prep2.setString(3,"e1");
				prep2.setString(4,"2017-05-03");
				prep2.setString(5,"09:15-09:45");
				prep2.setString(6,"booked");
				prep2.execute();
				prep2.close();
				PreparedStatement prep3 = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?);");
				prep3.setString(1,"tswizzle");
				prep3.setString(2,"femalePerm");
				prep3.setString(3,"e2");
				prep3.setString(4,"2017-05-02");
				prep3.setString(5,"13:00-15:45");
				prep3.setString(6,"booked");
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
}