package Business;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Employee {
	private String ID;
	private String firstName;
	private String lastName;
	private HashMap<DayOfWeek, ArrayList<LocalTime>> availability;
	private HashMap<LocalDate, ArrayList<LocalTime>> bookings;
	private ArrayList<Service> serviceType;
	
	// 1 block equals to 15 minutes so 2 blocks is 30minutes etc
	public enum Service {
		femaleCut(2),
		maleCut(1),
		femaleDye(4),
		maleDye(3),
		femalePerm(4),
		malePerm(3),
		femaleWash(1),
		maleWash(1);
		
		private int time;
		
		private Service(int s) {
			time = s;
		}
		
		public int getTime() {
			return time;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Employee(String ID, String firstName, String lastName, ArrayList<Service> serviceType){
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.serviceType = (ArrayList<Service>) serviceType.clone(); 
		// availability = new HashMap<LocalDate, ArrayList<LocalTime>>();
		availability = new HashMap<DayOfWeek, ArrayList<LocalTime>>();
		bookings =  new HashMap<LocalDate, ArrayList<LocalTime>>();
	}
	
	public ArrayList<Service> getService() {
		return serviceType;
	}
	
	public void addAvailability(DayOfWeek day, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = new ArrayList<LocalTime>();
		LocalTime time = start_time;
		while(!time.toString().equals(end_time.toString())) {
			times.add(time);
			time = time.plusMinutes(15);
		}
		availability.put(day,times);
	}
	
	public void addBooking(LocalDate date, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = new ArrayList<LocalTime>();
		LocalTime current_time = start_time;
		while(!current_time.equals(end_time.plusMinutes(15))) {
			times.add(current_time);
			current_time = current_time.plusMinutes(15); 
		}
		bookings.put(date, times);
	}
	
	public Boolean isFree(LocalDate date, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = bookings.get(date);
		LocalTime current_time;
		if(times != null) {
			current_time = times.get(0);
		} else {
			return true;
		}
		
		while(!current_time.equals(end_time)) {
			if(times.contains(current_time)) {
				return false;
			}
			current_time = current_time.plusMinutes(15);
		}
		return true;
	}
	
	public HashMap<DayOfWeek,ArrayList<LocalTime>> getAvailability() {
		return availability;
	}
	
	public String getID() {
		return ID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
