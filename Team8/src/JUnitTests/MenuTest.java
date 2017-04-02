package JUnitTests;

import static org.junit.Assert.*;
import org.junit.Test;

import Menu.Menu;

public class MenuTest {
	
	Menu m1 = new Menu();
	
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
	public void testValidService() {
		String service = "femaleCut";
		boolean check = m1.validService(service);
		assertEquals(true,check);
	}
}
