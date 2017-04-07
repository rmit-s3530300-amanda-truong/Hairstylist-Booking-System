package AppoinmentProgram;
import java.time.LocalDate;
import java.time.LocalTime;

import Calendar.Calendar;

public class Booking {
	private String ID;
	private Employee.Service[] serviceType;
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
	
	public void addDetails(String ID, Employee.Service[] serviceType, LocalDate bookingDate, LocalTime bookingTime, Calendar.Status status, String customerID) {
		this.ID = ID;
		this.serviceType = serviceType.clone();
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.customerID = customerID;
		this.status = Calendar.Status.pending;
	}
	
	// TODO: Needs Testing
	public void acceptBooking() {
		status = Calendar.Status.booked;
	}
	
	// TODO: remove from booking pending list
	// TODO: Needs Testing
	public void declineBooking() {
		status = Calendar.Status.free;	
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
	
}

