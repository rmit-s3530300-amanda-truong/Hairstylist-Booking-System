//package team8.bms;
//
//import static org.junit.Assert.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.ArrayList;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import business.Employee;
//import calendar.Booking;
//import calendar.Calendar;
//
//public class BookingTest {
//	Booking book;
//	String expected_ID;
//	String expected_service;
//	LocalDate expected_date;
//	LocalTime expected_start_time;
//	LocalTime expected_end_time;
//	String expected_customerID;
//	Employee emp;
//
//	@Before
//	public void setUp() throws Exception {
//		book = new Booking("0"); 
//		
//		expected_ID = "0";
//		expected_service = "femaleCut";
//		expected_date = LocalDate.of(2017, 04, 01);
//		expected_start_time = LocalTime.of(12,00);
//		
//		ArrayList<String> s = new ArrayList<String>();
//		s.add("femaleCut");
//		//emp = new Employee("111", "herro", "yes", s);
//		
//		expected_customerID = "a";
//		
//		book.addDetails(expected_date, expected_start_time, expected_end_time, expected_service,  emp, "a");
//	}
//	
///*	@Test
//	public void testSetStatus() {
//		Calendar.Status expected_status = Calendar.Status.pending;
//		book.setStatus(Calendar.Status.pending);
//		
//		Calendar.Status actual_status = book.getStatus();
//		
//		assertEquals(expected_status, actual_status);
//	}*/
//	
//	// Tests getters and addDetails() methods
//	@Test
//	public void testAddDetails() {
//		String actual_ID = book.getID();
//		String actual_service = book.getServices();
//		LocalDate actual_date = book.getDate();
//		LocalTime actual_s_time = book.getStartTime();
//		LocalTime actual_e_time = book.getEndTime();
//		String actual_customerID = book.getCustomerID();
//		
//		assertEquals(expected_ID, actual_ID);
//		assertEquals(expected_service, actual_service);
//		assertEquals(expected_date, actual_date);
//		assertEquals(expected_start_time, actual_s_time);
//		assertEquals(expected_end_time, actual_e_time);
//		assertEquals(expected_customerID, actual_customerID);
//	}
//}
