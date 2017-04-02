package Calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

import AppoinmentProgram.Booking;
import Calendar.Calendar;

public class Calendar {
	public enum Status {
		pending,
		booked,
		free
	}
	private LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Status>> information;
	private Booking[] bookingPendingList;
	private LocalDate currentDate;
	private HashMap<String, Booking> bookingList;
	
	public Calendar(LocalDate date) {
		information = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>>();
		currentDate = date;
		bookingList = new HashMap<String, Booking>();
		
		// Constructing calendar with a previous week of free time slots
		LinkedHashMap<LocalTime,Calendar.Status> nested_info = new LinkedHashMap<LocalTime, Calendar.Status>();
		LocalDate lastWeekDate = date.minusDays(7);
		for(int x = 0;x<7;x++){
			for(int i = 8; i<17 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					nested_info.put(localtime, Calendar.Status.free);
					localtime = localtime.plusMinutes(15);
				}
				information.put(lastWeekDate, nested_info);	
			}
			lastWeekDate = lastWeekDate.plusDays(1);
		}
		// Constructing calendar with 2 weeks of free time slots
		for(int x = 0;x<14;x++){
			for(int i = 8; i<17 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					nested_info.put(localtime, Calendar.Status.free);
					localtime = localtime.plusMinutes(15);
				}
				information.put(date, nested_info);	
			}
			date = date.plusDays(1);
		}
	}
	
	// TODO: Needs Testing
	public Boolean isBooked(LocalDate date, LocalTime time) {
		Status status = information.get(date).get(time);
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
	
	public void setCalendarInfo(LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>> info) {
		information = info;
	}
	
	public LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>> getCalendarInfo() {
		return information;
	}
	
	// TODO: Check if booking is free before add pending status
	// TODO: Needs Testing
	public void requestBooking(LocalDate date, LocalTime time) {
		information.get(date).put(time, Status.pending);
	}
	
	public LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Status>> getHistory() {
		LocalDate oldDate = currentDate.minusDays(7);
		LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Status>> historyInfo = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>>();
		for(int i =0; i < 7; i++){
			LinkedHashMap<LocalTime, Status> timeInfo = information.get(oldDate);
			historyInfo.put(oldDate, timeInfo);
			oldDate = oldDate.plusDays(1);
		}
		return historyInfo;
	}
	
	public LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>> getNextWeek() {
		LocalDate newDate = currentDate.plusDays(1);
		LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Status>> futureInfo = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>>();
		for(int i =0; i < 7; i++){
			LinkedHashMap<LocalTime, Status> timeInfo = information.get(newDate);
			futureInfo.put(newDate, timeInfo);
		}
		return futureInfo;
	}
	
    // TODO: Needs Testing
	public Booking[] getBookingPendingList() {
		return bookingPendingList;
	}
	
	// Returns a concatenated String which displays the calendar on console
	public String displayCalendar(LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>> info, LocalDate startDate) {
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
				output = output + String.format("%-13s|", info.get(date2).get(time).toString());
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
	
	public void printHashMap(LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Status>> information2) {
		for(Entry<LocalDate, LinkedHashMap<LocalTime, Status>> entry : information2.entrySet()) {
			LinkedHashMap<LocalTime, Status> entry2 = entry.getValue();
			System.out.println("OuterKey: "+entry.getKey());
			for(Entry<LocalTime, Status> entry3 : entry2.entrySet()) {
				System.out.println("AKey: " + entry3.getKey() + " Value: " + entry3.getValue());
			}			
		}
	}
}
