package Calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

import AppoinmentProgram.Booking;

public class Calendar {
	public enum Status {
		pending,
		booked,
		free
	}
	private HashMap<LocalDate,HashMap<LocalTime,Status>> information;
	private Booking[] bookingPendingList;
	private LocalDate currentDate;
	private HashMap<String, Booking> bookingList;
	
	public Calendar(LocalDate date, HashMap<LocalDate,HashMap<LocalTime,Status>> info) {
		information = info;
		bookingList = new HashMap<String, Booking>();
		currentDate = date;
	}
	
	// TODO: Needs Testing
	public void addAvailableTime(String employeeID, String date, String time) {
		
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
	
	public HashMap<LocalDate, HashMap<LocalTime, Status>> getCalendarInfo() {
		return information;
	}
	
	// TODO: Check if booking is free before add pending status
	// TODO: Needs Testing
	public void requestBooking(LocalDate date, LocalTime time) {
		information.get(date).put(time, Status.pending);
	}
	
	// TODO: Have to do error checking in case there are no last 7 days in Calendar
	// TODO: Needs Testing
	public HashMap<LocalDate,HashMap<LocalTime,Status>> getHistory() {
		LocalDate oldDate = currentDate.minusDays(7);
		HashMap<LocalDate,HashMap<LocalTime,Status>> historyInfo = new HashMap<LocalDate, HashMap<LocalTime, Status>>();
		for(int i =0; i < 7; i++){
			HashMap<LocalTime, Status> timeInfo = information.get(oldDate);
			historyInfo.put(oldDate, timeInfo);
			oldDate = oldDate.plusDays(1);
		}
		return historyInfo;
	}
	
	// TODO: Needs Testing
	public HashMap<LocalDate, HashMap<LocalTime, Status>> getNextWeek() {
		LocalDate newDate = currentDate.plusDays(1);
		HashMap<LocalDate,HashMap<LocalTime,Status>> futureInfo = new HashMap<LocalDate, HashMap<LocalTime, Status>>();
		for(int i =0; i < 7; i++){
			HashMap<LocalTime, Status> timeInfo = information.get(newDate);
			futureInfo.put(newDate, timeInfo);
		}
		return futureInfo;
	}
	
    // TODO: Needs Testing
	public Booking[] getBookingPendingList() {
		return bookingPendingList;
	}
	
	// TODO: returns a concatenated String which displays the calendar on console
	public String displayCalendar() {
		return null;
	}
	
}
