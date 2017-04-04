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
		db1.initialise("customer");
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
		if(db1.checkTable("customer") == true)
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
		
		check = db1.checkExists("customer","username",uname);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("customer","fname",fname);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("customer","lname",lname);		
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("customer","password",password);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("customer","gender",gender);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("customer","mobile",mobile);
		assertEquals(true,check);
		check = false;
		
		check = db1.checkExists("customer","address",address);
		assertEquals(true,check);
		check = false;
	}
	
	@Test
	public void testDeleteAllR() throws SQLException
	{
		Boolean checkR;
		db1.deleteAllR("customer","CUSTINFO");
		checkR = db1.checkRows("customer");
		assertEquals(false,checkR);
	}
	
	@Test
	public void testCheckLogin()
	{
		Boolean checkT = false;
		Boolean checkF = true;
		String username = "jpoop";
		String password = "password";
		checkT = db1.checkLogin("customer",username,password);
		assertEquals(true,checkT);
		
		String username2 = "jp";
		String password2 = "pw";
		checkF = db1.checkLogin("customer",username2,password2);
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
		checkT = db1.checkAuthen("customer",authen,result,username,password);
		assertEquals(true,checkT);
		
		String username2 = "jp";
		String password2 = "pw";
		checkF = db1.checkAuthen("customer",authen,result,username2,password2);
		assertEquals(false,checkF);
	}
	
	@Test
	public void testAddTest()	
	{
		Boolean check = false;
		Boolean check2 = false;
		Boolean check3 = false;
		db1.addTest("customer");	
		check = db1.checkExists("customer","username", "jpoop");
		check2 = db1.checkExists("customer","username", "rgeorge");
		check3 = db1.checkExists("customer","username", "bstar");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("customer","fname","John");
		check2 = db1.checkExists("customer","fname","Regina");
		check3 = db1.checkExists("customer","fname","Bob");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("customer","lname","Poop");
		check2 = db1.checkExists("customer","lname","George");		
		check3 = db1.checkExists("customer","lname","Star");		
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("customer","password","password");
		check2 = db1.checkExists("customer","password","password1");
		check3 = db1.checkExists("customer","password","password2");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("customer","gender","male");
		check2 = db1.checkExists("customer","gender","female");
		check3 = db1.checkExists("customer","gender","female");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("customer","mobile","0412123123");
		check2 = db1.checkExists("customer","mobile","0469123123");
		check3 = db1.checkExists("customer","mobile","0469999999");
		assertEquals(true,check);
		assertEquals(true,check2);
		assertEquals(true,check3);
		check = false;
		check2 = false;
		check3 = false;
		
		check = db1.checkExists("customer","address","1 Happy Street, Happyville, 3000, nsw");
		check2 = db1.checkExists("customer","address","1 Sad street, Sadville, 2000, vic");
		check3 = db1.checkExists("customer","address","1 Angry Street, Angryville, 3333, vic");
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
		
		checkT = db1.checkExists("customer",col,value);
		assertEquals(true,checkT);
		
		checkF = db1.checkExists("customer",col2, value2);
		assertEquals(false,checkF);
	}
	
	@Test
	public void testCheckTable()
	{
		check = db1.checkTable("customer");
		assertEquals(true,check);
	}
	
	@Test
	public void testCheckRows()
	{
		db1.addTest("customer");
		check = db1.checkRows("customer");
		assertEquals(true,check);
		
		db1.deleteAllR("customer","CUSTINFO");
		check = db1.checkRows("customer");
		assertEquals(false,check);
	}
	
	@Test
	public void testCheckConnetion()
	{
		Boolean check = false;
		db1.initialise("customer");
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
