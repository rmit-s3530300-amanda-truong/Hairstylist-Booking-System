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
