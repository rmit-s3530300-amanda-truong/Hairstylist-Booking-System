package Calendar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import Business.Employee.Service;

public class Booking {
	// Booking ID is the date and time concatenated
	private String ID;
	// String is the ID of the employee which will be performing the service
	private HashMap<Service, String> services;
	private LocalDate bookingDate;
	private LocalTime bookingTime;
	private Calendar.Status status;
	private String customerID;
	
	public Booking(String id) {
		status = Calendar.Status.unavailable;
		this.ID = id;
	}
	
	public Booking(Calendar.Status stat, String id) {
		status = stat;
		this.ID = id;
	}
	
	public void addDetails(HashMap<Service, String> service, LocalDate bookingDate, LocalTime bookingTime, String customerID) {
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

