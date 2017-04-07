package AppoinmentProgram;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import AppoinmentProgram.Employee.Service;
import Calendar.Calendar;

public class Booking {
	private String ID;
	private HashMap<Service, String> services;
	private LocalDate bookingDate;
	private LocalTime bookingTime;
	private Calendar.Status status;
	private String customerID;
	
	public Booking() {
		status = Calendar.Status.unavailable;
	}
	
	public Booking(Calendar.Status stat) {
		status = stat;
	}
	
	public void addDetails(String ID, HashMap<Service, String> serviceType, LocalDate bookingDate, LocalTime bookingTime, String customerID) {
		this.ID = ID;
		this.services = serviceType;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.customerID = customerID;
		this.status = Calendar.Status.pending;
	}
	
	// TODO: Needs Testing
	public Boolean acceptBooking() {
		if(status == Calendar.Status.pending){
			status = Calendar.Status.booked;
			return true;
		}
		return false;
	}
	
	// TODO: remove from booking pending list
	// TODO: Needs Testing
	public Boolean declineBooking() {
		if(status == Calendar.Status.pending){
			status = Calendar.Status.free;
			return true;
		}
		return false;
	}
	
	public LocalDate getDate() {
		return bookingDate;
	}
	
	public LocalTime getTime() {
		return bookingTime;
	}
	
	public Calendar.Status getStatus() {
		return status;
	}
	
	public void setStatus(Calendar.Status stat) {
		status = stat;
	}
	
	public String getCustomerID() {
		return customerID;
	}
	
	public HashMap<Service, String> getServices() {
		return services;
	}
	
}

