package JUnitTests;

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

import Database.CompanyDatabase;
public class CompanyDatabaseTest {

	CompanyDatabase compDb;
	Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet result = null;
	PreparedStatement prep = null;
	
	@Before
	public void setUp() throws SQLException
	{
		compDb = new CompanyDatabase();
		conn = compDb.initialise();
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
		result = conn.getMetaData().getTables(null, null, "COMPANY", null);
		while(result.next()){
			String name = result.getString("TABLE_NAME");
			if(name.equals("COMPANY"))
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
		HashMap<String, HashMap<String,String>> checkMap = new HashMap<String,HashMap<String,String>>();
		HashMap<String, HashMap<String,String>> empValues = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> empInfo = new HashMap<String,String>();
		String id = "e1";
		String fname = "Bob";
		String lname = "Lee";
		String service = "femaleCut, maleCut, femaleDye";
		String id2 = "e2";
		String fname2 = "Elissa";
		String lname2 = "Smith";
		String service2 = "femaleCut";
		empInfo.put("fname",fname);
		empInfo.put("lname",lname);
		empInfo.put("service",service);
		empValues.put(id,empInfo);
		empInfo.clear();
		empInfo.put("fname",fname2);
		empInfo.put("lname",lname2);
		empInfo.put("service",service2);
		empValues.put(id2,empInfo);
		checkMap = compDb.storeEmpValues();
		for (String key : checkMap.keySet())
        {
            if (empValues.containsKey(key)) {
                actual = true;
            }
        } 
		for (String val : empValues.keySet())
        {
            if (checkMap.get(val).equals(empValues.get(val))) {
                actual2 = true;
            }
        } 
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
	}

	@Test
	public void testCheckLogin()
	{
		Boolean actual = false;
		Boolean expected = true;
		String username = "abcboss";
		String password = "password";
		actual = compDb.checkLogin(username,password);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testCheckLogin2()
	{
		Boolean actual = true;
		Boolean expected = false;
		String username = "abcboss";
		String password = "pw";
		actual = compDb.checkLogin(username,password);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testCheckLogin3()
	{
		Boolean actual = true;
		Boolean expected = false;
		String username = "boss";
		String password = "password";
		actual = compDb.checkLogin(username,password);
		assertEquals(expected,actual);
	}

	@Test
	public void testAddTest()	
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean actual3 = false;
		Boolean expected = true;
		compDb.addTest();	
		actual = compDb.checkValueExists("username", "abcboss");
		actual2 = compDb.checkValueExists("username", "e1");
		actual3 = compDb.checkValueExists("username", "e2");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("fname","John");
		actual2 = compDb.checkValueExists("fname","Bob");
		actual3 = compDb.checkValueExists("fname","Elissa");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("lname","Bishop");
		actual2 = compDb.checkValueExists("lname","Lee");		
		actual3 = compDb.checkValueExists("lname","Smith");		
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		//password is null for employee
		actual = compDb.checkValueExists("password","password");
		assertEquals(expected,actual);
		actual = false;
		
		actual = compDb.checkValueExists("gender","male");
		actual2 = compDb.checkValueExists("gender","male");
		actual3 = compDb.checkValueExists("gender","female");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("mobile","0430202101");
		actual2 = compDb.checkValueExists("mobile","0400123000");
		actual3 = compDb.checkValueExists("mobile","0469899898");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("address","1 Bossy Street, Bossville, 3000");
		actual2 = compDb.checkValueExists("address","1 Hair Street, Hairy, 2000");
		actual3 = compDb.checkValueExists("address","1 ChoppaChoppa Street, Choparoo, 3333");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		//service is null for business owner
		actual2 = compDb.checkValueExists("service","femaleCut, maleCut, femaleDye");
		actual3 = compDb.checkValueExists("service","femaleCut");
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("busStatus","owner");
		actual2 = compDb.checkValueExists("busStatus","employee");
		actual3 = compDb.checkValueExists("busStatus","employee");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
	}

	@Test
	public void testcheckValueExists()
	{
		Boolean actual = false;
		Boolean expected = true;
		String col = "username";
		String value = "abcboss";
		actual = compDb.checkValueExists(col,value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists2()
	{
		Boolean actual = true;
		Boolean expected = false;
		String col = "username";
		String value = "boss";
		actual = compDb.checkValueExists(col, value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists3()
	{
		Boolean actual = false;
		Boolean expected = true;
		String col = "username";
		String value = "e1";
		actual = compDb.checkValueExists(col, value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testCheckEmployees()
	{
		int expected = 2;
		int actual = compDb.checkEmployees();
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
