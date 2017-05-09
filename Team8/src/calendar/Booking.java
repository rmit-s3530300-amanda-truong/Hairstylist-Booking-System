package calendar;
import java.time.LocalDate;
import java.time.LocalTime;

import business.Employee;
import business.Employee.Service;

public class Booking {
	// Booking ID is the date and time concatenated
	private String ID;
	private Employee emp;
	private Service service;
	private LocalDate bookingDate;
	private LocalTime start_time;
	private LocalTime end_time;
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
	
	public void addDetails(LocalDate bookingDate, LocalTime start_time, LocalTime end_time, Service service, Employee emp, String customerID) {
		this.service = service;
		this.emp = emp;
		this.bookingDate = bookingDate;
		this.start_time = start_time;
		this.end_time = end_time;
		this.customerID = customerID;
		this.status = Calendar.Status.pending;
	}
	
	public LocalDate getDate() {
		return bookingDate;
	}
	
	public LocalTime getStartTime() {
		return start_time;
	}
	
	public LocalTime getEndTime() {
		return end_time;
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
	
	public Service getServices() {
		return service;
	}
	
	public Employee getEmployee() {
		return emp;
	}
	
	public String getID() {
		return ID;
	}
	
}

