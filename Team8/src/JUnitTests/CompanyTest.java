package JUnitTests;

import static org.junit.Assert.*;
import org.junit.Test;

import AppoinmentProgram.Company;

public class CompanyTest {
	
	Company co1 = new Company(null, null, null);
	
	@Test
	public void testValidFName1() {
		String name = "john";
		boolean check = co1.validName(name);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidFName2() {
		String name = "";
		boolean check = co1.validName(name);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidFName3() {
		String name = "john watson";
		boolean check = co1.validName(name);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidGender1() {
		String gender = "male";
		boolean check = co1.validGender(gender);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidGender2() {
		String gender = "";
		boolean check = co1.validGender(gender);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidGender3() {
		String gender = "test";
		boolean check = co1.validGender(gender);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidMobile1() {
		String mobile = "0412345678";
		boolean check = co1.validMobile(mobile);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidMobile2() {
		String mobile = "";
		boolean check = co1.validMobile(mobile);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidMobile3() {
		String mobile = "041234567";
		boolean check = co1.validMobile(mobile);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidStreetNumber1() {
		String number = "";
		boolean check = co1.validStreetNumber(number);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidStreetNumber2() {
		String number = "2";
		boolean check = co1.validStreetNumber(number);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidStreetNumber3() {
		String number = "abc";
		boolean check = co1.validStreetNumber(number);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidStreetName1() {
		String name = "";
		boolean check = co1.validStreetName(name);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidStreetName2() {
		String name = "collin street";
		boolean check = co1.validStreetName(name);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidStreetName3() {
		String name = "123";
		boolean check = co1.validStreetName(name);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidSuburb1() {
		String suburb = "melbourne";
		boolean check = co1.validSuburb(suburb);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidSuburb2() {
		String suburb = "";
		boolean check = co1.validSuburb(suburb);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidSuburb3() {
		String suburb = "123";
		boolean check = co1.validSuburb(suburb);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidZip1() {
		String zip = "3000";
		boolean check = co1.validZip(zip);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidZip2() {
		String zip = "300";
		boolean check = co1.validZip(zip);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidZip3() {
		String zip = "";
		boolean check = co1.validZip(zip);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidZip4() {
		String zip = "abc";
		boolean check = co1.validZip(zip);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidState1() {
		String state = "VIC";
		boolean check = co1.validState(state);
		assertEquals(true,check);
	}
	
	@Test
	public void testValidState2() {
		String state = "";
		boolean check = co1.validState(state);
		assertEquals(false,check);
	}
	
	@Test
	public void testValidState3() {
		String state = "1234";
		boolean check = co1.validState(state);
		assertEquals(false,check);
	}
	
}
