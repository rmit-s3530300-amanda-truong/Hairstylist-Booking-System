package JUnitTests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Calendar.Booking;
import Calendar.Calendar;
import Business.Employee.Service;

public class BookingTest {
	Booking book;
	String expected_ID;
	HashMap<Service, String> expected_service;
	LocalDate expected_date;
	LocalTime expected_time;
	String expected_customerID;

	@Before
	public void setUp() throws Exception {
		book = new Booking(); 
		expected_ID = "0";
		expected_service = new HashMap<Service, String>();
		expected_service.put(Service.femaleCut, "000");
		expected_date = LocalDate.of(2017, 04, 01);
		expected_time = LocalTime.of(12,00);
		expected_customerID = "a";
		
		book.addDetails(expected_ID, expected_service, expected_date, expected_time, expected_customerID);
	}
	
	@Test
	public void testSetStatus() {
		Calendar.Status expected_status = Calendar.Status.pending;
		book.setStatus(Calendar.Status.pending);
		
		Calendar.Status actual_status = book.getStatus();
		
		assertEquals(expected_status, actual_status);
	}
	
	// Tests getters and addDetails() methods
	@Test
	public void testAddDetails() {
		String actual_ID = book.getID();
		HashMap<Service, String> actual_service = book.getServices();
		LocalDate actual_date = book.getDate();
		LocalTime actual_time = book.getTime();
		String actual_customerID = book.getCustomerID();
		
		assertEquals(expected_ID, actual_ID);
		assertEquals(expected_service, actual_service);
		assertEquals(expected_date, actual_date);
		assertEquals(expected_time, actual_time);
		assertEquals(expected_customerID, actual_customerID);
	}
}
