package Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;

import Business.Employee;
import Business.Employee.Service;
import Calendar.Calendar;

public class Calendar {
	public enum Status {
		pending,
		booked,
		free,
		unavailable
	}
	
	private LocalDate currentDate;
	private LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> calendar;
	// bookingList, String is the ID of the booking
	private ArrayList<Booking> bookingList;
	
	public Calendar(LocalDate date) {
		bookingList = new ArrayList<Booking>();
		currentDate = date;
		calendar = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		
		// Constructing calendar with a previous week of unavailable time slots
		LocalDate lastWeekDate = date.minusDays(7);
		
		for(int x = 0;x<7;x++){
			LinkedHashMap<LocalTime, Booking> nested_info = new LinkedHashMap<LocalTime, Booking>();
			for(int i = 8; i<16 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					String id = lastWeekDate.toString()+"/"+localtime.toString();
					nested_info.put(localtime, new Booking(id));
					localtime = localtime.plusMinutes(15);
				}
				calendar.put(lastWeekDate, nested_info);
			}
			lastWeekDate = lastWeekDate.plusDays(1);
		}
		
		// Constructing calendar with 2 weeks of unavailable time slots
		for(int x = 0;x<14;x++){
			LinkedHashMap<LocalTime, Booking> nested_info = new LinkedHashMap<LocalTime, Booking>();
			for(int i = 8; i<16 ;i++) {
				LocalTime localtime = LocalTime.of(i, 00);
				for(int y = 0 ; y<4 ;y++){
					String id = date.toString()+"/"+localtime.toString();
					nested_info.put(localtime, new Booking(id));
					localtime = localtime.plusMinutes(15);
				}
				calendar.put(date, nested_info);
			}
			date = date.plusDays(1);
		}
		
		// Adding pending bookings to list
		for(Entry<LocalDate, LinkedHashMap<LocalTime, Booking>> x : calendar.entrySet()) {
			for(Entry<LocalTime, Booking> y : x.getValue().entrySet()) {
				Booking book = y.getValue();
				if(book.getStatus() == Status.pending || book.getStatus() == Status.booked) {
					bookingList.add(book);
				}
			}
		}
	}
	
	public String getBookingSummary() {
		LinkedHashMap<String, Booking> list = new LinkedHashMap<String, Booking>();
		String output="";
		for(Entry<LocalDate, LinkedHashMap<LocalTime, Booking>> x : calendar.entrySet()) {
			for(Entry<LocalTime, Booking> y : x.getValue().entrySet()) {
				if(y.getValue().getStatus() == Status.pending || y.getValue().getStatus() == Status.booked) {
					list.put(y.getValue().getCustomerID(), y.getValue());
				}
			}
		}
		for(Entry<String,Booking> entry : list.entrySet()) {
			Booking book = entry.getValue();
			LocalTime start_time = book.getStartTime();
			LocalTime end_time = book.getEndTime();
			Service service = book.getServices();
			String emp_id = book.getEmployee().getID();
			
			output = output + String.format("Booking ID: %s, Status: %s, Date: %s, Start Time: %s, End Time: %s, Customer: %s, Service: %s, Employee: %s \n",book.getID(), book.getStatus().toString(), book.getDate(), start_time, end_time, book.getCustomerID(), service, emp_id);
		}
		return output;
	}
	
	public void updateCalendar(HashMap<String, Employee> employeeList) {
		for(Entry<String, Employee> value : employeeList.entrySet()) {
			Employee emp = value.getValue();
			HashMap<DayOfWeek, ArrayList<LocalTime>> availability = emp.getAvailability();
			LocalDate date = currentDate;
			DayOfWeek day = currentDate.getDayOfWeek();
			for(int i = 0; i <14; i++) {
				if(availability.containsKey(day)) {
					ArrayList<LocalTime> available_times = availability.get(day);
					LinkedHashMap<LocalTime, Booking> map_time = new LinkedHashMap<LocalTime, Booking>();
					for(int x=0; x<available_times.size();x++) {
						String id = date.toString()+available_times.get(x).toString();
						map_time.put(available_times.get(x), new Booking(Status.free,id));
					}
					calendar.put(date, map_time);
				}
				date = date.plusDays(1);
				day = date.getDayOfWeek();
			}
		}
	}
	
	public Boolean isBooked(LocalDate date, LocalTime time) {
		Status status = calendar.get(date).get(time).getStatus();
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
		calendar = info;
	}
	
	public LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> getCalendarInfo() {
		return calendar;
	}
	
	// returns false when cannot book
	public Boolean requestBooking(LocalDate date, LocalTime start_time, LocalTime end_time, Employee emp, Service service, String customer_id) {
		Boolean isBooked = false;
		ArrayList<Booking> booking_list = new ArrayList<Booking>();
		ArrayList<LocalTime> times_list = new ArrayList<LocalTime>();
		LocalTime current_time = start_time;
		
		//collecting the times and date
		while(!current_time.equals(end_time)) {
			Booking book = calendar.get(date).get(current_time);
			booking_list.add(book);
			times_list.add(current_time);
			if(book.getStatus() == Status.unavailable || book.getStatus() == Status.booked) {
				isBooked = true;
			}
			//Check if the same customer placed pending if they did reject the request
			else if(book.getStatus() == Status.pending) {
				String existing_cust_id = book.getCustomerID();
				if(existing_cust_id.equals(customer_id)) {
					return false;
				}
			}
			current_time = current_time.plusMinutes(15);
		}
		
		if(!isBooked) {
			for(int i=0; i < booking_list.size(); i++) {
				Booking book = booking_list.get(i);
				book.addDetails(date, start_time, end_time, service, emp, customer_id);
				calendar.get(date).put(times_list.get(i), book);
				bookingList.add(book);
			}
			return true;
		}
		return false;
	}
	
	public Boolean acceptBooking(String bookingID) {
		Booking book = getBooking(bookingID);
		if(book != null) {
			Status status = book.getStatus();
			if(status == Calendar.Status.pending){
				LocalDate date = book.getDate();
				LocalTime start_time = book.getStartTime();
				LocalTime end_time = book.getEndTime();
				Employee emp = book.getEmployee();
				// can only accept booking if employee is free at the times given
				if(emp.isFree(date, start_time, end_time)) {
					book.getEmployee().addBooking(date, start_time, end_time);
					addBookingToCalendar(date, start_time, end_time);
					return true;	
				}
			}
		}
		return false;
	}
	
	public void addBookingToCalendar(LocalDate date, LocalTime start_time, LocalTime end_time) {
		LocalTime current_time = start_time;
		while(!current_time.equals(end_time)) {
			Booking book = calendar.get(date).get(current_time);
			book.setStatus(Status.booked);
			calendar.get(date).put(current_time, book);
			bookingList.add(book);
			current_time = current_time.plusMinutes(15);
		}
	}
	

	public Boolean declineBooking(String bookingID) {
		Booking book = getBooking(bookingID);
		if(book != null) {
			Status status = book.getStatus();
			if(status == Calendar.Status.pending){
				book.setStatus(Calendar.Status.free);
				bookingList.remove(bookingID);
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
	
	public LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Booking>> getHistory() {
		LocalDate oldDate = currentDate.minusDays(7);
		LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Booking>> historyInfo = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		for(int i =0; i < 7; i++){
			LinkedHashMap<LocalTime, Booking> timeInfo = calendar.get(oldDate);
			historyInfo.put(oldDate, timeInfo);
			oldDate = oldDate.plusDays(1);
		}
		return historyInfo;
	}
	
	public LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> getNextWeek() {
		LocalDate newDate = currentDate.plusDays(1);
		LinkedHashMap<LocalDate,LinkedHashMap<LocalTime,Booking>> futureInfo = new LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>>();
		for(int i =0; i < 7; i++){
			LinkedHashMap<LocalTime, Booking> timeInfo = calendar.get(newDate);
			futureInfo.put(newDate, timeInfo);
			newDate = newDate.plusDays(1);
		}
		return futureInfo;
	}
	
	public ArrayList<Booking> getPendingBooking() {
		Collections.sort(bookingList, new Comparator<Booking>() {
			@Override
			public int compare(Booking arg0, Booking arg1) {
				return arg0.getID().compareTo(arg1.getID());
			}
		});
		return bookingList;
	}
	
	public Booking getBooking(String ID) {
		for(Booking booking : bookingList) {
			if(booking.getID().equals(ID)) {
				return booking;
			}
		}
		return null;
	}
	
	public Boolean containsBooking(String ID) {
		for(Booking booking : bookingList) {
			if(booking.getID().equals(ID)) {
				return true;
			}
		}
		return false;
	}
	
	public String getBookingPendingString() {
		ArrayList<Booking> list = new ArrayList<Booking>();
		String output = "";
		for(Booking book : bookingList) {
			if(book.getStatus() == Status.pending) {
				list.add(book);
			}
		}
		
		for(Booking book : list) {
			LocalTime start_time = book.getStartTime();
			LocalTime end_time = book.getEndTime();
			Service service = book.getServices();
			String emp_id = book.getEmployee().getID();
			
			output = output + String.format("Booking ID: %s, Status: %s, Date: %s, Start Time: %s, End Time: %s, Customer: %s, Service: %s, Employee: %s \n",book.getID(), book.getStatus().toString(), book.getDate(), start_time, end_time, book.getCustomerID(), service, emp_id);
		}
		return output;
	}
	
	public String displayCalendar() {
		LinkedHashMap<LocalDate, LinkedHashMap<LocalTime, Booking>> info = getCalendarInfo();
		LocalDate date = getDate();
		String output = displayCalendar(info, date);
		return output;
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
		
		// printing the dates in a row
		for(int i = 0; i < 7; i++){
			output = output + String.format("|%-13s", date.toString());
			date = date.plusDays(1);
		}
		output = output + String.format("\n%s\n", printBorder("-", 12*9+8));
		
		LocalTime time = LocalTime.of(8, 00);
		LocalDate date2=startDate;
		while(!time.toString().equals(LocalTime.of(16, 00).toString())) {
			date2 = startDate;
			output = output + String.format("|%s - %s| ", time.toString(),time.plusMinutes(15).toString());
			for(int i =0; i < 7; i ++) {
				Status status;
				if(info.get(date2).get(time) == null){
					status = Status.unavailable;
				} else {
					status = info.get(date2).get(time).getStatus();  
				}
				output = output + String.format("%-13s|",status.toString());
				date2 = date2.plusDays(1);
			}
			output = output + String.format("\n%s\n", printBorder("-", 12*9+8));
			time = time.plusMinutes(15);
			
		}
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
