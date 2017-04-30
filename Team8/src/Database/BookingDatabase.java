package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class BookingDatabase {
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet result = null;
	private static boolean hasData = false;
	private static PreparedStatement prep = null;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public BookingDatabase() {
		this.initialise();
		this.addTest();
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
							+ "bookingID text NOT NULL		,"	
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
	public void addBooking(String bookingID, String custUsername, String service, String employeeID, String date, String time, String status)
	{		
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?,?);");
			prep.setString(1, bookingID);
			prep.setString(2, custUsername);
			prep.setString(3, service);
			prep.setString(4, employeeID);
			prep.setString(5, date);
			prep.setString(6, time);
			prep.setString(7, status);
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
				String bookingID = result.getString("bookingID");
				String customerUsername = result.getString("custUsername");
				String employeeID = result.getString("employeeID");
				String service = result.getString("service");
				String date = result.getString("date");
				String time = result.getString("time");
				String status = result.getString("status");
				info.add(customerUsername);
				info.add(date);
				info.add(time);
				info.add(employeeID);
				info.add(service);
				info.add(status);
				bookingMap.put(bookingID, info);
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
			if(col.equals("bookingID"))
			{
				colName = "bookingID";
			}
			else if(col.equals("employeeID"))
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
			LOGGER.info(e.getClass().getName() + ": " + e.getMessage());
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return checkExists;
	}
	
	public void deleteBooking(String id)
	{
		try
		{
			if(conn.isClosed())
			{
				getConnection();
			}
			prep = conn.prepareStatement("DELETE FROM BOOKING WHERE bookingID = ?");
			prep.setString(1, id);
			prep.execute();
			prep.close();
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
			if(!checkValueExists("bookingID","2017-05-02/09:15") || !checkValueExists("bookingID","2017-05-03/13:00") 
					|| !checkValueExists("bookingID","2017-05-03/13:15") || !checkValueExists("bookingID","2017-04-26/13:00")
					|| !checkValueExists("bookingID","2017-04-19/14:00"))
			{
				if(conn.isClosed())
				{
					getConnection();
				}
				prep = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?,?);");
				prep.setString(1, "2017-05-02/09:15");
				prep.setString(2,"jbrown");
				prep.setString(3,"maleCut");
				prep.setString(4,"e1");
				prep.setString(5,"2017-05-02");
				prep.setString(6,"09:15-09:30");
				prep.setString(7,"booking");
				prep.execute();
				prep.close();
				PreparedStatement prep2 = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?,?);");
				prep2.setString(1, "2017-05-03/13:00");
				prep2.setString(2,"rgeorge");
				prep2.setString(3,"femaleCut");
				prep2.setString(4,"e2");
				prep2.setString(5,"2017-05-03");
				prep2.setString(6,"13:00-13:30");
				prep2.setString(7,"booked");
				prep2.execute();
				prep2.close();
				PreparedStatement prep3 = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?,?);");
				prep3.setString(1, "2017-05-03/13:15");
				prep3.setString(2,"rgeorge");
				prep3.setString(3,"femaleCut");
				prep3.setString(4,"e2");
				prep3.setString(5,"2017-05-03");
				prep3.setString(6,"13:00-13:30");
				prep3.setString(7,"booked");
				prep3.execute();
				prep3.close();
				PreparedStatement prep4 = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?,?);");
				prep4.setString(1, "2017-04-26/13:00");
				prep4.setString(2,"jbrown");
				prep4.setString(3,"maleCut");
				prep4.setString(4,"e2");
				prep4.setString(5,"2017-04-26");
				prep4.setString(6,"13:00-13:15");
				prep4.setString(7,"booked");
				prep4.execute();
				prep4.close();
				PreparedStatement prep5 = conn.prepareStatement("INSERT INTO BOOKING values(?,?,?,?,?,?,?);");
				prep5.setString(1, "2017-04-19/14:00");
				prep5.setString(2,"jbrown");
				prep5.setString(3,"maleCut");
				prep5.setString(4,"e2");
				prep5.setString(5,"2017-04-19");
				prep5.setString(6,"14:00-14:15");
				prep5.setString(7,"booked");
				prep5.execute();
				prep5.close();
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