package JUnitTests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import Calendar.Calendar;
import Calendar.Calendar.Status;

public class CalendarTest {
	Calendar c1;

	@Before
	public void setUp() throws Exception {
		LocalDate localdate = LocalDate.of(2017, 01, 10);
		HashMap<LocalDate, HashMap<LocalTime, Calendar.Status>> info = new HashMap<LocalDate, HashMap<LocalTime, Calendar.Status>>();
		HashMap<LocalTime,Calendar.Status> nested_info = new HashMap<LocalTime, Calendar.Status>();
		
		for(int x = 0;x<7;x++){
			for(int i = 10; i<17 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				nested_info.put(localtime, Calendar.Status.free);
				info.put(localdate, nested_info);	
			}
			localdate = localdate.plusDays(1);
		}
		
		// localdate = 2017.01.17
		c1 = new Calendar(localdate, info);
	}

	/*@After
	public void tearDown() throws Exception {
	}*/

	@Test
	public void testGetHistoryFree() {
		HashMap<LocalDate, HashMap<LocalTime, Status>> actual_history = c1.getHistory();
		HashMap<LocalDate, HashMap<LocalTime, Status>> expected_history = c1.getCalendarInfo();
		
		assertEquals(actual_history, expected_history);
	}
	
	@Test
	public void testGetHistoryNull() {
		LocalDate date = LocalDate.of(2017, 01, 30);
		c1.setCurrentDate(date);
		HashMap<LocalDate, HashMap<LocalTime, Status>> actual_history = c1.getHistory();
		
		HashMap<LocalDate, HashMap<LocalTime, Calendar.Status>> expected_history = new HashMap<LocalDate, HashMap<LocalTime, Calendar.Status>>();
		LocalDate modified_date = date.minusDays(7);
		for(int x = 0;x<7;x++){
			expected_history.put(modified_date, null);
			modified_date = modified_date.plusDays(1);
		}
		
		assertEquals(expected_history, actual_history);
	}

	public void printHashMap(HashMap<String, HashMap<String,Calendar.Status>> map) {
		for(Entry<String, HashMap<String, Calendar.Status>> entry : map.entrySet()) {
			HashMap<String,Calendar.Status> entry2 = entry.getValue();
			System.out.println("OuterKey: "+entry.getKey());
			for(Entry<String, Calendar.Status> entry3 : entry2.entrySet()) {
				System.out.println("AKey: " + entry3.getKey() + " Value: " + entry3.getValue());
			}			
		}
	}
}
