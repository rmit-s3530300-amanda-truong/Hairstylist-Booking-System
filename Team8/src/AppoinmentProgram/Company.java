package AppoinmentProgram;

import java.util.*;

import Calendar.Calendar;

public class Company {
	private String businessName;
	private HashMap<String, Employee> employeeList;
	private String ownerID;
	private Calendar calendar;
	
	// TODO: Add in other details that company need here
	public Company(String businessName, String ownerID, Calendar calendar) {
		this.businessName = businessName;
		this.ownerID = ownerID;
		employeeList = new HashMap<String, Employee>();
		this.calendar = calendar;
	}
	
	// TODO: Needs Testing
	public void addEmployee(Employee employee) {
		String ID = employee.getID();
		employeeList.put(ID, employee);
	}
	
	// TODO: Needs Testing
	public void removeEmployee(String ID) {
		
	}
	
	// TODO: Needs Testing
	public Employee getEmployee(String ID) {
		return null;
	}
	
}
