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
	boolean hasData = false;
	boolean check = false;
	
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
		String gender = "boy";
		String mobile = "0412123123";
		String address = "1 happy street, happy, 3000";
		
		db1.addCustInfo("bsmith","bob","smith","pass","boy","0412123123","1 happy street, happy, 3000");
		
		check = db1.checkValue(uname);
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
	public void testAddTest()	
	{
		db1.addTest();
		check = db1.checkRows();
		assertEquals(true,check);
	}
	
	@Test
	public void testCheckConnetion()
	{
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
