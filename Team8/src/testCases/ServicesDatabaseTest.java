package testCases;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import database.ServicesDatabase;
public class ServicesDatabaseTest {

	ServicesDatabase servDb;
	Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet result = null;
	PreparedStatement prep = null;
	
	@Before
	public void setUp() throws SQLException
	{
		servDb = new ServicesDatabase();
		conn = servDb.initialise();
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
		result = conn.getMetaData().getTables(null, null, "SERVICES", null);
		while(result.next()){
			String name = result.getString("TABLE_NAME");
			if(name.equals("SERVICES"))
			{
				actual = true;
			}
		}
		assertEquals(expected,actual);
	}
	
	@Test
	public void testStoreValues()
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean expected = true;
		String service = "maleCut";
		String time = "1";		
		String service2 = "femaleCut";
		String time2 = "2";
		String service3 = "maleDye";
		String time3 = "3";
		String service4= "femaleDye";
		String time4 = "4";
		String service5= "malePerm";
		String time5 = "3";
		String service6= "femalePerm";
		String time6 = "4";
		String service7= "maleWash";
		String time7 = "1";
		String service8= "femaleWash";
		String time8 = "1";
		
		HashMap<String, String> expectedMap = new HashMap<String,String>();
		expectedMap.put(service, time);
		expectedMap.put(service2, time2);
		expectedMap.put(service3, time3);
		expectedMap.put(service4, time4);
		expectedMap.put(service5, time5);
		expectedMap.put(service6, time6);
		expectedMap.put(service7, time7);
		expectedMap.put(service8, time8);
		
		HashMap<String, String> actualMap = new HashMap<String,String>();
		actualMap = servDb.storeServiceValues();
		
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
