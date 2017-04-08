package AppoinmentProgram;

import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;

import AppoinmentProgram.Employee.Service;
import Calendar.Calendar;
import Database.CompanyDatabase;
import Database.CustomerDatabase;

public class Company {
	private HashMap<String, Employee> employeeList;
	private HashMap<String, Customer> custList;
	private Calendar calendar;
	
	// TODO: Add in other details that company need here
	public Company() {
		employeeList = new HashMap<String, Employee>();
		custList = new HashMap<String, Customer>();
		this.calendar = new Calendar(LocalDate.now());
	}
	
	public void addCustomer(Customer customer) {
		String username = customer.getUsername();
		custList.put(username, customer);
	}
	
	public Customer getCustomer(String username) {
		return custList.get(username);
	}
	
	public void retrieveDatabaseInfo(CustomerDatabase customerDb, CompanyDatabase companyDb) {
		HashMap<String, HashMap<String,String>> empValues;
		HashMap<String, HashMap<String,String>> custValues;
		empValues = companyDb.storeEmpValues();
		setEmployeeList(empValues);
		custValues = customerDb.storeCustomerValues();
		setCustList(custValues);
	}
	
	public void setCustList(HashMap<String, HashMap<String, String>> map) {
		HashMap<String, Customer> custList = new HashMap<String, Customer>();
		for(Entry<String, HashMap<String,String>> x : map.entrySet()) {
			String username = x.getKey();
			String fname = x.getValue().get("fName");
			String lname = x.getValue().get("lName");
			String gender = x.getValue().get("gender");
			custList.put(username, new Customer(username,fname,lname, gender));
		}
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
	
	public void setEmployeeList(HashMap<String, HashMap<String, String>> map) {
		HashMap<String, Employee> employList = new HashMap<String, Employee>();
		for(Entry<String, HashMap<String,String>> x : map.entrySet()) {
			ArrayList<Service> service_arraylist = new ArrayList<Service>();
			String username = x.getKey();
			String fname = x.getValue().get("fname");
			String lname = x.getValue().get("lname");
			String service_list = x.getValue().get("service");
			String[] services = service_list.split(", ");
			for(int i=0;i<services.length;i++) {
				Service type = getService(services[i]);
				if(type != null) {
					service_arraylist.add(type);
				}
			}
			employList.put(username, new Employee(username,fname,lname,service_arraylist));
		}
	}
	
	// Helper method for setEmployeeList
	// Compares string to service enum and returns the service enum if they equal
	public Service getService(String s) {
		if(s.equals(Service.femaleCut.toString())){
			return Service.femaleCut;
		}
		else if(s.equals(Service.maleCut.toString())){
			return Service.maleCut;
		}
		else if(s.equals(Service.femaleDye.toString())){
			return Service.femaleDye;
		}
		else if(s.equals(Service.maleDye.toString())){
			return Service.maleDye;
		}
		else if(s.equals(Service.femalePerm.toString())){
			return Service.femalePerm;
		}
		else if(s.equals(Service.malePerm.toString())){
			return Service.malePerm;
		}
		else if(s.equals(Service.femaleWash.toString())){
			return Service.femaleWash;
		} 
		else if (s.equals(Service.femaleWash.toString())){
			return Service.maleWash;
		} else {
			return null;
		}
	}
}
