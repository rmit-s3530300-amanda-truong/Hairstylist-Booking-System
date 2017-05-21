package business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import mainController.MainController;

public class Company {
	private HashMap<String, Employee> employeeList;
	private HashMap<String, Customer> custList;
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	private Calendar calendar;
	private ArrayList<String> services;
	// String is service name, int is time taken
	private HashMap<String, Integer> service_times; 
	private LinkedHashMap<DayOfWeek,String> business_hours;
	private String busHours;
	
	private MainController menu;
	
	private String compName;
	private String username;
	private String password;
	private String owner_fname;
	private String owner_lname;
	private String mobile;
	private String address;
	private String service;
	private String status;
	
	private CompanyDatabase companyDb = new CompanyDatabase();
	private CustomerDatabase customerDb = new CustomerDatabase();
	private AvailabilityDatabase availDb = new AvailabilityDatabase();
	private BookingDatabase bookingDb = new BookingDatabase();
	private ServicesDatabase servDb = new ServicesDatabase();
	
	public Company(String fName, String lName, String uName, String password, String mobileNo, String address) {
		owner_fname = fName;
		owner_lname = lName;
		username = uName;
		this.password = password;
		mobile = mobileNo;
		this.address = address;
		employeeList = new HashMap<String, Employee>();
		custList = new HashMap<String, Customer>();
		services = new ArrayList<String>();
		service_times = new HashMap<String, Integer>();
		this.calendar = new Calendar(LocalDate.now());
		business_hours = new LinkedHashMap<DayOfWeek, String>();
		menu = new MainController(this, customerDb, companyDb, availDb, bookingDb, servDb);
	}
	
	// TODO: change String bushours to HashMap<DayOfWeek, String>
	public Company(String compName, String username, String password, String fname, String lname, String mobile, String address, String service, String busHours, String status) {
		employeeList = new HashMap<String, Employee>();
		custList = new HashMap<String, Customer>();
		this.calendar = new Calendar(LocalDate.now());
		services = new ArrayList<String>();
		service_times = new HashMap<String, Integer>();
		business_hours = new LinkedHashMap<DayOfWeek, String>();
		
		this.compName = compName;
		this.username = username;
		this.password = password;
		owner_fname = fname;
		owner_lname = lname;
		this.mobile = mobile;
		this.address = address;
		this.service = service;
		this.busHours = busHours;
		String[] split =busHours.split("\\|",-1);
		for(int i=0; i<split.length;i++){
			System.out.println(split[i]);
			String[] day_times = split[i].split("=|,");
			System.out.println(day_times[0]);
			if(day_times[1].equals("empty")) {
				business_hours.put(DayOfWeek.valueOf(day_times[0].toUpperCase()), "empty");
			} else {
				System.out.println(day_times[0]+" "+day_times[1]+" "+day_times[2]);
				business_hours.put(DayOfWeek.valueOf(day_times[0].toUpperCase()), day_times[1]+","+day_times[2]);
			}
		}
		for(Entry<DayOfWeek, String> entry : business_hours.entrySet()) {
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
		this.status = status;
	}
	
	public void setBusinessName(String name) {
		compName = name;
	}
	
	public String getName()
	{
		return compName;
	}
		
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public CustomerDatabase getCustDb()
	{
		return customerDb;
	}
	
	public BookingDatabase getBookingDb()
	{
		return bookingDb;
	}
	
	public AvailabilityDatabase getAvailDb()
	{
		return availDb;
	}
	
	public ServicesDatabase getServDb()
	{
		return servDb;
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
	
	public String getBusString() {
		return busHours;
	}
	
	public void setBusHours(LinkedHashMap<DayOfWeek, String> avail_times) {
		business_hours = avail_times;
	}
	
	public void setBusString(String busHour)
	{
		this.busHours = busHour;
	}
	
	public LinkedHashMap<DayOfWeek, String> getBusHours() {
		return business_hours;
	}
	
	public void clearService() {
		services.clear();
		service_times.clear();
	}
	
	public void addService(String service) {
		services.add(service);
	}
	
	public ArrayList<String> getService() {
		return services;
	}
	
	public String getService(String s) {
		for(String serv : services) {
			if(serv.equals(s)) {
				return serv;
			}
		}
		return null;
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
	public void retrieveDatabaseInfo(CustomerDatabase customerDb, CompanyDatabase companyDb) {
		HashMap<String, HashMap<String,String>> empValues;
		HashMap<String, HashMap<String,String>> custValues;
		HashMap<String, ArrayList<String>> availValues;
		HashMap<String, ArrayList<String>> bookValues;
		HashMap<String, String> serviceTimeDb;
		HashMap<String, String> serviceList;
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
		serviceList = setServList(serviceTimeDb);
		LOGGER.info("Set Service Values");
		menu = new MainController(this, customerDb, companyDb, availDb, bookingDb, servDb);
	}
	
	public MainController getMenu()
	{
		return menu;
	}
	
	public HashMap<DayOfWeek, ArrayList<LocalTime>> setAvailList(HashMap<String, ArrayList<String>> list)
	{
		HashMap<DayOfWeek,ArrayList<LocalTime>> timeMap = new HashMap<DayOfWeek,ArrayList<LocalTime>>();
		String employeeID = null;
		String dayStr;
		String startTimeStr;
		String endTimeStr;
		String compName;
		String key;
		
		for(Map.Entry<String, ArrayList<String>> x: list.entrySet())
		{
			ArrayList<String> infoList = x.getValue();
			key = x.getKey();
			startTimeStr = infoList.get(1);
			endTimeStr = infoList.get(2);
			String[] keyList = key.split(":");
			employeeID = keyList[0];
			dayStr = keyList[1];
			compName = keyList[2];
			String[] startTimeList = startTimeStr.split(":");
			String[] endTimeList = endTimeStr.split(":");
			DayOfWeek day = DayOfWeek.valueOf(dayStr.toUpperCase());
			int startHourInt = Integer.parseInt(startTimeList[0]);
			int startMinInt = Integer.parseInt(startTimeList[1]);
			int endHourInt = Integer.parseInt(endTimeList[0]);
			int endMinInt = Integer.parseInt(endTimeList[1]);
			LocalTime startTime = LocalTime.of(startHourInt, startMinInt);
			LocalTime endTime = LocalTime.of(endHourInt, endMinInt);
			if(this.compName.equals(compName))
			{
				HashMap<String, Employee> empList = getEmployeeList();
				for(Entry<String, Employee> entry: empList.entrySet())
				{
					Employee emp  = entry.getValue();
				}
				Employee emp = getEmployee(employeeID);
				if(emp != null)
				{
					emp.addAvailability(day,startTime,endTime);
					timeMap = emp.getAvailability();
				}
			}
		}
		return timeMap;
	}
	
	public void setCustList(HashMap<String, HashMap<String, String>> map) {
		HashMap<String, Customer> custList = new HashMap<String, Customer>();
		for(Entry<String, HashMap<String,String>> x : map.entrySet()) {
			String[] id = x.getKey().split("-");
			String username = id[0];
			String compName = x.getValue().get("compName");
			String fname = x.getValue().get("fName");
			String lname = x.getValue().get("lName");
			if(this.compName.equals(compName))
			{
				custList.put(username, new Customer(username, compName, fname,lname));
			}
		}
		this.custList = custList;
	}
	
	public HashMap<String,String> setServList(HashMap<String,String> map) {
		HashMap<String,String> serviceList = new HashMap<String, String>();
		if(!services.isEmpty()) {
			services.clear();
		}
		for(Entry<String, String> x : map.entrySet()) {
			String[] key = x.getKey().split(":");
			if(key[0].equals(compName))
			{
				String service = key[1];
				String time = x.getValue();
				serviceList.put(service, time);
				services.add(service);
				addServiceTime(service,Integer.parseInt(time));
			}
		}
		return serviceList;
	}
	
	public ArrayList<Booking> setBookingList(HashMap<String, ArrayList<String>> map){
		ArrayList<Booking> bookList = new ArrayList<Booking>();
		HashMap<String, ArrayList<String>> storedBooking = map;
		String bookingID;
		String compName;
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
			compName = infoList.get(0);
			customerUsername = infoList.get(1);
			dateStr = infoList.get(2);
			timeStr = infoList.get(3);
			employeeID = infoList.get(4);
			serviceStr = infoList.get(5);
			statusStr = infoList.get(6);
			status = getStatus(statusStr);
			service = serviceStr;
			String[] timeList = timeStr.split("-");
			String startTimeStr = timeList[0];
			String endTimeStr = timeList[1];
			date = LocalDate.parse(dateStr);
			startTime = LocalTime.parse(startTimeStr);
			endTime = LocalTime.parse(endTimeStr);
			if(this.compName.equals(compName))
			{
				Booking booking = new Booking(status, bookingID);
				Employee emp = getEmployee(employeeID);
				if(emp != null)
				{
					emp.addBooking(date, startTime, endTime);
					booking.addDetails(date, startTime, endTime, service, emp, customerUsername);
					bookList.add(booking);
				}
				for(Booking book: bookList)
				{
					System.out.println(this.compName + book.getID());
				}
				calendar.setBookingList(bookList);
			}
		}
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
			String[] id = x.getKey().split("-");
			String username = id[0];
			String compName = x.getValue().get("compName");
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
			if(this.compName.equals(compName))
			{
				employList.put(username, new Employee(username, this.compName, fname,lname,service_arraylist));
			}
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
	}*/	public String getFname()
	{
		return owner_fname;
	}
	
	public String getLname()
	{
		return owner_lname;
	}
	
	public String getMobile()
	{
		return mobile;
	}
	
	public String getAddress()
	{
		return address;
	}
	
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
