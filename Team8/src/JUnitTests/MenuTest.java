package JUnitTests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import Business.Company;
import Calendar.Calendar;
import Database.AvailabilityDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;
import Menu.Menu;

public class MenuTest {
	Company comp;
	CompanyDatabase compDb;
	CustomerDatabase custDb;
	AvailabilityDatabase availDb;
	Menu m1;
	
	@Before
	public void setUp() throws Exception {
		compDb = new CompanyDatabase();
		custDb = new CustomerDatabase();
		availDb = new AvailabilityDatabase();
		Calendar cal = new Calendar(LocalDate.of(2017, 04, 01));
		comp = new Company();
		comp.setCalendar(cal);
		m1 = new Menu(comp,custDb,compDb,availDb);
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
	public void testValidMonth() {
		String month = "04";
		Boolean expected = true;
		Boolean actual = m1.validMonth(month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidMonth2() {
		String month = "05";
		Boolean expected = true;
		Boolean actual = m1.validMonth(month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidMonthFail() {
		String month = "03";
		Boolean expected = false;
		Boolean actual = m1.validMonth(month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidMonthFail2() {
		String month = "06";
		Boolean expected = false;
		Boolean actual = m1.validMonth(month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidMonthFail3() {
		String month = "13";
		Boolean expected = false;
		Boolean actual = m1.validMonth(month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidMonthFail4() {
		String month = "0";
		Boolean expected = false;
		Boolean actual = m1.validMonth(month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDay() {
		String day = "10";
		int month = 4;
		Boolean expected = true;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDay2() {
		String day = "02";
		int month = 4;
		Boolean expected = true;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDay3() {
		String day = "30";
		int month = 4;
		Boolean expected = true;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDayFail() {
		String day = "1";
		int month = 4;
		Boolean expected = false;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDayFail2() {
		String day = "31";
		int month = 4;
		Boolean expected = false;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDayFail3() {
		String day = "0";
		int month = 4;
		Boolean expected = false;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDayFail4() {
		String day = "32";
		int month = 4;
		Boolean expected = false;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidDayFail5() {
		String day = "a";
		int month = 4;
		Boolean expected = false;
		Boolean actual = m1.validDay(day, month);
		
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidUname() {
		String uName = "j_snow";
		boolean check = m1.validUname(uName);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidFName() {
		String name = "john";
		boolean check = m1.validName(name);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidGender() {
		String gender = "male";
		boolean check = m1.validGender(gender);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile() {
		String mobile = "0412345678";
		boolean check = m1.validMobile(mobile);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidStreetNumber() {
		String number = "2";
		boolean check = m1.validStreetNumber(number);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidStreetName() {
		String name = "collin street";
		boolean check = m1.validStreetName(name);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidSuburb() {
		String suburb = "melbourne";
		boolean check = m1.validSuburb(suburb);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidZip() {
		String zip = "3000";
		boolean check = m1.validZip(zip);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidState() {
		String state = "VIC";
		boolean check = m1.validState(state);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidOption() {
		int option = 3;
		boolean check = m1.validOption(option);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidLogin() {
		String username = "jpoop";
		boolean check = m1.validLogin(username);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidTime()
	{
		Boolean expected = true;
		Boolean actual = false;
		String startTime = "08:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime2()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "07:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime3()
	{
		Boolean expected = true;
		Boolean actual = false;
		String startTime = "09:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime4()
	{
		Boolean expected = true;
		Boolean actual = false;
		String startTime = "16:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime5()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "17:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime6()
	{
		Boolean expected = true;
		Boolean actual = false;
		String startTime = "15:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime7()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "133:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	@Test
	public void testValidTime8()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "0:0";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	@Test
	public void testValidTime9()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "0:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	@Test
	public void testValidTime10()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "01:0";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime11()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:05";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime12()
	{
		Boolean expected = true;
		Boolean actual = false;
		String startTime = "08:15";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime13()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:10";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime14()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:14";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime15()
	{
		Boolean expected = true;
		Boolean actual = false;
		String startTime = "08:30";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime16()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:35";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime17()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:29";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime18()
	{
		Boolean expected = true;
		Boolean actual = false;
		String startTime = "08:45";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime19()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:40";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime20()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:46";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime21()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "28:00";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime22()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "08:50";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testValidTime23()
	{
		Boolean expected = false;
		Boolean actual = true;
		String startTime = "asdfg";
		actual = m1.validTime(startTime);
		assertEquals(expected,actual);
	}
}
