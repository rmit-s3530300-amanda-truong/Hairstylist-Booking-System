package testCases;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import business.Employee;
import business.Employee.Service;

public class EmployeeTest {
	
	Employee e;
	String expected_id;
	String expected_firstName;
	String expected_lastName;
	ArrayList<Employee.Service> expected_service;

	@Before
	public void setUp() throws Exception {
		expected_id = "e0005";
		expected_firstName = "john";
		expected_lastName = "snow";
		expected_service = new ArrayList<Employee.Service>();
		expected_service.add(Service.maleWash);
		e = new Employee(expected_id, expected_firstName, expected_lastName, expected_service);
	}
	
	@Test
	public void testgetID() throws Exception {
		String actual_ID = e.getID();
		assertEquals(expected_id, actual_ID);
	}
	
	@Test
	public void testgetFname() throws Exception {
		String actual_firstName = e.getFirstName();
		assertEquals(expected_firstName, actual_firstName);
	}
	
	@Test
	public void testgetLname() throws Exception {
		String actual_Lname = e.getLastName();
		assertEquals(expected_lastName, actual_Lname);
	}
	
	@Test
	public void testgetService() throws Exception {
		ArrayList<Service> actual_service = e.getService();
		assertEquals(expected_service,actual_service);
	}

	@Test
	public void AddAvailabilityTest() {
		LocalDate date = LocalDate.of(2017, 10, 30);
		LocalTime start_time = LocalTime.of(3, 00);
		LocalTime end_time = LocalTime.of(5, 15);
		DayOfWeek day = date.getDayOfWeek();
		e.addAvailability(day, start_time, end_time);
		HashMap<DayOfWeek, ArrayList<LocalTime>>availability_1 = e.getAvailability();
		String actual_availability = null;
		
		for(Entry<DayOfWeek, ArrayList<LocalTime>> x : availability_1.entrySet()) {
			actual_availability = x.getKey().toString();
			for(int i=0; i<x.getValue().size();i++){
				actual_availability = actual_availability + ", "+x.getValue().get(i).toString();
			}
		}
		String expected_availability = "MONDAY, 03:00, 03:15, 03:30, 03:45, 04:00, 04:15, 04:30, 04:45, 05:00";
		
		assertEquals(expected_availability, actual_availability);
	}
	
	@Test
	public void GetAvailabilityTest() {
		LocalDate date = LocalDate.of(2017, 10, 30);
		LocalTime start_time = LocalTime.of(3, 00);
		LocalTime end_time = LocalTime.of(3, 30);
		DayOfWeek day = date.getDayOfWeek();
		e.addAvailability(day, start_time, end_time);
		HashMap<DayOfWeek, ArrayList<LocalTime>> actual_availability = e.getAvailability();
		
		HashMap<DayOfWeek, ArrayList<LocalTime>> expected_availability = new HashMap<DayOfWeek, ArrayList<LocalTime>>();
		ArrayList<LocalTime> time = new ArrayList<LocalTime>();
		time.add(LocalTime.of(3, 00));
		time.add(LocalTime.of(3, 15));
		expected_availability.put(day, time);
		
		assertEquals(expected_availability, actual_availability);
	}

}
