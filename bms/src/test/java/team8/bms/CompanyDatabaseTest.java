package team8.bms;

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

import team8.bms.database.CompanyDatabase;

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
		result = conn.getMetaData().getTables(null, null, "EMPLOYEE", null);
		while(result.next()){
			String name = result.getString("TABLE_NAME");
			if(name.equals("EMPLOYEE"))
			{
				actual = true;
			}
		}
		assertEquals(expected,actual);
	}
	
	@Test
	public void testCreateTable2() throws SQLException
	{
		Boolean actual = false;
		Boolean expected = true;
		result = conn.getMetaData().getTables(null, null, "BUSINESS", null);
		while(result.next()){
			String name = result.getString("TABLE_NAME");
			if(name.equals("BUSINESS"))
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
		String compName = "ABC HAIRSTYLIST";
		String compName2 = "DEF HAIRSTYLIST";
		String fname = "Spider";
		String lname = "Man";
		String service = "Female Cut, Male Cut, Female Dye, Male Dye, Female Shave, Male Shave";
		String fname2 = "Bob";
		String lname2 = "Lee";
		String service2 = "Female Cut, Male Cut, Female Dye";
		String id2 = "e2";
		String fname3 = "Elissa";
		String lname3 = "Smith";
		String service3 = "Female Cut, Male Cut, Female Dye, Male Dye, Female Perm, Male Perm, Female Wash, Male Wash";
		empInfo.put("compName2",compName2);
		empInfo.put("fname",fname);
		empInfo.put("lname",lname);
		empInfo.put("service",service);
		empValues.put(id+"-"+compName2,empInfo);
		empInfo.clear();
		empInfo.put("compName",compName);
		empInfo.put("fname",fname2);
		empInfo.put("lname",lname2);
		empInfo.put("service",service2);
		empValues.put(id+"-"+compName,empInfo);
		empInfo.clear();
		empInfo.put("compName",compName);
		empInfo.put("fname",fname3);
		empInfo.put("lname",lname3);
		empInfo.put("service",service3);
		empValues.put(id2+"-"+compName,empInfo);
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
		System.out.println(checkMap);
		System.out.println(empValues);
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
		actual = compDb.checkValueExists("username", "e1","EMPLOYEE");
		actual2 = compDb.checkValueExists("username", "e2","EMPLOYEE");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("fname","Spider","EMPLOYEE");
		actual2 = compDb.checkValueExists("fname","Bob", "EMPLOYEE");
		actual3 = compDb.checkValueExists("fname","Elissa", "EMPLOYEE");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("lname","Man","EMPLOYEE");
		actual2 = compDb.checkValueExists("lname","Lee","EMPLOYEE");		
		actual3 = compDb.checkValueExists("lname","Smith","EMPLOYEE");		
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("mobile","0430202101", "EMPLOYEE");
		actual2 = compDb.checkValueExists("mobile","0400123000", "EMPLOYEE");
		actual3 = compDb.checkValueExists("mobile","0469899898", "EMPLOYEE");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("address","1 Bossy Street, Bossville, 3000", "EMPLOYEE");
		actual2 = compDb.checkValueExists("address","1 Hair Street, Hairy, 2000", "EMPLOYEE");
		actual3 = compDb.checkValueExists("address","1 ChoppaChoppa Street, Choparoo, 3333", "EMPLOYEE");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = compDb.checkValueExists("service","Female Cut, Male Cut, Female Dye, Male Dye, Female Shave, Male Shave", "EMPLOYEE");
		actual2 = compDb.checkValueExists("service","Female Cut, Male Cut, Female Dye", "EMPLOYEE");
		actual3 = compDb.checkValueExists("service","Female Cut, Male Cut, Female Dye, Male Dye, Female Perm, Male Perm, Female Wash, Male Wash", "EMPLOYEE");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
	}
	
	@Test
	public void testAddTest2()	
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean expected = true;
		compDb.addTest();	
		actual = compDb.checkValueExists("username", "abcboss","BUSINESS");
		actual2 = compDb.checkValueExists("username", "defboss","BUSINESS");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("compName","ABC HAIRSTYLIST","BUSINESS");
		actual2 = compDb.checkValueExists("compName","DEF HAIRSTYLIST", "BUSINESS");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("fname","John","BUSINESS");
		actual2 = compDb.checkValueExists("fname","Chris", "BUSINESS");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("lname","Bishop","BUSINESS");
		actual2 = compDb.checkValueExists("lname","Pratt","BUSINESS");				
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("password","password","BUSINESS");
		actual2 = compDb.checkValueExists("password","password","BUSINESS");				
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("mobile","0430202101", "BUSINESS");
		actual2 = compDb.checkValueExists("mobile","0423123999", "BUSINESS");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("address","1 Bossy Street, Bossville, 3000", "BUSINESS");
		actual2 = compDb.checkValueExists("address","1 Chris Street, Prattville, 2000", "BUSINESS");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("service","Female Cut, Male Cut, Female Dye, Male Dye, Female Shave, Male Shave", "BUSINESS");
		actual2 = compDb.checkValueExists("service","Female Cut, Male Cut, Female Dye, Male Dye, Female Shave, Male Shave", "BUSINESS");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("busHours","Monday=08:00,16:00|Tuesday=08:00,16:00|Wednesday=08:00,16:00|Thursday=08:00,16:00|Friday=08:00,16:00|Saturday=empty|Sunday=empty", "BUSINESS");
		actual2 = compDb.checkValueExists("busHours","Monday=09:00,17:00|Tuesday=09:00,17:00|Wednesday=09:00,17:00|Thursday=09:00,17:00|Friday=09:00,17:00|Saturday=empty|Sunday=empty", "BUSINESS");
		assertEquals(expected,actual2);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
		
		actual = compDb.checkValueExists("status","verified", "BUSINESS");
		actual2 = compDb.checkValueExists("status","verified", "BUSINESS");
		assertEquals(expected,actual2);
		assertEquals(expected,actual2);
		actual = false;
		actual2 = false;
	}

	@Test
	public void testcheckValueExists()
	{
		Boolean actual = false;
		Boolean expected = true;
		String col = "username";
		String value = "abcboss";
		actual = compDb.checkValueExists(col,value,"BUSINESS");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists2()
	{
		Boolean actual = true;
		Boolean expected = false;
		String col = "username";
		String value = "boss";
		actual = compDb.checkValueExists(col, value, "BUSINESS");
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists3()
	{
		Boolean actual = false;
		Boolean expected = true;
		String col = "username";
		String value = "e1";
		actual = compDb.checkValueExists(col, value, "EMPLOYEE");
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