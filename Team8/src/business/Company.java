package business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import calendar.Booking;
import calendar.Calendar;
import calendar.Calendar.Status;
import database.AvailabilityDatabase;
import database.BookingDatabase;
import database.CompanyDatabase;
import database.CustomerDatabase;
import database.ServicesDatabase;

public class Company {
	private HashMap<String, Employee> employeeList;
	private HashMap<String, Customer> custList;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	private Calendar calendar;
	private ArrayList<String> services;
	// String is service name, int is time taken
	private HashMap<String, Integer> service_times; 
	private LocalTime start_time;
	private LocalTime end_time;
	
	public Company() {
		employeeList = new HashMap<String, Employee>();
		custList = new HashMap<String, Customer>();
		this.calendar = new Calendar(LocalDate.now());
		services = new ArrayList<String>();
		service_times = new HashMap<String, Integer>();
	}
	
	public void addCustomer(Customer customer) {
		String username = customer.getUsername();
		LOGGER.info("addCustomer: "+customer.getUsername());
		custList.put(username, customer);
	}
	
	public Customer getCustomer(String username) {
		LOGGER.info("getCustomer: "+username);
		return custList.get(username);
	}
	
	public HashMap<String, Customer> getCustList() {
		return custList;
	}
	
	public void setStartTime(LocalTime time) {
		start_time = time;
	}
	
	public void setEndTime(LocalTime time) {
		end_time = time;
	}
	
	public void addService(String service) {
		services.add(service);
	}
	
	public ArrayList<String> getService() {
		return services;
	}
	
	public void addServiceTime(String service, int time) {
		service_times.put(service, time);
	}
	
	public HashMap<String, Integer> getServiceTime() {
		return service_times;
	}
	
	public int getServiceTime(String service) {
		return service_times.get(service);
	}
	
	// For debugging to print out all employee availability
	public String showEmployeeAvailability() {
		String output="";
		for(Entry<String, Employee> entry : employeeList.entrySet()) {
			if(!entry.getValue().getAvailability().values().isEmpty()) {
				output = output + entry.getKey() + ":" + entry.getValue().getFirstName() + "\n";
				HashMap<DayOfWeek, ArrayList<LocalTime>> list = entry.getValue().getAvailability();
				for(Entry<DayOfWeek, ArrayList<LocalTime>> entry2 : list.entrySet()) {
					output = output + entry2.getKey().toString() + " " + entry2.getValue().get(0) + "-" + entry2.getValue().get(entry2.getValue().size()-1).plusMinutes(15) + "\n";
				}
				output = output + "\n";
			}
		}
		return output;
	}
	
	// Get database info and populates customer, employee and calendar info
	public void retrieveDatabaseInfo(CustomerDatabase customerDb, CompanyDatabase companyDb, AvailabilityDatabase availDb, BookingDatabase bookingDb, ServicesDatabase servDb) {
		HashMap<String, HashMap<String,String>> empValues;
		HashMap<String, HashMap<String,String>> custValues;
		HashMap<String, ArrayList<String>> availValues;
		HashMap<String, ArrayList<String>> bookValues;
		HashMap<String, String> serviceTimeDb;
		//HashMap<Service, String> serviceList;
		ArrayList<Booking> bookList;
		empValues = companyDb.storeEmpValues();
		setEmployeeList(empValues);
		LOGGER.info("Set Employee List");
		custValues = customerDb.storeCustomerValues();
		setCustList(custValues);
		LOGGER.info("Set Customer Values");
		availValues = availDb.storeAvailValues();
		setAvailList(availValues);
		LOGGER.info("Set Available Values");
		bookValues = bookingDb.storeBookingValues();
		bookList = setBookingList(bookValues);
		LOGGER.info("Set Booking Values"); 
		serviceTimeDb = servDb.storeServiceValues();
		//serviceList = setServList(serviceTimeDb);
		LOGGER.info("Set Service Values");
	}
	
	public HashMap<DayOfWeek, ArrayList<LocalTime>> setAvailList(HashMap<String, ArrayList<String>> list)
	{
		HashMap<DayOfWeek,ArrayList<LocalTime>> timeMap = new HashMap<DayOfWeek,ArrayList<LocalTime>>();
		String employeeID = null;
		String dayStr;
		String startTimeStr;
		String endTimeStr;
		String key;
		
		for(Map.Entry<String, ArrayList<String>> x: list.entrySet())
		{
			ArrayList<String> infoList = x.getValue();
			key = x.getKey();
			startTimeStr = infoList.get(1);
			endTimeStr = infoList.get(2);
			String[] keyList = key.split(":");
			dayStr = keyList[1];
			employeeID = keyList[0];
			
			String[] startTimeList = startTimeStr.split(":");
			String[] endTimeList = endTimeStr.split(":");
			DayOfWeek day = DayOfWeek.valueOf(dayStr.toUpperCase());
			int startHourInt = Integer.parseInt(startTimeList[0]);
			int startMinInt = Integer.parseInt(startTimeList[1]);
			int endHourInt = Integer.parseInt(endTimeList[0]);
			int endMinInt = Integer.parseInt(endTimeList[1]);
			LocalTime startTime = LocalTime.of(startHourInt, startMinInt);
			LocalTime endTime = LocalTime.of(endHourInt, endMinInt);
			Employee emp = getEmployee(employeeID);
			emp.addAvailability(day,startTime,endTime);
			timeMap = emp.getAvailability();
		}
		return timeMap;
	}
	
	public void setCustList(HashMap<String, HashMap<String, String>> map) {
		HashMap<String, Customer> custList = new HashMap<String, Customer>();
		for(Entry<String, HashMap<String,String>> x : map.entrySet()) {
			String username = x.getKey();
			String fname = x.getValue().get("fName");
			String lname = x.getValue().get("lName");
			custList.put(username, new Customer(username,fname,lname));
		}
		this.custList = custList;
	}
	
	/*public HashMap<String,String> setServList(HashMap<String,String> map) {
		HashMap<Service,String> serviceList = new HashMap<Service, String>();
		for(Entry<String, String> x : map.entrySet()) {
			String[] key = x.getKey().split(":");
			String serviceStr = key[1];
			Service service = getService(serviceStr);
			String time = x.getValue();
			serviceList.put(service, time);
		}
		return serviceList;
	}*/
	
	public ArrayList<Booking> setBookingList(HashMap<String, ArrayList<String>> map){
		ArrayList<Booking> bookList = new ArrayList<Booking>();
		HashMap<String, ArrayList<String>> storedBooking = map;
		String bookingID;
		String customerUsername;
		String employeeID;
		String statusStr;
		String serviceStr;
		String service;
		String dateStr;
		String timeStr;
		LocalDate date;
		LocalTime startTime;
		LocalTime endTime;
		Status status = null;
		for(Entry<String, ArrayList<String>> x : storedBooking.entrySet()){
			ArrayList<String> infoList = x.getValue();
			bookingID = x.getKey();
			customerUsername = infoList.get(0);
			dateStr = infoList.get(1);
			timeStr = infoList.get(2);
			employeeID = infoList.get(3);
			serviceStr = infoList.get(4);
			statusStr = infoList.get(5);
			status = getStatus(statusStr);
			service = serviceStr;
			String[] timeList = timeStr.split("-");
			String startTimeStr = timeList[0];
			String endTimeStr = timeList[1];
			date = LocalDate.parse(dateStr);
			startTime = LocalTime.parse(startTimeStr);
			endTime = LocalTime.parse(endTimeStr);
			Booking booking = new Booking(status, bookingID);
			Employee emp = getEmployee(employeeID);
			booking.addDetails(date, startTime, endTime, service, emp, customerUsername);
			bookList.add(booking);
		}
		calendar.setBookingList(bookList);
		return bookList;
	}
	
	public void addEmployee(Employee employee) {
		String ID = employee.getID();
		LOGGER.info("addEmployee: "+ID);
		employeeList.put(ID, employee);
	}
	
	public Employee getEmployee(String ID) {
		LOGGER.info("getEmployee: "+ID);
		return employeeList.get(ID);
	}
	
	public HashMap<String, Employee> getEmployeeList() {
		return employeeList;
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public void setCalendar( Calendar cal) {
		calendar = cal;
	}
	
	public void setEmployeeList(HashMap<String, HashMap<String, String>> map) {
		HashMap<String, Employee> employList = new HashMap<String, Employee>();
		for(Entry<String, HashMap<String,String>> x : map.entrySet()) {
			ArrayList<String> service_arraylist = new ArrayList<String>();
			String username = x.getKey();
			String fname = x.getValue().get("fname");
			String lname = x.getValue().get("lname");
			String service_list = x.getValue().get("service");
			String[] services = service_list.split(", ");
			for(int i=0;i<services.length;i++) {
				String type = services[i];
				if(type != null) {
					service_arraylist.add(type);
				}
			}
			employList.put(username, new Employee(username,fname,lname,service_arraylist));
		}
		employeeList = employList;
	}
	
	// Helper method for setEmployeeList
	// Compares string to service enum and returns the service enum if they equal
	/*public Service getService(String s) {
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
		else if (s.equals(Service.maleWash.toString())){
			return Service.maleWash;
		} else {
			return null;
		}
	}*/
	
	public Status getStatus(String s)
	{
		if(s.equals(Status.booked.toString()))
		{
			 return Status.booked;
		}
		else if(s.equals(Status.free.toString()))
		{
			return Status.free;
		}
		else if(s.equals(Status.unavailable.toString()))
		{
			return Status.unavailable;
		}
		else
		{
			return null;
		}
			
	}
}
