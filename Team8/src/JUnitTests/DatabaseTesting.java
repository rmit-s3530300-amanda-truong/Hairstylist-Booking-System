package JUnitTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import Database.Database;
public class DatabaseTesting {

	Database db1 = new Database();
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
	
	@Test 
	public void testConnection() throws SQLException
	{
		if(db1.checkConnection() == true)
		{
			check = true;
		}
		else
		{
			check = false;
		}
		
		assertEquals(true,check);
	}
	
	@Test
	public void testCreateTable() throws SQLException
	{
		if(db1.checkTable() == true)
		{
			check = true;
		}
		else
		{
			check = false;
		}
		assertEquals(true,check);
	}
	
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
	public void testDeleteAllR() throws SQLException
	{
		Boolean checkR;
		db1.deleteAllR("CUSTINFO");
		checkR = db1.checkRows();
		assertEquals(false,checkR);
	}
	
	@Test
	public void testCheckLogin()
	{
		Boolean checkT = false;
		Boolean checkF = true;
		String username = "jpoop";
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
		String username = "jpoop";
		String password = "password";
		checkT = db1.checkAuthen(authen,result,username,password);
		assertEquals(true,checkT);
		
		String username2 = "jp";
		String password2 = "pw";
		checkF = db1.checkAuthen(authen,result,username2,password2);
		assertEquals(false,checkF);
	}
	
	@Test
	public void testAddTest()	
	{
		Boolean check = false;
		Boolean check2 = false;
		Boolean check3 = false;
		db1.addTest();	
		check = db1.checkExists("username", "jpoop");
		check2 = db1.checkExists("username", "gpoop");
		check3 = db1.checkExists("username", "hithere");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("fname","john");
		check2 = db1.checkExists("fname","girly");
		check3 = db1.checkExists("fname","hi");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("lname","poop");
		check2 = db1.checkExists("lname","poop1");		
		check3 = db1.checkExists("lname","there");		
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
		
		check = db1.checkExists("address","1 happy street, happy surburb, 3000, nsw");
		check2 = db1.checkExists("address","1 sad street, sad surburb, 2000, vic");
		check3 = db1.checkExists("address","1 angry street, angry surburb, 3333, vic");
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
		String value = "jpoop";
		String col2 = "username";
		String value2 = "jp";
		
		checkT = db1.checkExists(col,value);
		assertEquals(true,checkT);
		
		checkF = db1.checkExists(col2, value2);
		assertEquals(false,checkF);
	}
	
	@Test
	public void testCheckTable()
	{
		check = db1.checkTable();
		assertEquals(true,check);
	}
	
	@Test
	public void testCheckRows()
	{
		db1.addTest();
		check = db1.checkRows();
		assertEquals(true,check);
		
		db1.deleteAllR("CUSTINFO");
		check = db1.checkRows();
		assertEquals(false,check);
	}
	
	@Test
	public void testCheckConnetion()
	{
		Boolean check = false;
		db1.initialise();
		check = db1.checkConnection();
		assertEquals(true,check);
		
		db1.closeConn();
		check = db1.checkConnection();
		assertEquals(false,check);
	}
	
	@Test
	public void testCloseConnection()
	{
		db1.closeConn();
		if(db1.checkConnection() == true)
		{
			check = true;
		}
		else
		{
			check = false;
		}
		assertEquals(false,check);
	}
	
	@After
	public void tearDown() throws SQLException
	{
		db1.closeConn();
	}

}
