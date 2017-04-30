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

public class Menu {
	
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
		return "true";
	}
	
	public void registerCustomer(String cUname, String cFname, String cLname, 
			String cPassword, String cMobile, String cAddress){
		customerDb.addCustInfo(cUname, cFname, cLname, cPassword, cMobile, cAddress);
	}
	
	public Company getCompany() {
		return comp;
	}

	
//	private void businessMenu() {
//		String selection2 = null;
//		int select2 = 0;
//		boolean selectValid2 = false;
//		System.out.println("Welcome back Owner of ABC Hairstylist");
//		System.out.println("---------------------------------------------");
//		
//		while(!selectValid2){
//			System.out.println("---------------------------------------------");
//			System.out.println("1. Add a new employee");
//			System.out.println("2. Add available Timeslots");
//			System.out.println("3. Booking Summary");
//			System.out.println("4. New Bookings");
//			System.out.println("5. View Employees availability");
//			System.out.println("6. View Calendar");
//			System.out.println("7. Log out");
//			System.out.println("8. End program");
//			System.out.print("Please select an option from above: ");
//			
//			selection2 = input.nextLine();
//			
//			try{
//				select2  = Integer.parseInt(selection2);
//			}
//			catch (NumberFormatException nfe){
//				System.out.println("Error: Invalid Option. Please Choose again");
//				continue;
//			}
//			
//			switch(select2){
//			case 1:
//				System.out.println("------------------");
//				System.out.println("Add a new Employee");
//				System.out.println("------------------");
//				addNewEmployee();
//				break;
//			case 2:
//				System.out.println("-----------------------");
//				System.out.println("Add available TimeSlots");
//				System.out.println("-----------------------");
//				addEmployeeAvailability();
//				break;
//			case 3:
//				System.out.println("---------------");
//				System.out.println("Booking Summary");
//				System.out.println("---------------");
//				if(comp.getCalendar().getBookingSummary().equals("")){
//					System.out.println("No Bookings Available\n");
//				} else {
//					System.out.println(comp.getCalendar().getBookingSummary());	
//				}
//				businessMenu();
//				break;
//			case 4:
//				System.out.println("------------");
//				System.out.println("New Bookings");
//				System.out.println("------------");
//				if(comp.getCalendar().getBookingPendingString().equals("")) {
//					System.out.println("No Bookings Available\n");
//				} else {
//					System.out.println(comp.getCalendar().getBookingPendingString());
//				}
//				businessMenu();
//				break;
//			case 5:
//				System.out.println("---------------------------");
//				System.out.println("View Employees availability");
//				System.out.println("---------------------------");
//				System.out.println(comp.showEmployeeAvailability());
//				businessMenu();
//				break;
//			case 6:
//				System.out.println("-------------");
//				System.out.println("View Calendar");
//				System.out.println("-------------");
//				System.out.println(comp.getCalendar().displayCalendar());
//				businessMenu();
//				break;
//			case 7:
//				System.out.println("You have been redirected to Main Menu.");
//				System.out.println("--------------------------------------");
//				mainMenu();
//				break;
//			case 8:
//				System.out.println("Thanks for using our program.");
//				System.exit(0);
//				break;
//			default:
//				break;
//			}
//		}
//	}
	
	public void addEmployeeAvailability(String username, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
		
		//updateEmpAvailability(day, startTime, endTime, username);
		Boolean checkId = availDb.checkValueExists("employeeID",username);
		Boolean checkDate = availDb.checkValueExists("day",day.toString());
		if(checkId && checkDate){
			availDb.deleteAvail(username, day.toString());
		}
		availDb.addAvailabilityInfo(username, day.toString(), startTime.toString(), endTime.toString());
		comp.retrieveDatabaseInfo(customerDb, companyDb, availDb, bookDb, servDb);
		comp.getCalendar().updateCalendar(comp.getEmployeeList());
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
	
	public boolean idValid(String id) {
		if(companyDb.checkValueExists("username",id)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean validEndTime(String sHour, String eHour, String sMinute, String eMinute){
		if(Integer.parseInt(sHour) == Integer.parseInt(eHour)){
			if(Integer.parseInt(sMinute) < Integer.parseInt(eMinute)){
				return true;
			}
			else{
				return false;
			}
		}
		else if(Integer.parseInt(sHour) > Integer.parseInt(eHour)){
			return false;
		}
		else{
			return true;
		}
	}
	
//	public Boolean validMonth(String monthString){
//		try
//		{
//			int month = Integer.parseInt(monthString);
//			if(month > 0 && month <= 12){
//				int current_month = comp.getCalendar().getDate().getMonthValue();
//				if(month-current_month <= 1 && month-current_month >= 0) 
//				{
//					return true;
//				} 
//				else 
//				{
//					System.out.println("Error: Must be within a month.");
//					return false;
//				}
//			}
//			else
//			{
//				System.out.println("Error: Must be a valid month");
//				return false;
//			}
//		}
//		catch(NumberFormatException e)
//		{
//			System.out.println("Error: Input must be a number");
//			return false;
//		}
//	}
//	
//	public Boolean validDay(String dayString, int month) {
//		try
//		{
//			int day = Integer.parseInt(dayString);
//			int current_day = comp.getCalendar().getDate().getDayOfMonth();
//			int current_month = comp.getCalendar().getDate().getMonthValue();
//			YearMonth yearMonthObject = YearMonth.of(2017, month);
//			int daysInMonth = yearMonthObject.lengthOfMonth();
//			if(day > 0 && day <= daysInMonth) {
//				if(month == current_month)
//				{
//					if(day > current_day)
//					{
//						return true;
//					}
//					else
//					{
//						System.out.println("Error: Can only add availabilty for future dates");
//						return false;
//					}
//				}
//				return true;
//			} else {
//				System.out.println("Error: Must be a valid day");
//				return false;
//			}
//		}
//		catch(DateTimeException e)
//		{
//			System.out.println("Error: Must be a valid day");
//			return false;
//		}
//		catch(NumberFormatException e)
//		{
//			System.out.println("Error: Input must be a number");
//			return false;
//		}
//	}
	
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
			System.out.println("This username is already taken, please enter another: ");
			return false;
		}
		else{
			return true;
		}
	}
	
}