package AppoinmentProgram;

import java.time.LocalDate;
import java.util.*;

import Calendar.Calendar;

public class Company {
	private HashMap<String, Employee> employeeList;
	private Calendar calendar;
	
	// TODO: Add in other details that company need here
	public Company() {
		employeeList = new HashMap<String, Employee>();
		this.calendar = new Calendar(LocalDate.now());
	}
	
	public void addEmployee(Employee employee) {
		String ID = employee.getID();
		employeeList.put(ID, employee);
	}
	
	// TODO: Needs Testing
	public Employee getEmployee(String ID) {
		return employeeList.get(ID);
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
}
