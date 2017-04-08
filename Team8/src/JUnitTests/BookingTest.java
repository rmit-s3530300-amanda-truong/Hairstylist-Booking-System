package JUnitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Calendar.Calendar;

import AppoinmentProgram.Booking;

public class BookingTest {
	Booking book;

	@Before
	public void setUp() throws Exception {
		book = new Booking(); 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStatus() {
		Calendar.Status expected_status = Calendar.Status.unavailable;
		Calendar.Status actual_status = book.getStatus();
		
		assertEquals(expected_status, actual_status);
	}
	
	@Test
	public void testSetStatus() {
		Calendar.Status expected_status = Calendar.Status.pending;
		book.setStatus(Calendar.Status.pending);
		
		Calendar.Status actual_status = book.getStatus();
		
		assertEquals(expected_status, actual_status);
	}
	
	
	
	@Test
	public void testAcceptBooking() {
		book.setStatus(Calendar.Status.pending);
		
		assertTrue(book.acceptBooking());
	}
	
	@Test
	public void testAcceptBookingFail() {
		book.setStatus(Calendar.Status.free);
		
		assertFalse(book.acceptBooking());
	}
	
	@Test
	public void testDeclineBooking() {
		book.setStatus(Calendar.Status.pending);
		
		assertTrue(book.declineBooking());
	}
	
	@Test
	public void testDeclineBookingFail() {
		book.setStatus(Calendar.Status.booked);
		
		assertFalse(book.declineBooking());
	}
	
	

}
