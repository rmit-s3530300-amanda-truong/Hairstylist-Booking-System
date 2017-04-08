package JUnitTests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import AppoinmentProgram.Employee;
import AppoinmentProgram.Employee.Service;

public class EmployeeTest {
	
	Employee e;
	String id;
	String firstName;
	String lastName;
	ArrayList<Employee.Service> service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		id = "e0005";
		firstName = "john";
		lastName = "snow";
		service = new ArrayList<Employee.Service>();
		service.add(Service.maleWash);
		e = new Employee(id, firstName, lastName, service);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testgetID() throws Exception {
		String eID = e.getID();
		assertEquals(id, eID);
	}
	
	@Test
	public void testgetFname() throws Exception {
		String efName = e.getFirstName();
		assertEquals(firstName, efName);
	}
	
	@Test
	public void testgetLname() throws Exception {
		String elName = e.getLastName();
		assertEquals(lastName, elName);
	}
	
	@Test
	public void testgetService() throws Exception {
		ArrayList<Service> eService = Employee.getService();
		assertEquals(eService,service);
	}

	@Test
	public void AddAvailabilityTest() {
		LocalDate date = LocalDate.of(2017, 10, 30);
		LocalTime start_time = LocalTime.of(3, 00);
		LocalTime end_time = LocalTime.of(5, 15);
		e.addAvailability(date, start_time, end_time);
		HashMap<LocalDate, ArrayList<LocalTime>>availability_1 = e.getAvailability();
		String actual_availability = null;
		
		for(Entry<LocalDate, ArrayList<LocalTime>> x : availability_1.entrySet()) {
			actual_availability = x.getKey().toString();
			for(int i=0; i<x.getValue().size();i++){
				actual_availability = actual_availability + ", "+x.getValue().get(i).toString();
			}
		}
		String expected_availability = "2017-10-30, 03:00, 03:15, 03:30, 03:45, 04:00, 04:15, 04:30, 04:45, 05:00, 05:15";
		
		assertEquals(actual_availability, expected_availability);
	}
	
	@Test
	public void GetAvailabilityTest() {
		LocalDate date = LocalDate.of(2017, 10, 30);
		LocalTime start_time = LocalTime.of(3, 00);
		LocalTime end_time = LocalTime.of(3, 15);
		e.addAvailability(date, start_time, end_time);
		HashMap<LocalDate, ArrayList<LocalTime>> actual_availability = e.getAvailability();
		
		HashMap<LocalDate, ArrayList<LocalTime>> expected_availability = new HashMap<LocalDate, ArrayList<LocalTime>>();
		ArrayList<LocalTime> time = new ArrayList<LocalTime>();
		time.add(LocalTime.of(3, 00));
		time.add(LocalTime.of(3, 15));
		expected_availability.put(LocalDate.of(2017, 10, 30), time);
		
		assertEquals(actual_availability, expected_availability);
		
	}

}
