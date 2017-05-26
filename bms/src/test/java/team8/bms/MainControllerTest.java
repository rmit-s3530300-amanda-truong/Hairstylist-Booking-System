package team8.bms;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import team8.bms.business.Company;
import team8.bms.database.AvailabilityDatabase;
import team8.bms.database.BookingDatabase;
import team8.bms.database.CompanyDatabase;
import team8.bms.database.CustomerDatabase;
import team8.bms.database.ServicesDatabase;
import team8.bms.mainController.MainController;

public class MainControllerTest {
	Company comp;
	CompanyDatabase compDb;
	CustomerDatabase custDb;
	AvailabilityDatabase availDb;
	BookingDatabase bookDb;
	ServicesDatabase servDb;
	MainController m1;
	
	String uname;
	String name;
	String pass;
	String mobileNo;
	String suburbName;
	String zipCode;
	String addressLine;
	
	@Before
	public void setUp() throws Exception {
		compDb = new CompanyDatabase();
		custDb = new CustomerDatabase();
		availDb = new AvailabilityDatabase();
		comp = new Company("John", "Bishop", "abcboss", "password", "0430202101", "1 Bossy Street, Bossville, 3000");
		bookDb = new BookingDatabase();
		servDb = new ServicesDatabase();
		m1 = new MainController(comp,custDb,compDb,availDb, bookDb, servDb);
		
		
		uname = "^(?=^.{5,}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$";
		name = "^[a-zA-Z-\\s]*$";
		pass = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$)(?=.*[@#$%^!&+=]).{6,}$";
		mobileNo = "^(?:\\+?61|0)4 ?(?:(?:[01] ?[0-9]|2 ?[0-57-9]|3 ?[1-9]|4 ?[7-9]|5 ?[018]) ?[0-9]|3 ?0 ?[0-5])(?: ?[0-9]){5}$";
		suburbName = "^([a-zA-Z](\\s?))*$";
		zipCode = "^[0-9]{4}$";
		addressLine = "^\\d+\\s[A-z]+\\s[A-z]+";
	}
	
	@Test
	public void testAuthenticate1(){
		String username = "jbrown";
		String password = "password";
		String buss = "ABC HAIRSTYLIST";
		String check = m1.authenticate(username, password, buss);
		assertEquals("customer",check);
	}
	
	@Test
	public void testAuthenticate2(){
		String username = "abcboss";
		String password = "password";
		String buss = "ABC HAIRSTYLIST";
		String check = m1.authenticate(username, password, buss);
		assertEquals("business",check);
	}
	
	@Test
	public void testAuthenticate3(){
		String username = "abcboss";
		String password = "pass";
		String buss = "ABC HAIRSTYLIST";
		String check = m1.authenticate(username, password, buss);
		assertEquals("false",check);
	}
	
	@Test
	public void testAuthenticate4(){
		String username = "abcboss";
		String password = "pass";
		String buss = "DEF HAIRSTYLIST";
		String check = m1.authenticate(username, password, buss);
		assertEquals("false",check);
	}
	
	@Test
	public void testUniqueUsename1(){
		String username = "jbrown";
		String buss = "ABC HAIRSTYLIST";
		boolean check = m1.uniqueUname(username, buss);
		assertEquals(false,check);
	}
	
	@Test
	public void testUniqueUsename2(){
		String username = "j_patel";
		String buss = "ABC HAIRSTYLIST";
		boolean check = m1.uniqueUname(username, buss);
		assertEquals(true,check);
	}
	
	@Test
	public void testUniqueUsename3(){
		String username = "jbrown";
		String buss = "DEF HAIRSTYLIST";
		boolean check = m1.uniqueUname(username, buss);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidEndTime1(){
		String startTimeHour = "08";
		String startTimeMinute = "40";
		String endTimeHour = "10";
		String endTimeMinute = "20";
		boolean check = m1.validEndTime(startTimeHour, endTimeHour, startTimeMinute, endTimeMinute);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidEndTime2(){
		String startTimeHour = "08";
		String startTimeMinute = "40";
		String endTimeHour = "08";
		String endTimeMinute = "40";
		boolean check = m1.validEndTime(startTimeHour, endTimeHour, startTimeMinute, endTimeMinute);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidEndTime3(){
		String startTimeHour = "08";
		String startTimeMinute = "40";
		String endTimeHour = "09";
		String endTimeMinute = "30";
		boolean check = m1.validEndTime(startTimeHour, endTimeHour, startTimeMinute, endTimeMinute);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidEndTime4(){
		String startTimeHour = "08";
		String startTimeMinute = "40";
		String endTimeHour = "09";
		String endTimeMinute = "40";
		boolean check = m1.validEndTime(startTimeHour, endTimeHour, startTimeMinute, endTimeMinute);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidEndTime5(){
		String startTimeHour = "10";
		String startTimeMinute = "30";
		String endTimeHour = "08";
		String endTimeMinute = "00";
		boolean check = m1.validEndTime(startTimeHour, endTimeHour, startTimeMinute, endTimeMinute);
		assertEquals(false,check);
	}
	
	@Test
	public void testIdValid() {
		String username = "e1";
		Boolean expected = true;
		Boolean actual = m1.idValid(username);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIdValidFail() {
		String username = "e5";
		Boolean expected = false;
		Boolean actual = m1.idValid(username);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testValidUname1() {
		String uName = "j_snow";
		boolean check = m1.validate(uName, uname);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidUname2() {
		String uName = "jsnow";
		boolean check = m1.validate(uName, uname);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidUname3() {
		String uName = "12345";
		boolean check = m1.validate(uName, uname);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidUname4() {
		String uName = "js123";
		boolean check = m1.validate(uName, uname);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidUname5() {
		String uName = "j_snow123";
		boolean check = m1.validate(uName, uname);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidPassword1() {
		String password = "1234";
		boolean check = m1.validate(password, pass);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidPassword2() {
		String password = "abcd";
		boolean check = m1.validate(password, pass);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidPassword3() {
		String password = "abc123";
		boolean check = m1.validate(password, pass);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidPassword4() {
		String password = "aBc123";
		boolean check = m1.validate(password, pass);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidPassword5() {
		String password = "aBc1@";
		boolean check = m1.validate(password, pass);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidPassword6() {
		String password = "aBc12@";
		boolean check = m1.validate(password, pass);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidFName() {
		String fname = "john";
		boolean check = m1.validate(fname, name);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile1() {
		String mobile = "0412345678";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile2() {
		String mobile = "0412 345 678";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile3() {
		String mobile = "";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidMobile4() {
		String mobile = "412345678";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidMobile5() {
		String mobile = "+61412345678";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile6() {
		String mobile = "+614 1234 5678";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile7() {
		String mobile = "04 1234 5678";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile8() {
		String mobile = "+61412 345 678";
		boolean check = m1.validate(mobile, mobileNo);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidAddressLine1() {
		String sName = "445 Swanston Street";
		boolean check = m1.validate(sName, addressLine);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidAddressLine2() {
		String sName = "445 Swanston";
		boolean check = m1.validate(sName, addressLine);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidAddressLine3() {
		String sName = "445";
		boolean check = m1.validate(sName, addressLine);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidSuburb() {
		String suburb = "melbourne";
		boolean check = m1.validate(suburb, suburbName);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidZip() {
		String zip = "3000";
		boolean check = m1.validate(zip, zipCode);
		assertEquals(true,check);
	}
	
	@Test
	public void testgetCompany() {
		Company compny = m1.getCompany();
		assertEquals(compny, comp);
	}
}
