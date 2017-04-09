package AppoinmentProgram;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import AppoinmentProgram.Employee.Service;
import Calendar.Calendar;

public class Booking {
	private String ID;
	// String is the ID of the employee which will be performing the service
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
	
	public void addDetails(String ID, HashMap<Service, String> service, LocalDate bookingDate, LocalTime bookingTime, String customerID) {
		this.ID = ID;
		this.services = service;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.customerID = customerID;
		this.status = Calendar.Status.pending;
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
	
	public String getID() {
		return ID;
	}
	
}

