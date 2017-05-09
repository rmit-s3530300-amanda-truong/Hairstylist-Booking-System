package calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;

import business.Employee;
import business.Employee.Service;
import calendar.Calendar;

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
	private ArrayList<Booking> displayBookedList;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	public Calendar(LocalDate date) {
		bookingList = new ArrayList<Booking>();
		displayBookedList = new ArrayList<Booking>();
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
	
	// Called to retrieve information from database and populates ArrayList<Booking> bookingList
	public void setBookingList(ArrayList<Booking> newBookList)
	{
		if(newBookList.size()>0) {
			for(int i =0;i<newBookList.size();i++) {
				Booking book = newBookList.get(i);
				book.setStatus(Status.booked);
				bookingList.add(book);
			}
			updateBookingList();
		}
	}
	
	// Helper method for setBookingList
	public void updateBookingList() {
		if(bookingList.size() > 0)
		{	
		    for(int x =0; x<bookingList.size();x++) {
		    	Booking newBook = bookingList.get(x);
		    	LocalDate date = newBook.getDate();
		    	LocalTime s_time = newBook.getStartTime();
		    	LocalTime e_time = newBook.getEndTime();
		    	Boolean canAdd = true;
		    	for(int i =0; i<displayBookedList.size();i++) 
		    	{
		    		Booking current = displayBookedList.get(i);
		    		LocalDate date2 = current.getDate();
		    		LocalTime s_time2 = current.getStartTime();
		    		LocalTime e_time2 = current.getEndTime();
		    		
		    		if(date.equals(date2) && s_time.equals(s_time2) && e_time.equals(e_time2))
		    		{
		    			canAdd = false;
		    		}
		    	}
		    	if(canAdd)
		    	{
	    			displayBookedList.add(newBook);
		    		
		    	}
		    }
		}
	}
	
	// Gets the String concatenation of all pending/booked bookings
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
	
	// Called after database populates employeeList and updates the calendar with the employee availability
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
						String id = date.toString()+"/"+available_times.get(x).toString();
						map_time.put(available_times.get(x), new Booking(Status.free,id));
					}
					calendar.put(date, map_time);
				}
				date = date.plusDays(1);
				day = date.getDayOfWeek();
			}
		}
	}
	
	// Checks if the booking is booked
	public Boolean isBooked(LocalDate date, LocalTime time) {
		Status status = calendar.get(date).get(time).getStatus();
		if(status == Status.booked) {
			LOGGER.info("isBooked: TRUE");
			return true;
		}
		else {
			LOGGER.info("isBooked: FALSE");
			return false;
		}
	}
	
	public LocalDate getDate() {
		LOGGER.info("getDate: "+currentDate.toString());
		return currentDate;
	}
	
	public void setCurrentDate(LocalDate date) {
		LOGGER.info(currentDate.toString()+"->"+date);
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
			if(book.getStatus() == Status.unavailable || book.getStatus() == Status.booked) {
				isBooked = true;
			}
			//Check if the same customer placed pending if they did reject the request
			else if(book.getStatus() == Status.pending) {
				String existing_cust_id = book.getCustomerID();
				if(existing_cust_id.equals(customer_id)) {
					LOGGER.info("Cannot Request: Existing Booking");
					return false;
				} else {
					booking_list.add(book);
					times_list.add(current_time);
				}
			} else {
				booking_list.add(book);
				times_list.add(current_time);
			}
			current_time = current_time.plusMinutes(15);
		}
		
		// if booking is not booked
		if(!isBooked) {
			for(int i=0; i < booking_list.size(); i++) {
				Booking book = booking_list.get(i);
				book.addDetails(date, start_time, end_time, service, emp, customer_id);
				calendar.get(date).put(times_list.get(i), book);
				bookingList.add(book);
			}
			displayBookedList.add(booking_list.get(0));
			return true;
		} else {
			LOGGER.info("Cannot Request: Time is already booked");
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
				else {
					LOGGER.info("Cannot Accept: Employee is not free");
				}
			} else {
				LOGGER.info("Cannot Accept: The booking status is not pending");	
			}
			
		}
		else {
			LOGGER.info("Cannot Accept: The booking is null");
		}
		return false;
	}
	
	public void addBookingToCalendar(LocalDate date, LocalTime start_time, LocalTime end_time) {
		LocalTime current_time = start_time;
		LOGGER.info("Adding Booking To Calendar: "+date.toString()+" "+start_time.toString()+""+end_time.toString());
		while(!current_time.equals(end_time)) {
			Booking book = calendar.get(date).get(current_time);
			book.setStatus(Status.booked);
			calendar.get(date).put(current_time, book);
			current_time = current_time.plusMinutes(15);
		}
	}

	public Boolean declineBooking(String bookingID) {
		Booking book = getBooking(bookingID);
		if(book != null) {
			Status status = book.getStatus();
			if(status == Calendar.Status.pending){
				LocalTime current_time = book.getStartTime();
				LocalTime end_time = book.getEndTime();
				LocalDate date = book.getDate();
				while(!current_time.equals(end_time)) {
					book.setStatus(Calendar.Status.free);
					book.addDetails(null, null, null, null, null, "");
					calendar.get(date).put(current_time, book);
					
					// Finding the times to remove from BookingLIst
					for(int i=0;i<bookingList.size();i++) {
						Booking book1 = bookingList.get(i);
						if(book.getID().equals(bookingID)) {
							bookingList.remove(book1);
						}
					}
					
					// Finding the times to remove from displayBookedList
					for(int i=0;i<displayBookedList.size();i++) {
						Booking book2 = displayBookedList.get(i);
						if(book.getID().equals(bookingID)) {
							displayBookedList.remove(book2);
						}
					}
					current_time = current_time.plusMinutes(15);
				}
				return true;
			}
			LOGGER.info("Cannot Decline: Booking status is not pending");
			return false;
		} else {
			LOGGER.info("Cannot Decline: Booking is null");
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
	
	public ArrayList<Booking> getBookingList() {
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
		LOGGER.info("getBooking: null");
		return null;
	}
	
	public ArrayList<Booking> getDisplayFutureBooking() {
		ArrayList<Booking> future = new ArrayList<Booking>();
		for(Booking book : displayBookedList) {
			if(book.getStatus() == Status.booked) {
				if(book.getDate().isEqual(currentDate) || book.getDate().isAfter(currentDate)) {
					future.add(book);
				}
			}
		}
		Collections.sort(future, new Comparator<Booking>() {
			@Override
			public int compare(Booking arg0, Booking arg1) {
				return arg0.getID().compareTo(arg1.getID());
			}
		});
		return future;
	}
	
	public ArrayList<Booking> getDisplayPastBooking() {
		ArrayList<Booking> past = new ArrayList<Booking>();
		for(Booking book : displayBookedList) {
			if(book.getStatus() == Status.booked) {
				if(book.getDate().isBefore(currentDate)) {
					past.add(book);
				}
			}
		}
		Collections.sort(past, new Comparator<Booking>() {
			@Override
			public int compare(Booking arg0, Booking arg1) {
				return arg0.getID().compareTo(arg1.getID());
			}
		});
		return past;
	}
	
	public Boolean containsBooking(String ID) {
		for(Booking booking : bookingList) {
			if(booking.getID().equals(ID)) {
				LOGGER.info("containsBooking: TRUE");
				return true;
			}
		}
		LOGGER.info("containsBooking: FALSE");
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
	
	public String getDisplayPendingString() {
		ArrayList<Booking> list = new ArrayList<Booking>();
		String output = "";
		for(Booking book : displayBookedList) {
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
	
	public ArrayList<Booking> getDisplayPendingBooking() {
		ArrayList<Booking> list = new ArrayList<Booking>();
		
		for(Booking book : displayBookedList) {
			if(book.getStatus() == Status.pending) {
				list.add(book);
			}
		}
		return list;
	}
	
	public String getDisplayBookedString() {
		ArrayList<Booking> list = new ArrayList<Booking>();
		String output = "";
		for(Booking book : displayBookedList) {
			if(book.getStatus() == Status.booked) {
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
	
	public String getBookingString() {
		String output="";
		for(Booking book : displayBookedList) {
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
