package JUnitTests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Business.Company;
import Business.Employee;
import Business.Employee.Service;
import Calendar.Calendar;
import Customer.Customer;
import Database.AvailabilityDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;

public class CompanyTest {
	Company comp;
	String ID;
	Customer customer;
	HashMap<String, Customer> custList;
	Employee employee;
	ArrayList<Service> service;
	CompanyDatabase compDb;
	CustomerDatabase custDb;
	AvailabilityDatabase availDb;
	
	@Before
	public void setUp() throws Exception {
		comp = new Company();
		compDb = new CompanyDatabase();
		custDb = new CustomerDatabase();
		availDb = new AvailabilityDatabase();
		ID = "000";
		customer = new Customer(ID,"fname", "lname","male");
		comp.addCustomer(customer);
		custList = comp.getCustList();
		
		service = new ArrayList<Service>();
		service.add(Service.femaleCut);
		employee = new Employee("0", "fname", "lname", service);
		comp.addEmployee(employee);
	}

	@Test
	public void testAddCustomer() {
		String expected_ID = "000";
		String expected_fname = "fname";
		String expected_lname = "lname";
		String expected_gender = "male";
		Customer customer = new Customer(expected_ID,expected_fname, expected_lname,expected_gender);
		
		comp.addCustomer(customer);
		HashMap<String, Customer>custList = comp.getCustList();
		Customer actual_customer = custList.get(expected_ID);
		
		// Does the customer ID exist in the HashMap
		Boolean expected = true;
		Boolean actual;
		Customer cust = custList.get(ID);
		if(cust != null) {
			actual = true;
		} else {
			actual = false;
		}
		assertEquals(expected, actual);
		
		String actual_ID = actual_customer.getUsername();
		assertEquals(expected_ID, actual_ID);
		
		String actual_fname = customer.getFirstName();
		assertEquals(expected_fname, actual_fname);
		
		String actual_gender = customer.getGender();
		assertEquals(expected_gender, actual_gender);
	}
	
	@Test 
	public void testGetCustomer() {
		Customer expected_customer = customer;
		Customer actual_customer = comp.getCustomer(ID);
		
		assertEquals(expected_customer, actual_customer);
	}
	
	@Test
	public void testGetCustList() {
		HashMap<String, Customer> expected = custList;
		HashMap<String, Customer> actual = comp.getCustList();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSetCustList() {
		HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> nested_info = new HashMap<String, String>();
		String expected_fname = "fname";
		String expected_lname = "lname";
		String expected_gender = "male";
		String expected_ID = "000";
		
		nested_info.put("fName", expected_fname);
		nested_info.put("lName", expected_lname);
		nested_info.put("gender", expected_gender);
		map.put(expected_ID, nested_info);
		
		comp.setCustList(map);
		HashMap<String, Customer> customerList = comp.getCustList();
		Customer customer = customerList.get(expected_ID);
		
		int expected_size = 1;
		int actual_size = comp.getCustList().size();
		assertEquals(expected_size, actual_size);
		
		Boolean expected_keyExist = true;
		Boolean actual_keyExist = customerList.containsKey(expected_ID);
		assertEquals(expected_keyExist, actual_keyExist);
		
		String actual_fname = customer.getFirstName();
		assertEquals(expected_fname, actual_fname);
		
		String actual_lname = customer.getLastName();
		assertEquals(expected_lname, actual_lname);
		
		String actual_gender = customer.getGender();
		assertEquals(expected_gender, actual_gender);
	}
	
	@Test
	public void testAddEmployee() {
		ArrayList<Service> expected_service = new ArrayList<Service>();
		expected_service.add(Service.femaleCut);
		String expected_ID = "0";
		String expected_fname = "fname";
		String expected_lname ="lname";
		Employee employee = new Employee("0", "fname", "lname", service);
		
		comp.addEmployee(employee);
		HashMap<String, Employee> employee_list = comp.getEmployeeList();
		Employee actual_employee = employee_list.get(expected_ID);
		
		int expected_size = 1;
		int actual_size = comp.getEmployeeList().size();
		assertEquals(expected_size, actual_size);
		
		Boolean expected_keyExist = true;
		Boolean actual_keyExist = employee_list.containsKey(expected_ID);
		assertEquals(expected_keyExist, actual_keyExist);
		
		String actual_fname = actual_employee.getFirstName();
		assertEquals(expected_fname, actual_fname);
		
		String actual_lname = actual_employee.getLastName();
		assertEquals(expected_lname, actual_lname);
		
		ArrayList<Service> actual_service = actual_employee.getService();
		assertEquals(expected_service, actual_service);
	}
	
	@Test
	public void testGetEmployeeList() {
		HashMap<String, Employee> expected_list = new HashMap<String, Employee>();
		expected_list.put(employee.getID(), employee);
		HashMap<String, Employee> actual_list = comp.getEmployeeList();
		
		assertEquals(expected_list, actual_list);
	}
	
	@Test
	public void testGetCalendar() {
		Calendar expected_cal = new Calendar(LocalDate.of(2017, 02, 02));
		comp.setCalendar(expected_cal);
		Calendar actual_cal = comp.getCalendar();
		
		assertEquals(expected_cal, actual_cal);
	}
	
	@Test
	public void testSetEmployeeList() {
		HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> nested_info = new HashMap<String, String>();
		String expected_fname = "fname";
		String expected_lname = "lname";
		String expected_ID = "000";
		String expected_service_string = "femaleCut, maleCut";
		ArrayList<Service> expected_service = new ArrayList<Service>();
		expected_service.add(Service.femaleCut);
		expected_service.add(Service.maleCut);
		
		nested_info.put("fname", expected_fname);
		nested_info.put("lname", expected_lname);
		nested_info.put("service", expected_service_string);
		map.put(expected_ID, nested_info);
		
		comp.setEmployeeList(map);
		HashMap<String, Employee> employee_list = comp.getEmployeeList();
		Employee actual_employee = employee_list.get(expected_ID);
		
		int expected_size = 1;
		int actual_size = comp.getEmployeeList().size();
		assertEquals(expected_size, actual_size);
		
		Boolean expected_keyExist = true;
		Boolean actual_keyExist = employee_list.containsKey(expected_ID);
		assertEquals(expected_keyExist, actual_keyExist);
		
		String actual_fname = actual_employee.getFirstName();
		assertEquals(expected_fname, actual_fname);
		
		String actual_lname = actual_employee.getLastName();
		assertEquals(expected_lname, actual_lname);
		
		ArrayList<Service> actual_service = actual_employee.getService();
		assertEquals(expected_service, actual_service);
	}
	
	@Test
	public void testGetServiceFemaleCut() {
		Service expected = Service.femaleCut;
		String s = "femaleCut";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMaleCut() {
		Service expected = Service.maleCut;
		String s = "maleCut";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFemaleDye() {
		Service expected = Service.femaleDye;
		String s = "femaleDye";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMaleDye() {
		Service expected = Service.maleDye;
		String s = "maleDye";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFemalePerm() {
		Service expected = Service.femalePerm;
		String s = "femalePerm";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMalePerm() {
		Service expected = Service.malePerm;
		String s = "malePerm";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFemaleWash() {
		Service expected = Service.femaleWash;
		String s = "femaleWash";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceMaleWash() {
		Service expected = Service.maleWash;
		String s = "maleWash";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);	
	}
	
	@Test
	public void testGetServiceFail() {
		Service expected = null;
		String s = "cut";
		Service actual = comp.getService(s);
		
		assertEquals(expected, actual);
	}
}
