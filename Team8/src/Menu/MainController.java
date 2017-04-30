package Menu;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Business.Company;
import Business.Employee;
import Calendar.Calendar;
import Database.AvailabilityDatabase;
import Database.BookingDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;
import Database.ServicesDatabase;

public class MainController {
	
	private Company comp;
	private CustomerDatabase customerDb;
	private CompanyDatabase companyDb;
	private AvailabilityDatabase availDb;
	private BookingDatabase bookDb;
	private ServicesDatabase servDb;
	
	public Menu(Company company, CustomerDatabase customerDb, CompanyDatabase companyDb, AvailabilityDatabase availDb, BookingDatabase bookDb, ServicesDatabase servDb){
		comp = company;
		this.companyDb = companyDb;
		this.customerDb = customerDb;
		this.availDb = availDb;
		this.bookDb = bookDb;
		this.servDb = servDb;
	}
		
	public String authenticate(String uName, String pass){
		if(customerDb.checkLogin(uName,pass)){
			return "customer";
		}
		else if(companyDb.checkLogin(uName,pass)){
			return "business";
		}
		else{
			return "false";
		}
	}
	
	public void registerCustomer(String cUname, String cFname, String cLname, 
			String cPassword, String cMobile, String cAddress){
		customerDb.addCustInfo(cUname, cFname, cLname, cPassword, cMobile, cAddress);
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
		availDb.addAvailabilityInfo(username, day.toString(), startTime.toString(), endTime.toString());
		comp.retrieveDatabaseInfo(customerDb, companyDb, availDb, bookDb, servDb);
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
		bookDb.addBooking(id, custUsername, service, empID, date, time, status);
	}
	
	public boolean idValid(String id) {
		if(companyDb.checkValueExists("username",id)){
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
		int uname = companyDb.checkEmployees() + 1;
		String username = "e" + uname;
		return username;
	}
	
	public void addEmployee(String username, String fname, String lname, String mobile, String address, String service){
		String password = null;
		String company = "ABC";
		String status = "employee";
		companyDb.addBusInfo(username, company, fname, lname, password, mobile, address, service, status);		
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
	public boolean uniqueUname(String uUname) {
		if(customerDb.checkValueExists("username",uUname)){
			return false;
		}
		else{
			return true;
		}
	}
	
}