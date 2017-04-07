package Calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

import AppoinmentProgram.Booking;
import AppoinmentProgram.Employee;
import Calendar.Calendar;

public class Calendar {
	public enum Status {
		pending,
		booked,
		free,
		unavailable
	}
	// private Booking[] bookingPendingList;
	private LocalDate currentDate;
	private LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> bookingList;
	
	public Calendar(LocalDate date) {
		currentDate = date;
		bookingList = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		
		// Constructing calendar with a previous week of unavailable time slots
		LinkedHashMap<LocalTime, Booking> nested_info = new LinkedHashMap<LocalTime, Booking>();
		LocalDate lastWeekDate = date.minusDays(7);
		for(int x = 0;x<7;x++){
			for(int i = 8; i<17 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					nested_info.put(localtime, new Booking());
					localtime = localtime.plusMinutes(15);
				}
				bookingList.put(lastWeekDate, nested_info);
			}
			lastWeekDate = lastWeekDate.plusDays(1);
		}
		// Constructing calendar with 2 weeks of unavailable time slots
		for(int x = 0;x<14;x++){
			for(int i = 8; i<17 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					nested_info.put(localtime, new Booking());
					localtime = localtime.plusMinutes(15);
				}
				bookingList.put(date, nested_info);
			}
			date = date.plusDays(1);
		}
	}
	
	public HashMap<String, Booking> getBookingSummary() {
		HashMap<String, Booking> list = new HashMap<String, Booking>();
		for(Entry<LocalDate, LinkedHashMap<LocalTime, Booking>> x : bookingList.entrySet()) {
			for(Entry<LocalTime, Booking> y : x.getValue().entrySet()) {
				if(y.getValue().getStatus() == Status.pending || y.getValue().getStatus() == Status.booked) {
					list.put(y.getValue().getCustomerID(), y.getValue());
				}
			}
		}
		return list;
	}
	
	// Ensure employee has 14 days worth of availability before calling this method(maybe?)
	// Need to test
	public void updateCalendar(HashMap<String, Employee> employeeList) {
		for(Entry<String, Employee> value : employeeList.entrySet()) {
			Employee emp = value.getValue();
			HashMap<LocalDate, ArrayList<LocalTime>> availability = emp.getAvailability();
			LocalDate date = currentDate;
			for(int i = 0; i <14; i++) {
				if(availability.containsKey(date)) {
					ArrayList<LocalTime> available_times = availability.get(date);
					LinkedHashMap<LocalTime, Booking> map_time = null;
					for(int x=0; x<available_times.size();x++) {
						map_time = new LinkedHashMap<LocalTime, Booking>();
						map_time.put(available_times.get(x), new Booking(Status.free));
					}
					bookingList.put(date, map_time);
				}
				date = date.plusDays(1);
			}
		}
	}
	
	// TODO: Needs Testing
	public Boolean isBooked(LocalDate date, LocalTime time) {
		Status status = bookingList.get(date).get(time).getStatus();
		if(status == Status.booked) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public LocalDate getDate() {
		return currentDate;
	}
	
	public void setCurrentDate(LocalDate date) {
		currentDate = date;
	}
	
	public void setCalendarInfo(LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info) {
		bookingList = info;
	}
	
	public LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> getCalendarInfo() {
		return bookingList;
	}
	
	// TODO: Needs Testing
	// returns false when cannot book
	public Boolean requestBooking(LocalDate date, LocalTime time) {
		Booking book = bookingList.get(date).get(time);
		if(book.getStatus() == Status.booked){
			return false;
		} else {
			book.setStatus(Status.pending);
			bookingList.get(date).put(time, book);
			return true;
		}
		
	}
	
	public LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Booking>> getHistory() {
		LocalDate oldDate = currentDate.minusDays(7);
		LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Booking>> historyInfo = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		for(int i =0; i < 7; i++){
			LinkedHashMap<LocalTime, Booking> timeInfo = bookingList.get(oldDate);
			historyInfo.put(oldDate, timeInfo);
			oldDate = oldDate.plusDays(1);
		}
		return historyInfo;
	}
	
	public LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> getNextWeek() {
		LocalDate newDate = currentDate.plusDays(1);
		LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Booking>> futureInfo = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		for(int i =0; i < 7; i++){
			LinkedHashMap<LocalTime, Booking> timeInfo = bookingList.get(newDate);
			futureInfo.put(newDate, timeInfo);
			newDate = newDate.plusDays(1);
		}
		return futureInfo;
	}
	
    // TODO: Needs Testing
	public HashMap<String, Booking> getBookingPendingList() {
		HashMap<String, Booking> list = new HashMap<String, Booking>();
		for(Entry<LocalDate, LinkedHashMap<LocalTime, Booking>> x : bookingList.entrySet()) {
			for(Entry<LocalTime, Booking> y : x.getValue().entrySet()) {
				if(y.getValue().getStatus() == Status.pending) {
					list.put(y.getValue().getCustomerID(), y.getValue());
				}
			}
		}
		return list;
	}
	
	// Returns a concatenated String which displays the calendar on console
	public String displayCalendar(LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info, LocalDate startDate) {
		String output;
		LocalDate month_ = info.entrySet().iterator().next().getKey();
		String month = month_.getMonth().toString();
		output = String.format("%s\n", printBorder("-", 12*9+8));
		output = output + String.format("%55s\n",month);
		output = output + String.format("%s\n", printBorder("-", 12*9+8));
		output = output + String.format("|%-13s", "");
		LocalDate date = startDate; 
		for(int i = 0; i < 7; i++){
			output = output + String.format("|%-13s", date.toString());
			date = date.plusDays(1);
		}
		output = output + String.format("\n%s\n", printBorder("-", 12*9+8));
		LocalTime time = LocalTime.of(8, 00);
		LocalDate date2=startDate;
		while(!time.toString().equals(LocalTime.of(16, 15).toString())) {
			date2 = startDate;
			output = output + String.format("|%-13s| ", time.toString());
			for(int i =0; i < 7; i ++) {
				output = output + String.format("%-13s|", info.get(date2).get(time).getStatus().toString());
				date2.plusDays(1);
			}
			output = output + String.format("\n%s\n", printBorder("-", 12*9+8));
			time = time.plusMinutes(15);
		}
		System.out.println(output);
		return output;
	}
	
	public String printBorder(String a, int times) {
		String output="";
		for(int i =0; i < times ; i++) {
			output = output+a;
		}
		return output;
	}
	
	public void printHashMap(LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>> information) {
		for(Entry<LocalDate, LinkedHashMap<LocalTime, Status>> entry : information.entrySet()) {
			LinkedHashMap<LocalTime, Status> entry2 = entry.getValue();
			System.out.println("OuterKey: "+entry.getKey());
			for(Entry<LocalTime, Status> entry3 : entry2.entrySet()) {
				System.out.println("AKey: " + entry3.getKey() + " Value: " + entry3.getValue());
			}			
		}
	}
}
