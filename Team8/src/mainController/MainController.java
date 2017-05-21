package mainController;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import business.Company;
import business.Employee;
import calendar.Calendar;
import database.AvailabilityDatabase;
import database.BookingDatabase;
import database.CompanyDatabase;
import database.CustomerDatabase;
import database.ServicesDatabase;

public class MainController {
	
	private Company comp;
	private CustomerDatabase customerDb;
	private CompanyDatabase companyDb;
	private AvailabilityDatabase availDb;
	private BookingDatabase bookDb;
	private ServicesDatabase servDb;
	
	public MainController(Company company, CustomerDatabase customerDb, CompanyDatabase companyDb, AvailabilityDatabase availDb, BookingDatabase bookDb, ServicesDatabase servDb){
		this.companyDb = companyDb;
		this.customerDb = customerDb;
		this.availDb = availDb;
		this.bookDb = bookDb;
		this.servDb = servDb;
		comp = company;
	}
	
	//checks the input against database
	public String authenticate(String uName, String pass, String business){
		if(customerDb.checkLogin(uName,pass,business)){
			return "customer";
		}
		else if(comp.getUsername().equals(uName) && comp.getPassword().equals(pass)){
			return "business";
		}
		else{
			return "false";
		}
	}
	
	public void registerCustomer(String cUname, String company, String cFname, String cLname, 
			String cPassword, String cMobile, String cAddress){
		customerDb.addCustInfo(cUname, company, cFname, cLname, cPassword, cMobile, cAddress);
	}
	
	public Company getCompany() {
		return comp;
	}
	
	public void addEmployeeAvailability(String username, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
		
		Boolean checkId = availDb.checkValueExists("employeeID",username);
		Boolean checkDate = availDb.checkValueExists("day",day.toString());
		if(checkId && checkDate){
			availDb.deleteAvail(username, day.toString());
		}
		availDb.addAvailabilityInfo(username, comp.getName(), day.toString(), startTime.toString(), endTime.toString());
		comp.retrieveDatabaseInfo(customerDb, companyDb);
		comp.getCalendar().updateCalendar(comp.getEmployeeList());
		updateEmpAvailability(day, startTime, endTime, username);
	}
	
	public void updateEmpAvailability(DayOfWeek day, LocalTime startTime, LocalTime endTime, String id) {
		HashMap<String, Employee> employeeList = comp.getEmployeeList();
		Employee e = employeeList.get(id);
		e.addAvailability(day, startTime, endTime);
		employeeList.put(id, e);
		Calendar cal = comp.getCalendar();
		cal.updateCalendar(employeeList);
		comp.setCalendar(cal);
	}
	
	public void addBooking(String id, String custUsername, String service, String empID, String date, String time, String status)
	{
		String[] startAndEnd = time.split("-");
		LocalTime current_time = LocalTime.parse(startAndEnd[0]);
		LocalTime end_time = LocalTime.parse(startAndEnd[1]);
		while(!current_time.equals(end_time)) {
			bookDb.addBooking(date + "/"+ current_time.toString(), comp.getName(), custUsername, service, empID, date, time, status);
			current_time = current_time.plusMinutes(15);
		}
	}
	
	public boolean idValid(String id) {
		if(companyDb.checkValueExists("username",id,"EMPLOYEE")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean validEndTime(String sHour, String eHour, String sMinute, String eMinute){
		if((Integer.parseInt(eHour) - Integer.parseInt(sHour)) == 1){
			if(Integer.parseInt(sMinute) <= Integer.parseInt(eMinute)){
				return true;
			}
			else{
				return false;
			}
		}
		else if((Integer.parseInt(eHour) - Integer.parseInt(sHour)) > 1){
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getEmpUname(){
		int uname = companyDb.checkEmployees(comp.getName()) + 1;
		String username = "e" + uname;
		return username;
	}
	
	public void addEmployee(String username, String fname, String lname, String mobile, String address, String service){
		String password = null;
		String[] serviceList = service.split(", ");
		ArrayList<String> serviceType = new ArrayList<String>();
		for(int i=0; i<serviceList.length; i++)
		{
			serviceType.add(serviceList[i]);
		}
		Employee emp = new Employee(username, comp.getName(), fname, lname, serviceType);
		companyDb.addEmployee(username, comp.getName(), fname, lname, mobile, address, service);		
	}

	//validates the user input against regexs
	public boolean validate(String check, String regex){
		Matcher matcher;
		Pattern pattern = Pattern.compile(regex);
		matcher = pattern.matcher(check);
		Boolean valid = matcher.find();
		if(check.isEmpty() || !valid) {
			return false;
		} 
		else{
			return true;
		}
	}
	
	//checking if the username is unique
	public boolean uniqueUname(String uUname, String comp) {
		if(customerDb.checkUsername(uUname, comp)){
			return false;
		}
		else{
			return true;
		}
	}
	
}