package JUnitTests;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.BookingDatabase;
public class BookingDatabaseTest {

	BookingDatabase bookingDb;
	Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet result = null;
	PreparedStatement prep = null;
	
	@Before
	public void setUp() throws SQLException
	{
		bookingDb = new BookingDatabase();
		conn = bookingDb.initialise();
	}
	
	@Test
	public void testInitConnection()
	{
		Boolean actual = false;
		Boolean expected = true;
		if(conn != null)
		{
			actual = true;
		}
		assertEquals(expected,actual);
	}
	
	@Test 
	public void testConnection() throws SQLException
	{
		Boolean actual = false;
		Boolean expected = true;
		if(!conn.isClosed())
		{
			actual = true;
		}
		assertEquals(expected,actual);
	}
	
	@Test
	public void testCreateTable() throws SQLException
	{
		Boolean actual = false;
		Boolean expected = true;
		result = conn.getMetaData().getTables(null, null, "BOOKING", null);
		while(result.next()){
			String name = result.getString("TABLE_NAME");
			if(name.equals("BOOKING"))
			{
				actual = true;
			}
		}
		assertEquals(expected,actual);
	}
	
	@Test
	public void testAddBooking()
	{
		Boolean expected = true;
		Boolean actual = false;
		String bookingID = "2017-05-01/08:15";
		String customerUsername = "jbrown";
		String service = "femaleCut";
		String employeeID = "e1";
		String date = "2017-05-01";
		String startTime = "08:15";
		String endTime = "08:30";
		String time = startTime + "-" + endTime;
		String status = "booked";
		bookingDb.addBooking(bookingID, customerUsername, service, employeeID, date, time, status);
		
		actual = bookingDb.checkValueExists("bookingID", bookingID);
		assertEquals(expected,actual);
		actual = false;
		
		actual = bookingDb.checkValueExists("custUsername", customerUsername);
		assertEquals(expected,actual);
		actual = false;
		
		actual = bookingDb.checkValueExists("service", service);
		assertEquals(expected,actual);
		actual = false;
		
		actual = bookingDb.checkValueExists("employeeID", employeeID);
		assertEquals(expected,actual);
		actual = false;
		
		actual = bookingDb.checkValueExists("date", date);
		assertEquals(expected,actual);
		actual = false;
		
		actual = bookingDb.checkValueExists("time", time);
		assertEquals(expected,actual);
		actual = false;
		
		actual = bookingDb.checkValueExists("status", status);
		assertEquals(expected,actual);
		actual = false;
	}
	
	@Test
	public void testdeleteAvailability()
	{
		Boolean expected = false;
		Boolean actual = true;
		String bookingID = "2017-05-01/08:15";
		String customerUsername = "jbrown";
		String service = "femaleCut";
		String employeeID = "e1";
		String date = "2017-05-01";
		String startTime = "08:15";
		String endTime = "08:30";
		String time = startTime + "-" + endTime;
		String status = "booked";
		bookingDb.addBooking(bookingID, customerUsername, service, employeeID, date, time, status);
		bookingDb.deleteBooking(bookingID);
		actual = bookingDb.checkValueExists("date", date);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testStoreValues()
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean expected = true;
		String bookingID = "2017-05-04/08:15";
		String customerUsername = "jbrown";
		String service = "maleCut";
		String employeeID = "e1";
		String date = "2017-05-04";
		String startTime = "08:15";
		String endTime = "08:30";
		String time = startTime + "-" + endTime;
		String status = "booked";
		
		String bookingID2 = "2017-05-03/09:15";
		String customerUsername2 = "rgeorge";
		String service2 = "femaleCut";
		String employeeID2 = "e1";
		String date2 = "2017-05-03";
		String startTime2 = "09:15";
		String endTime2 = "09:30";
		String time2 = startTime + "-" + endTime;
		String status2 = "booked";
		
		String bookingID3 = "2017-05-03/09:30";
		String customerUsername3 = "rgeorge";
		String service3 = "femaleCut";
		String employeeID3 = "e1";
		String date3 = "2017-05-03";
		String startTime3 = "09:30";
		String endTime3 = "09:45";
		String time3 = startTime + "-" + endTime;
		String status3 = "booked";
		
		HashMap<String, ArrayList<String>> expectedMap = new HashMap<String,ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> list3 = new ArrayList<String>();
		list.add(customerUsername);
		list.add(date);
		list.add(time);
		list.add(employeeID);
		list.add(service);
		list.add(status);
		expectedMap.put(bookingID, list);
		
		list2.add(customerUsername);
		list2.add(date);
		list2.add(time);
		list2.add(employeeID);
		list2.add(service);
		list2.add(status);
		expectedMap.put(bookingID2, list2);
		
		list3.add(customerUsername);
		list3.add(date);
		list3.add(time);
		list3.add(employeeID);
		list3.add(service);
		list3.add(status);
		expectedMap.put(bookingID3, list3);
		
		HashMap<String, ArrayList<String>> actualMap = new HashMap<String,ArrayList<String>>();
		actualMap = bookingDb.storeBookingValues();
		
		for (String keyCheck : expectedMap.keySet())
        {
            if (expectedMap.containsKey(keyCheck)) {
                actual = true;
            }
        } 
		for (String valCheck : actualMap.keySet())
        {
            if (actualMap.get(valCheck).equals(expectedMap.get(valCheck))) {
                actual2 = true;
            }
        } 
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
	}

	@Test
	public void testcheckValueExists() throws SQLException
	{
		String bookingID = "2017-05-01/08:15";
		String customerUsername = "jbrown";
		String service = "femaleCut";
		String employeeID = "e1";
		String date = "2017-05-01";
		String startTime = "08:15";
		String endTime = "08:30";
		String time = startTime + "-" + endTime;
		String status = "booked";
		bookingDb.addBooking(bookingID, customerUsername, service, employeeID, date, time, status);
		
		Boolean actual = false;
		Boolean expected = true;
		String col = "bookingID";
		String value = "2017-05-01/08:15";
		actual = bookingDb.checkValueExists(col,value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists2()
	{
		String bookingID = "2017-05-01/08:15";
		String customerUsername = "jbrown";
		String service = "femaleCut";
		String employeeID = "e1";
		String date = "2017-05-01";
		String startTime = "08:15";
		String endTime = "08:30";
		String time = startTime + "-" + endTime;
		String status = "booked";
		bookingDb.addBooking(bookingID, customerUsername, service, employeeID, date, time, status);
		
		Boolean actual = true;
		Boolean expected = false;
		String col = "customerUsername";
		String value = "jbloop";
		actual = bookingDb.checkValueExists(col, value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists3()
	{
		String bookingID = "2017-05-01/08:15";
		String customerUsername = "jbrown";
		String service = "femaleCut";
		String employeeID = "e1";
		String date = "2017-05-01";
		String startTime = "08:15";
		String endTime = "08:30";
		String time = startTime + "-" + endTime;
		String status = "booked";
		bookingDb.addBooking(bookingID, customerUsername, service, employeeID, date, time, status);
		
		Boolean actual = false;
		Boolean expected = true;
		String col = "date";
		String value = "2017-05-01";
		actual = bookingDb.checkValueExists(col, value);
		assertEquals(expected,actual);
		
		actual = false;
		expected = true;
		String col2 = "time";
		String value2= "08:15-08:30";
		actual = bookingDb.checkValueExists(col2, value2);
		assertEquals(expected,actual);
		
		actual = false;
		expected = true;
		String col3 = "status";
		String value3= "booked";
		actual = bookingDb.checkValueExists(col3, value3);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testAddTest()	
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean actual3 = false;
		Boolean expected = true;
		
		bookingDb.addTest();
		
		actual = bookingDb.checkValueExists("bookingID", "2017-05-04/08:15");
		actual2 = bookingDb.checkValueExists("bookingID", "2017-05-03/09:15");
		actual3 = bookingDb.checkValueExists("bookingID", "2017-05-03/09:30");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = bookingDb.checkValueExists("custUsername", "jbrown");
		actual2 = bookingDb.checkValueExists("custUsername", "rgeorge");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = bookingDb.checkValueExists("service", "maleCut");
		actual2 = bookingDb.checkValueExists("service", "femaleCut");
		actual3 = bookingDb.checkValueExists("service", "femaleCut");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = bookingDb.checkValueExists("employeeID", "e1");
		assertEquals(expected,actual);
		actual = false;
		
		actual = bookingDb.checkValueExists("date","2017-05-04");
		actual2 = bookingDb.checkValueExists("date","2017-05-03");		
		actual3 = bookingDb.checkValueExists("date","2017-05-03");		
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = bookingDb.checkValueExists("time","08:15-08:30");
		actual2 = bookingDb.checkValueExists("time","09:15-09:30");
		actual3 = bookingDb.checkValueExists("time","09:30-09:45");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = bookingDb.checkValueExists("status", "booked");
		assertEquals(expected,actual);
	}
	
	@After
	public void tearDown() throws SQLException
	{
		conn.close();
		if(stmt != null)
		{
			stmt.close();
		}
		if(stmt2 != null)
		{
			stmt2.close();
		}
		if(result != null)
		{
			result.close();
		}
		if(prep != null)
		{
			prep.close();
		}
	}

}
