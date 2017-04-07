package AppoinmentProgram;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class Employee {
	
	private String ID;
	private String firstName;
	private String lastName;
	
	HashMap<LocalDate, ArrayList<LocalTime>> availability;
	private static ArrayList<Service> serviceType;
	
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
	
	public Employee(String ID, String firstName, String lastName, ArrayList<Service> serviceType){
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.serviceType = (ArrayList<Service>) serviceType.clone(); 
		availability = new HashMap<LocalDate, ArrayList<LocalTime>>();
	}
	
	// TODO: Needs Testing
	public static ArrayList<Service> getService() {
		return serviceType;
	}
	
	public void addAvailability(LocalDate date, LocalTime start_time, LocalTime end_time) {
		ArrayList<LocalTime> times = new ArrayList<LocalTime>();
		LocalTime time = start_time;
		while(!time.toString().equals(end_time.plusMinutes(15).toString())) {
			times.add(time);
			time = time.plusMinutes(15);
		}
		availability.put(date,times);
	}
	
	public HashMap<LocalDate, ArrayList<LocalTime>> getAvailability() {
		return availability;
	}

	public String getID() {
		return ID;
	}
}
