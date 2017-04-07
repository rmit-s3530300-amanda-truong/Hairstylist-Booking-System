package JUnitTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import Database.CustomerDatabase;
public class DatabaseTesting {

	CustomerDatabase db1 = new CustomerDatabase();
	Connection conn = null;
	Statement stmt = null;
	ResultSet result = null;
	ResultSet result2 = null;
	Boolean hasData = false;
	Boolean check = false;
	
	@Before
	public void setUp() throws SQLException
	{
		db1.initialise();
	}
	/*
	@Test 
	public void testConnection() throws SQLException
	{
		
	}
	
	@Test
	public void testCreateTable() throws SQLException
	{
		
	}
	*/
	@Test
	public void testAddCustInfo() throws SQLException {
		String uname = "bsmith";
		String fname = "bob";
		String lname = "smith";
		String password = "pass";
		String gender = "male";
		String mobile = "0412123123";
		String address = "1 happy street, happy, 3000, vic";
		
		db1.addCustInfo("bsmith","bob","smith","pass","boy","0412123123","1 happy street, happy, 3000, vic");
		
		check = db1.checkExists("username",uname);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("fname",fname);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("lname",lname);		
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("password",password);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("gender",gender);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("mobile",mobile);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("address",address);
		assertEquals(true,check);
		check = false;
	}
	
	@Test
	public void testCheckLogin()
	{
		Boolean checkT = false;
		Boolean checkF = true;
		String username = "jbrown";
		String password = "password";
		checkT = db1.checkLogin(username,password);
		assertEquals(true,checkT);
		
		String username2 = "jp";
		String password2 = "pw";
		checkF = db1.checkLogin(username2,password2);
		assertEquals(false,checkF);
	}
	
	@Test
	public void testCheckAuthen()
	{
		Boolean checkT = false;
		Boolean checkF = true;
		Boolean authen = null;
		String username = "jbrown";
		String password = "password";
		checkT = db1.checkAuthen(authen,username,password);
		assertEquals(true,checkT);
		
		String username2 = "jp";
		String password2 = "pw";
		checkF = db1.checkAuthen(authen,username2,password2);
		assertEquals(false,checkF);
	}
	
	@Test
	public void testAddTest()	
	{
		Boolean check = false;
		Boolean check2 = false;
		Boolean check3 = false;
		db1.addTest();	
		check = db1.checkExists("username", "jbrown");
		check2 = db1.checkExists("username", "rgeorge");
		check3 = db1.checkExists("username", "tswizzle");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("fname","John");
		check2 = db1.checkExists("fname","Regina");
		check3 = db1.checkExists("fname","Taylor");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("lname","Brown");
		check2 = db1.checkExists("lname","George");		
		check3 = db1.checkExists("lname","Swift");		
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("password","password");
		check2 = db1.checkExists("password","password1");
		check3 = db1.checkExists("password","password2");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("gender","male");
		check2 = db1.checkExists("gender","female");
		check3 = db1.checkExists("gender","female");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("mobile","0412123123");
		check2 = db1.checkExists("mobile","0469123123");
		check3 = db1.checkExists("mobile","0469999999");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("address","1 Happy Street, Happyville, 3000, nsw");
		check2 = db1.checkExists("address","1 Sad street, Sadville, 2000, vic");
		check3 = db1.checkExists("address","1 Sing Street, Singville, 3333, vic");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
	}

	@Test
	public void testCheckExists()
	{
		Boolean checkT = false;
		Boolean checkF = true;
		
		String col = "username";
		String value = "jbrown";
		String col2 = "username";
		String value2 = "jp";
		
		checkT = db1.checkExists(col,value);
		assertEquals(true,checkT);
		
		checkF = db1.checkExists(col2, value2);
		assertEquals(false,checkF);
	}
	
	@After
	public void tearDown() throws SQLException
	{
		
	}

}
