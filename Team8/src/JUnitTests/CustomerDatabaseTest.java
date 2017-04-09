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

import Database.CustomerDatabase;
public class CustomerDatabaseTest {

	CustomerDatabase custDb = new CustomerDatabase();
	Connection conn = null;
	Statement stmt = null;
	ResultSet result = null;
	PreparedStatement prep = null;
	
	@Before
	public void setUp() throws SQLException
	{
		conn = custDb.initialise();
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
		result = conn.getMetaData().getTables(null, null, "CUSTINFO", null);
		while(result.next()){
			String name = result.getString("TABLE_NAME");
			if(name.equals("CUSTINFO"))
			{
				actual = true;
			}
		}
		assertEquals(expected,actual);
	}
	
	@Test
	public void testAddCustInfo() throws SQLException {
		Boolean actual = false;
		Boolean expected = true;
		String uname = "bsmith";
		String fname = "bob";
		String lname = "smith";
		String password = "pass";
		String gender = "male";
		String mobile = "0412123123";
		String address = "1 happy street, happy, 3000, vic";
		
		custDb.addCustInfo(uname,fname,lname,password,gender,mobile,address);
		
		actual = custDb.checkValueExists("username",uname);
		assertEquals(expected,actual);
		actual = false;
		
		actual = custDb.checkValueExists("fname",fname);
		assertEquals(expected,actual);
		actual = false;
		
		actual = custDb.checkValueExists("lname",lname);		
		assertEquals(expected,actual);
		actual = false;
		
		actual = custDb.checkValueExists("password",password);
		assertEquals(expected,actual);
		actual = false;
		
		actual = custDb.checkValueExists("gender",gender);
		assertEquals(expected,actual);
		actual = false;
		
		actual = custDb.checkValueExists("mobile",mobile);
		assertEquals(expected,actual);
		actual = false;
		
		actual = custDb.checkValueExists("address",address);
		assertEquals(expected,actual);
		actual= false;
	}
	
	@Test
	public void testStoreCustValues()
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean expected = true;
		HashMap<String, HashMap<String,String>> checkMap = new HashMap<String,HashMap<String,String>>();
		HashMap<String, HashMap<String,String>> custValues = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> custInfo = new HashMap<String,String>();
		String id = "jbrown";
		String fname = "John";
		String lname = "Brown";
		String gender = "male";
		String id2 = "rgeorge";
		String fname2 = "Regina";
		String lname2 = "George";
		String gender2 = "female";
		String id3 = "tswizzle";
		String fname3 = "Taylor";
		String lname3 = "Swift";
		String gender3 = "female";
		custInfo.put("fName",fname);
		custInfo.put("lName",lname);
		custInfo.put("gender",gender);
		custValues.put(id,custInfo);
		custInfo.clear();
		custInfo.put("fName",fname2);
		custInfo.put("lName",lname2);
		custInfo.put("gender",gender2);
		custValues.put(id2,custInfo);
		custInfo.clear();
		custInfo.put("fName",fname3);
		custInfo.put("lName",lname3);
		custInfo.put("gender",gender3);
		custValues.put(id3,custInfo);
		checkMap = custDb.storeCustomerValues();
		for (String key : checkMap.keySet())
        {
            if (custValues.containsKey(key)) {
                actual = true;
            }
        } 
		for (String val : custValues.keySet())
        {
            if (checkMap.get(val).equals(custValues.get(val))) {
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
		String username = "jbrown";
		String password = "password";
		actual = custDb.checkLogin(username,password);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testCheckLogin2()
	{
		Boolean actual = true;
		Boolean expected = false;
		String username = "jbrown";
		String password = "pw";
		actual = custDb.checkLogin(username,password);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testCheckLogin3()
	{
		Boolean actual = true;
		Boolean expected = false;
		String username = "jb";
		String password = "password";
		actual = custDb.checkLogin(username,password);
		assertEquals(expected,actual);
	}

	@Test
	public void testAddTest()	
	{
		Boolean actual = false;
		Boolean actual2 = false;
		Boolean actual3 = false;
		Boolean expected = true;
		custDb.addTest();	
		actual = custDb.checkValueExists("username", "jbrown");
		actual2 = custDb.checkValueExists("username", "rgeorge");
		actual3 = custDb.checkValueExists("username", "tswizzle");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = custDb.checkValueExists("fname","John");
		actual2 = custDb.checkValueExists("fname","Regina");
		actual3 = custDb.checkValueExists("fname","Taylor");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = custDb.checkValueExists("lname","Brown");
		actual2 = custDb.checkValueExists("lname","George");		
		actual3 = custDb.checkValueExists("lname","Swift");		
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = custDb.checkValueExists("password","password");
		actual2 = custDb.checkValueExists("password","password1");
		actual3 = custDb.checkValueExists("password","password2");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = custDb.checkValueExists("gender","male");
		actual2 = custDb.checkValueExists("gender","female");
		actual3 = custDb.checkValueExists("gender","female");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = custDb.checkValueExists("mobile","0412123123");
		actual2 = custDb.checkValueExists("mobile","0469123123");
		actual3 = custDb.checkValueExists("mobile","0469999999");
		assertEquals(expected,actual);
		assertEquals(expected,actual2);
		assertEquals(expected,actual3);
		actual = false;
		actual2 = false;
		actual3 = false;
		
		actual = custDb.checkValueExists("address","1 Happy Street, Happyville, 3000, nsw");
		actual2 = custDb.checkValueExists("address","1 Sad street, Sadville, 2000, vic");
		actual3 = custDb.checkValueExists("address","1 Sing Street, Singville, 3333, vic");
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
		String value = "jbrown";
		actual = custDb.checkValueExists(col,value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists2()
	{
		Boolean actual = true;
		Boolean expected = false;
		String col = "username";
		String value = "jp";
		actual = custDb.checkValueExists(col, value);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testcheckValueExists3()
	{
		Boolean actual = false;
		Boolean expected = true;
		String col = "username";
		String value = "rgeorge";
		actual = custDb.checkValueExists(col, value);
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