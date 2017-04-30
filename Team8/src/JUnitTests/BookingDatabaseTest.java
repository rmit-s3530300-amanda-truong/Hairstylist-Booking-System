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
	public void testDeleteBooking()
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
		String bookingID = "2017-05-02/09:15";
		String customerUsername = "jbrown";
		String service = "maleCut";
		String employeeID = "e1";
		String date = "2017-05-02";
		String startTime = "09:15";
		String endTime = "09:30";
		String time = startTime + "-" + endTime;
		String status = "booking";
		
		String bookingID2 = "2017-05-03/13:00";
		String customerUsername2 = "rgeorge";
		String service2 = "femaleCut";
		String employeeID2 = "e1";
		String date2 = "2017-05-03";
		String startTime2 = "13:00";
		String endTime2 = "13:30";
		String time2 = startTime2 + "-" + endTime2;
		String status2 = "booking";
		
		String bookingID3 = "2017-05-03/13:15";
		String customerUsername3 = "rgeorge";
		String service3 = "femaleCut";
		String employeeID3 = "e1";
		String date3 = "2017-05-03";
		String startTime3 = "13:00";
		String endTime3 = "13:15";
		String time3 = startTime3 + "-" + endTime3;
		String status3 = "booking";
		
		String bookingID4 = "2017-04-26/13:00";
		String customerUsername4 = "jbrown";
		String service4 = "maleCut";
		String employeeID4 = "e2";
		String date4 = "2017-04-26";
		String startTime4 = "13:00";
		String endTime4 = "13:15";
		String time4 = startTime4 + "-" + endTime4;
		String status4 = "booking";
		
		String bookingID5 = "2017-04-19/14:00";
		String customerUsername5 = "jbrown";
		String service5 = "maleCut";
		String employeeID5 = "e2";
		String date5 = "2017-04-19";
		String startTime5 = "14:00";
		String endTime5 = "14:15";
		String time5 = startTime5 + "-" + endTime5;
		String status5 = "booking";
		
		HashMap<String, ArrayList<String>> expectedMap = new HashMap<String,ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> list3 = new ArrayList<String>();
		ArrayList<String> list4 = new ArrayList<String>();
		ArrayList<String> list5 = new ArrayList<String>();
		list.add(customerUsername);
		list.add(date);
		list.add(time);
		list.add(employeeID);
		list.add(service);
		list.add(status);
		expectedMap.put(bookingID, list);
		
		list2.add(customerUsername2);
		list2.add(date2);
		list2.add(time2);
		list2.add(employeeID2);
		list2.add(service2);
		list2.add(status2);
		expectedMap.put(bookingID2, list2);
		
		list3.add(customerUsername3);
		list3.add(date3);
		list3.add(time3);
		list3.add(employeeID3);
		list3.add(service3);
		list3.add(status3);
		expectedMap.put(bookingID3, list3);
		
		list4.add(customerUsername4);
		list4.add(date4);
		list4.add(time4);
		list4.add(employeeID4);
		list4.add(service4);
		list4.add(status4);
		expectedMap.put(bookingID4, list4);
		
		list5.add(customerUsername5);
		list5.add(date5);
		list5.add(time5);
		list5.add(employeeID5);
		list5.add(service5);
		list5.add(status5);
		expectedMap.put(bookingID5, list5);
		
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
		Boolean actual4= false;
		Boolean actual5 = false;
		Boolean expected = true;
		
		bookingDb.addTest();
		
		actual = bookingDb.checkValueExists("bookingID", "2017-05-02/09:15");
		actual2 = bookingDb.checkValueExists("bookingID", "2017-05-03/13:00");
		actual3 = bookingDb.checkValueExists("bookingID", "2017-05-03/13:15");
		actual4 = bookingDb.checkValueExists("bookingID", "2017-04-26/13:00");
		actual5 = bookingDb.checkValueExists("bookingID", "2017-04-19/14:00");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		assertEquals(expected,actual4);
		assertEquals(expected,actual5);
		actual = false;
		actual2 = false;
		actual3 = false;
		actual4 = false;
		actual5 = false;
		
		actual = bookingDb.checkValueExists("custUsername", "jbrown");
		actual2 = bookingDb.checkValueExists("custUsername", "rgeorge");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = bookingDb.checkValueExists("service", "maleCut");
		actual2 = bookingDb.checkValueExists("service", "femaleCut");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = bookingDb.checkValueExists("employeeID", "e1");
		actual2 = bookingDb.checkValueExists("employeeID", "e2");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = bookingDb.checkValueExists("date","2017-05-02");
		actual2 = bookingDb.checkValueExists("date","2017-05-03");		
		actual3 = bookingDb.checkValueExists("date","2017-04-26");
		actual4 = bookingDb.checkValueExists("date","2017-04-19");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		assertEquals(expected,actual4);
		actual = false;
		actual2 = false;
		actual3 = false;
		actual4 = false;
		
		actual = bookingDb.checkValueExists("time","09:15-09:30");
		actual2 = bookingDb.checkValueExists("time","13:00-13:30");
		actual3 = bookingDb.checkValueExists("time","13:00-13:15");
		actual4 = bookingDb.checkValueExists("time","14:00-14:15");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		assertEquals(expected,actual4);
		actual = false;
		actual2 = false;
		actual3 = false;
		actual4 = false;
		
		actual = bookingDb.checkValueExists("status", "booking");
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
