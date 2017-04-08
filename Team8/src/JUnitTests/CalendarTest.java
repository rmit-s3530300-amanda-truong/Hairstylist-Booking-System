package JUnitTests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import AppoinmentProgram.Booking;
import AppoinmentProgram.Employee.Service;
import Calendar.Calendar;
import Calendar.Calendar.Status;

public class CalendarTest {
	Calendar c1;

	@Before
	public void setUp() throws Exception {
		LocalDate localdate = LocalDate.of(2017, 01, 10);
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		LinkedHashMap<LocalTime,Booking> nested_info = new LinkedHashMap<LocalTime, Booking>();
		
		for(int x = 0;x<7;x++){
			for(int i = 8; i<17 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					nested_info.put(localtime, new Booking(Calendar.Status.free));
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
	
	
	
	@Test
	public void testAcceptBooking() {
	}
	
	@Test
	public void testAcceptBookingFail() {
	}
	
	@Test
	public void testDeclineBooking() {
	}
	
	@Test
	public void testDeclineBookingFail() {
	}
	
	@Test
	public void testGetBookingPendingList() {
		String expected_output = "ID: 0, Status: pending, Date: 2017-01-15, Start Time: 10:00, End Time: 11:30, Customer: 000, Service|Employee: femaleCut|Alan, femaleDye|Candy,  \n";
		String actual_output;
		
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = c1.getCalendarInfo();
		LinkedHashMap<LocalTime,Booking> nested_info;
		LocalDate date = LocalDate.of(2017, 01, 15);
		LocalTime time = LocalTime.of(10, 00);
		nested_info = info.get(date);
		Booking book = nested_info.get(time);
		HashMap<Service,String> services = new HashMap<Service,String>();
		services.put(Service.femaleCut, "Alan");
		services.put(Service.femaleDye, "Candy");
		book.addDetails("0", services, date, time, "000");
		nested_info.put(LocalTime.of(5, 00), book);
		info.put(date, nested_info);
		c1.setCalendarInfo(info);
		
		actual_output = c1.getBookingPendingString();
		
		assertEquals(expected_output, actual_output);
	}
	
	@Test
	public void testGetBookingSummary() {
		String expected_output = "ID: 0, Status: pending, Date: 2017-01-15, Start Time: 10:00, End Time: 10:30, Customer: 000, Service|Employee: femaleCut|Alan,  \n";
		String actual_output;
		
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = c1.getCalendarInfo();
		LinkedHashMap<LocalTime,Booking> nested_info;
		LocalDate date = LocalDate.of(2017, 01, 15);
		LocalTime time = LocalTime.of(10, 00);
		nested_info = info.get(date);
		Booking book = nested_info.get(time);
		HashMap<Service,String> services = new HashMap<Service,String>();
		services.put(Service.femaleCut, "Alan");
		book.addDetails("0", services, date, time, "000");
		nested_info.put(LocalTime.of(5, 00), book);
		info.put(date, nested_info);
		c1.setCalendarInfo(info);
		
		actual_output = c1.getBookingSummary();
		
		assertEquals(expected_output, actual_output);
	}

	@Test
	public void testGetHistoryFree() {
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> actual_history = c1.getHistory();
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> expected_history = c1.getCalendarInfo();
		
		assertEquals(actual_history, expected_history);
	}
	
	@Test
	public void testGetHistoryNull() {
		LocalDate date = LocalDate.of(2017, 01, 30);
		c1.setCurrentDate(date);
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> actual_history = c1.getHistory();
		
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> expected_history = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		LocalDate modified_date = date.minusDays(7);
		for(int x = 0;x<7;x++){
			expected_history.put(modified_date, null);
			modified_date = modified_date.plusDays(1);
		}
		
		assertEquals(expected_history, actual_history);
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
				+"|08:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|16:00        | free         |free         |free         |free         |free         |free         |free         |\n"
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
				+"|08:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|16:00        | free         |free         |free         |free         |free         |free         |free         |\n"
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
				+"|08:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|08:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|09:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|10:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|11:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|12:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|13:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|14:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:15        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:30        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|15:45        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n"
				+"|16:00        | free         |free         |free         |free         |free         |free         |free         |\n"
				+"--------------------------------------------------------------------------------------------------------------------\n", actual_cal);
	}

	public void printHashMap(LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> information2) {
		for(Entry<LocalDate, LinkedHashMap<LocalTime, Booking>> entry : information2.entrySet()) {
			LinkedHashMap<LocalTime, Booking> entry2 = entry.getValue();
			System.out.println("OuterKey: "+entry.getKey());
			for(Entry<LocalTime, Booking> entry3 : entry2.entrySet()) {
				System.out.println("AKey: " + entry3.getKey() + " Value: " + entry3.getValue().getStatus());
			}			
		}
	}
}
