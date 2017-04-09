package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Business.Customer;

public class CustomerTest {
	Customer customer;
	String expected_username;
	String expected_fname;
	String expected_lname;
	String expected_gender;
	
	@Before
	public void setUp() throws Exception {
		expected_username = "0";
		expected_fname = "fname";
		expected_lname = "lname";
		expected_gender = "gender";
		customer = new Customer(expected_username, expected_fname, expected_lname, expected_gender);
	}
	
	@Test
	public void testGetUsername() {
		String actual_username = customer.getUsername();
		assertEquals(expected_username, actual_username);
	}
	
	@Test
	public void testGetFirstName() {
		String actual_fname = customer.getFirstName();
		assertEquals(expected_fname, actual_fname);
	}
	
	@Test
	public void testGetLastName() {
		String actual_lname = customer.getLastName();
		assertEquals(expected_lname, actual_lname);
	}
	
	@Test
	public void testGetGender() {
		String actual_gender = customer.getGender();
		assertEquals(expected_gender, actual_gender);
	}

}
