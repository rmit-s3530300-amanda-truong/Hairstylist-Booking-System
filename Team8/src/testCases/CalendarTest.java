package testCases;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

import business.Customer;
import business.Employee;
import business.Employee.Service;
import calendar.Booking;
import calendar.Calendar;
import calendar.Calendar.Status;

public class CalendarTest {
	Calendar c1;
	Employee emp;
	Customer cust;
	String cust_id;

	@Before
	public void setUp() throws Exception {
		ArrayList<Service> services = new ArrayList<Service>();
		services.add(Service.femaleCut);
		emp = new Employee("01", "John", "Snow", services);
		cust = new Customer("000", "Margaery", "Tyrell");
		cust_id = cust.getUsername();
		
		LocalDate localdate = LocalDate.of(2017, 01, 10);
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		
		for(int x = 0;x<7;x++){
			LinkedHashMap<LocalTime,Booking> nested_info = new LinkedHashMap<LocalTime, Booking>();
			for(int i = 8; i<17 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					String id=localdate.toString()+"/"+localtime.toString();
					nested_info.put(localtime, new Booking(Calendar.Status.free, id));
					localtime = localtime.plusMinutes(15);
				}
				info.put(localdate, nested_info);	
			}
			localdate = localdate.plusDays(1);
		}
		
		// localdate = 2017.01.17
		c1 = new Calendar(localdate);
		c1.setCalendarInfo(info);
	}

	/*@Test
	public void testGetBookingPendingList() {
		String expected_output = "Booking ID: 2017-01-15/10:00, Status: pending, Date: 2017-01-15, Start Time: 10:00, End Time: 10:15, Customer: 000, Service: femaleCut, Employee: 01 \n";
		String actual_output;
		
		c1.requestBooking(LocalDate.of(2017, 01, 15), LocalTime.of(10, 00), LocalTime.of(10, 15), emp, Service.femaleCut, "000");
		
		actual_output = c1.getBookingPendingString();
		
		assertEquals(expected_output, actual_output);
	}*/
	
	@Test
	public void testGetBookingSummary() {
		String expected_output = "Booking ID: 2017-01-15/10:00, Status: booked, Date: 2017-01-15, Start Time: 10:00, End Time: 10:30, Customer: 000, Service: femaleCut, Employee: 01 \n";
		String actual_output;
		
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = c1.getCalendarInfo();
		LinkedHashMap<LocalTime,Booking> nested_info;
		LocalDate date = LocalDate.of(2017, 01, 15);
		LocalTime start_time = LocalTime.of(10, 00);
		LocalTime end_time = LocalTime.of(10, 30);
		nested_info = info.get(date);
		Booking book = nested_info.get(start_time);
		
		book.addDetails(date, start_time, end_time, Service.femaleCut,emp, "000");
		nested_info.put(LocalTime.of(5, 00), book);
		info.put(date, nested_info);
		c1.setCalendarInfo(info);
		
		actual_output = c1.getBookingSummary();
		
		assertEquals(expected_output, actual_output);
	}
	
	@Test
	// Requesting booking when times are 'free', expect TRUE
	public void requestBookingTest() {
		Boolean expected_boolean = true;
		Status expected_status = Status.booked;
		Boolean actual_boolean;
		Status actual_status;
		
		
		actual_boolean = c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
		
	}
	
	@Test
	// Requesting booking when time is "pending", expect TRUE
	public void requestBookingTest3() {
		Boolean expected_boolean = true;
		Status expected_status = Status.booked;
		Boolean actual_boolean;
		Status actual_status;
		
		actual_boolean = c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, "002");
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
		
	}
	
	/*@Test
	// Requesting booking when the same customer is booking, expect TRUE
	public void requestBookingTest2() {
		Boolean expected_boolean = false;
		Status expected_status = Status.pending;
		Boolean actual_boolean;
		Status actual_status;
		
		c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		
		actual_boolean = c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}*/
	
	/*@Test
	// Requesting booking when times are 'booked', expect FALSE
	public void requestBookingTestFail() {
		Boolean expected_boolean = false;
		Status expected_status = Status.booked;
		Boolean actual_boolean;
		Status actual_status;
		
		c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		c1.acceptBooking(book.getID());
		
		actual_boolean = c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, "002");
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
		
	}*/
	
	@Test
	// Requesting booking when times are 'unavailable', expect FALSE
	public void requestBookingTestFail2() {
		Boolean expected_boolean = false;
		Status expected_status = Status.unavailable;
		Boolean actual_boolean;
		Status actual_status;
		
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		book.setStatus(Status.unavailable);
		
		actual_boolean = c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
		
	}
	
	@Test
	// Accepting booking when it is in pending status, expect TRUE
	public void acceptBookingTest() {
		Boolean expected_boolean = true;
		Status expected_status = Status.booked;
		Boolean actual_boolean;
		Status actual_status;
		
		c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		
		actual_boolean = c1.acceptBooking(book.getID());
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}
	
	@Test
	// Accepting booking when the booking is not in pending status, expect FALSE
	public void acceptBookingTestFail() {
		Boolean expected_boolean = false;
		Status expected_status = Status.free;
		Boolean actual_boolean;
		Status actual_status;
		
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		
		actual_boolean = c1.acceptBooking(book.getID());
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}
	
	@Test
	// Accepting booking when the booking is already in booked status, expect FALSE
	public void acceptBookingTestFail2() {
		Boolean expected_boolean = false;
		Status expected_status = Status.booked;
		Boolean actual_boolean;
		Status actual_status;
		
		c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		c1.acceptBooking(book.getID());
		
		actual_boolean = c1.acceptBooking(book.getID());
		
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}
	
	@Test
	// Accepting booking when the booking is in unavailable status, expect FALSE
	public void acceptBookingTestFail3() {
		Boolean expected_boolean = false;
		Status expected_status = Status.unavailable;
		Boolean actual_boolean;
		Status actual_status;
		
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		book.setStatus(Status.unavailable);
		
		actual_boolean = c1.acceptBooking(book.getID());
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}
	
	@Test
	// Accepting booking when the employee is unavailable at the times specified, expect FALSE
	public void acceptBookingTestFail4() {
		Boolean expected_boolean = false;
		Status expected_status = Status.booked;
		Boolean actual_boolean;
		Status actual_status;
		
		emp.addBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30));
		c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		
		actual_boolean = c1.acceptBooking(book.getID());
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}
	
	@Test
	// Decline booking when it is in free state, expect FALSE
	public void declineBookingTestFail() {
		Boolean expected_boolean = false;
		Status expected_status = Status.free;
		Boolean actual_boolean;
		Status actual_status;
		
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		
		actual_boolean = c1.declineBooking(book.getID());
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}
	
	/*@Test
	// Decline booking when it is in booked state, expect FALSE
	public void declineBookingTestFail2() {
		Boolean expected_boolean = false;
		Status expected_status = Status.booked;
		Boolean actual_boolean;
		Status actual_status;
		
		c1.requestBooking(LocalDate.of(2017, 01, 10), LocalTime.of(8, 00), LocalTime.of(8, 30), emp, Service.femaleCut, cust_id);
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		c1.acceptBooking(book.getID());
		
		actual_boolean = c1.declineBooking(book.getID());
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}*/
	
	@Test
	// Decline booking when it is in unavailable state, expect FALSE
	public void declineBookingTestFail3() {
		Boolean expected_boolean = false;
		Status expected_status = Status.unavailable;
		Boolean actual_boolean;
		Status actual_status;
		
		Booking book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		book.setStatus(Status.unavailable);
		
		actual_boolean = c1.declineBooking(book.getID());
		book = c1.getCalendarInfo().get(LocalDate.of(2017, 01, 10)).get(LocalTime.of(8, 00));
		actual_status = book.getStatus();
		
		assertEquals(expected_boolean, actual_boolean);
		assertEquals(expected_status, actual_status);
	}
	
	@Test
	public void testDisplayFutureInfo() {
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = c1.getCalendarInfo();
		LocalDate date = c1.getDate().minusDays(14);
		String actual_cal = c1.displayCalendar(info, date.plusDays(7));
		assertEquals("--------------------------------------------------------------------------------------------------------------------\n"
				+"                                                JANUARY\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|             |2017-01-10   |2017-01-11   |2017-01-12   |2017-01-13   |2017-01-14   |2017-01-15   |2017-01-16   \n"   
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:00 - 08:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:15 - 08:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:30 - 08:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:45 - 09:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:00 - 09:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:15 - 09:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:30 - 09:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:45 - 10:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:00 - 10:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:15 - 10:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:30 - 10:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:45 - 11:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:00 - 11:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:15 - 11:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:30 - 11:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:45 - 12:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:00 - 12:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:15 - 12:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:30 - 12:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:45 - 13:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:00 - 13:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:15 - 13:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:30 - 13:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:45 - 14:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:00 - 14:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:15 - 14:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:30 - 14:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:45 - 15:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:00 - 15:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:15 - 15:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:30 - 15:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:45 - 16:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n", actual_cal);
	}
	
	@Test
	public void testDisplayHistory() {
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = c1.getCalendarInfo();
		LocalDate date = c1.getDate();
		String actual_cal = c1.displayCalendar(info, date.minusDays(7));
		assertEquals("--------------------------------------------------------------------------------------------------------------------\n"
				+"                                                JANUARY\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|             |2017-01-10   |2017-01-11   |2017-01-12   |2017-01-13   |2017-01-14   |2017-01-15   |2017-01-16   \n"   
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:00 - 08:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:15 - 08:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:30 - 08:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:45 - 09:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:00 - 09:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:15 - 09:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:30 - 09:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:45 - 10:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:00 - 10:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:15 - 10:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:30 - 10:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:45 - 11:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:00 - 11:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:15 - 11:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:30 - 11:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:45 - 12:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:00 - 12:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:15 - 12:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:30 - 12:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:45 - 13:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:00 - 13:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:15 - 13:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:30 - 13:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:45 - 14:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:00 - 14:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:15 - 14:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:30 - 14:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:45 - 15:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:00 - 15:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:15 - 15:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:30 - 15:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:45 - 16:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n", actual_cal);
	}
	
	@Test
	public void testDisplayCalendar() {
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = c1.getCalendarInfo();
		c1.setCurrentDate(LocalDate.of(2017, 01, 10));
		String actual_cal = c1.displayCalendar(info, c1.getDate());
		assertEquals("--------------------------------------------------------------------------------------------------------------------\n"
				+"                                                JANUARY\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|             |2017-01-10   |2017-01-11   |2017-01-12   |2017-01-13   |2017-01-14   |2017-01-15   |2017-01-16   \n"   
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:00 - 08:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:15 - 08:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:30 - 08:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:45 - 09:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:00 - 09:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:15 - 09:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:30 - 09:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:45 - 10:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:00 - 10:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:15 - 10:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:30 - 10:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:45 - 11:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:00 - 11:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:15 - 11:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:30 - 11:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:45 - 12:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:00 - 12:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:15 - 12:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:30 - 12:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:45 - 13:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:00 - 13:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:15 - 13:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:30 - 13:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:45 - 14:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:00 - 14:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:15 - 14:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:30 - 14:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:45 - 15:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:00 - 15:15| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:15 - 15:30| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:30 - 15:45| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:45 - 16:00| free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n", actual_cal);
	}
}
