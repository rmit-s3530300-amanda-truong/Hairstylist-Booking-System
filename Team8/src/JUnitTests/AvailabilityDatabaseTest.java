package JUnitTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import Database.AvailabilityDatabase;
public class AvailabilityDatabaseTest {

	AvailabilityDatabase availDb;
	Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet result = null;
	PreparedStatement prep = null;
	
	@Before
	public void setUp() throws SQLException
	{
		availDb = new AvailabilityDatabase();
		conn = availDb.initialise();
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
		result = conn.getMetaData().getTables(null, null, "AVAILABILITY", null);
		while(result.next()){
			String name = result.getString("TABLE_NAME");
			if(name.equals("AVAILABILITY"))
			{
				actual = true;
			}
		}
		assertEquals(expected,actual);
	}
	
	@Test
	public void testAddAvailability()
	{
		Boolean expected = true;
		Boolean actual = false;
		String employeeID = "e1";
		String day = "Thursday";
		String startTime = "14:00";
		String endTime = "16:00";
		availDb.addAvailabilityInfo(employeeID, day, startTime, endTime);
		
		actual = availDb.checkValueExists("employeeID", employeeID);
		assertEquals(expected,actual);
		actual = false;
		
		actual = availDb.checkValueExists("day", day);
		assertEquals(expected,actual);
		actual = false;
		
		actual = availDb.checkValueExists("startTime", startTime);
		assertEquals(expected,actual);
		actual = false;
		
		actual = availDb.checkValueExists("endTime", endTime);
		assertEquals(expected,actual);
		actual = false;
	}
	
	@Test
	public void testdeleteAvailability() throws SQLException
	{
		Boolean expected = false;
		Boolean actual = true;
		String employeeID = "e1";
		String day = "Thursday";
		String startTime = "12:00";
		String endTime = "15:00";
		availDb.addAvailabilityInfo(employeeID, day, startTime, endTime);
		availDb.deleteAvail(employeeID, day);
		actual = availDb.checkValueExists(day, "Thursday");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testStoreValues()
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean expected = true;
		String employeeID = "e1";
		String day = "Thursday";
		String startTime = "12:00";
		String endTime = "15:00";
		availDb.addAvailabilityInfo(employeeID, day, startTime, endTime);
		HashMap<String, ArrayList<String>> expectedMap = new HashMap<String,ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		list.add(day);
		list.add(startTime);
		list.add(endTime);
		String key = employeeID + ":" + day;
		expectedMap.put(key, list);
		
		HashMap<String, ArrayList<String>> actualMap = new HashMap<String,ArrayList<String>>();
		actualMap = availDb.storeAvailValues();
		
		for (String keyCheck : actualMap.keySet())
        {
            if (expectedMap.containsKey(keyCheck)) {
                actual = true;
            }
        } 
		for (String valCheck : expectedMap.keySet())
        {
            if (actualMap.get(valCheck).equals(expectedMap.get(valCheck))) {
                actual2 = true;
            }
        } 
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
	}

	@Test
	public void testcheckValueExists()
	{
		String employeeID = "e1";
		String day = "Monday";
		String startTime = "12:00";
		String endTime = "15:00";
		availDb.addAvailabilityInfo(employeeID, day, startTime, endTime);
		
		Boolean actual = false;
		Boolean expected = true;
		String col = "employeeID";
		String value = "e1";
		actual = availDb.checkValueExists(col,value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists2()
	{
		String employeeID = "e1";
		String day = "Monday";
		String startTime = "12:00";
		String endTime = "15:00";
		availDb.addAvailabilityInfo(employeeID, day, startTime, endTime);
		
		Boolean actual = true;
		Boolean expected = false;
		String col = "employeeID";
		String value = "e3";
		actual = availDb.checkValueExists(col, value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists3()
	{
		String employeeID = "e1";
		String day = "Monday";
		String startTime = "12:00";
		String endTime = "15:00";
		availDb.addAvailabilityInfo(employeeID, day, startTime, endTime);
		
		Boolean actual = false;
		Boolean expected = true;
		String col = "day";
		String value = "Monday";
		actual = availDb.checkValueExists(col, value);
		assertEquals(expected,actual);
		
		actual = false;
		expected = true;
		String col2 = "startTime";
		String value2= "12:00";
		actual = availDb.checkValueExists(col2, value2);
		assertEquals(expected,actual);
		
		actual = false;
		expected = true;
		String col3 = "endTime";
		String value3= "15:00";
		actual = availDb.checkValueExists(col3, value3);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testAddTest()	
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean actual3 = false;
		Boolean expected = true;
		availDb.addTest();	
		actual = availDb.checkValueExists("employeeID", "e1");
		actual2 = availDb.checkValueExists("employeeID", "e2");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = availDb.checkValueExists("day","Monday");
		actual2 = availDb.checkValueExists("day","Tuesday");
		actual3 = availDb.checkValueExists("day","Wednesday");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = availDb.checkValueExists("startTime","08:15");
		actual2 = availDb.checkValueExists("startTime","09:15");		
		actual3 = availDb.checkValueExists("startTime","13:00");		
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = availDb.checkValueExists("endTime","10:15");
		actual2 = availDb.checkValueExists("endTime","12:15");
		actual3 = availDb.checkValueExists("endTime","15:00");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
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
