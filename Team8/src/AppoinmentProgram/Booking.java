package AppoinmentProgram;
import Calendar.Calendar;

public class Booking {
	private String ID;
	private Employee.Service[] serviceType;
	private String bookingDate;
	private String bookingTime;
	private Calendar.Status status;
	
	
	public Booking(String ID, Employee.Service[] serviceType, String bookingDate, String bookingTime, Calendar.Status status) {
		this.ID = ID;
		this.serviceType = serviceType.clone();
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.status = status;
	}
	
	// TODO: Needs Testing
	public void acceptBooking() {
		status = Calendar.Status.booked;
	}
	
	// TODO: Delete the Booking object and remove from booking pending list
	// TODO: Needs Testing
	public void declineBooking() {
		
	}
}
