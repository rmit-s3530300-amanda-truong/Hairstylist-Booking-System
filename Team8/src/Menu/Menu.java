package Menu;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Business.Company;
import Business.Employee;
import Calendar.Calendar;
import Database.AvailabilityDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;

public class Menu {
	
	private Company comp;
	private CustomerDatabase customerDb;
	private CompanyDatabase companyDb;
	//private AvailabilityDatabase availDb;
	
	public Menu(Company company, CustomerDatabase customerDb, CompanyDatabase companyDb, AvailabilityDatabase availDb){
		comp = company;
		this.companyDb = companyDb;
		this.customerDb = customerDb;
		//this.availDb = availDb;
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
	
//	private void addEmployeeAvailability() {
//		Boolean startTimeValid = false;
//		Boolean endTimeValid = false;
//		Boolean idValid = false;
//		Boolean validDay = false;
//		Boolean bothTimeValid = false;
//		
//		String id = "";
//		LocalTime startTime = null;
//		LocalTime endTime = null;
//		DayOfWeek days = null;
//		
//		while(!idValid)
//		{	
//			System.out.print("Enter Employee ID: ");
//			id = input.nextLine();
//			idValid = idValid(id);
//		}
//		
//		while(!validDay) {
//			System.out.println("Enter Weekday (Monday - Friday): ");
//			String dayString = input.nextLine();
//			int day_int = 0;
//			if(dayString.equals("mon")) {
//				day_int = 1;
//				validDay = true;
//			}
//			else if (dayString.equals("tues")) {
//				day_int = 2;
//				validDay = true;
//			}
//			else if(dayString.equals("wed")) {
//				day_int = 3;
//				validDay = true;
//			}
//			else if(dayString.equals("thurs")) {
//				day_int = 4;
//				validDay = true;
//			}
//			else if(dayString.equals("fri")) {
//				day_int = 5;
//				validDay = true;
//			}
//			else {
//				System.out.println("Wrong");
//			}
//			if(validDay){
//				days = DayOfWeek.of(day_int);
//			}
//		}
//		
//		while(!bothTimeValid)
//		{
//			String[] startTime_split = null;
//			while(!startTimeValid)
//			{
//				System.out.print("Enter Start Time (08:00-16:00): ");
//				String startTime_string = input.nextLine();
//				if(validTime(startTime_string))
//				{
//					startTime_split = startTime_string.split(":");
//					startTime = LocalTime.of(Integer.parseInt(startTime_split[0]), Integer.parseInt(startTime_split[1]));
//					//add to calendar
//					startTimeValid = true;
//				}
//			}
//			
//			while(!endTimeValid)
//			{
//				System.out.print("Enter End Time (08:00-16:00): ");
//				String endTime_string = input.nextLine();
//				if(validTime(endTime_string))
//				{
//					String[] endTime_split = endTime_string.split(":");
//					Boolean status = validEndTime(endTime_split, startTime_split);
//					if(status) {
//						endTime = LocalTime.of(Integer.parseInt(endTime_split[0]), Integer.parseInt(endTime_split[1]));
//						endTimeValid = status;
//						bothTimeValid = status;
//					}
//				}
//			}
//		}
//		updateEmpAvailability(days, startTime, endTime, id);
//		Boolean checkId = availDb.checkValueExists("employeeID",id);
//		Boolean checkDate = availDb.checkValueExists("date",days.toString());
//		if(checkId && checkDate)
//		{
//			availDb.deleteAvail(id, days.toString());
//		}
//		availDb.addAvailabilityInfo(id, Integer.toString(days.getValue()), startTime.toString(), endTime.toString());
//		System.out.println("Available Time Has Been Added");
////		businessMenu();
//	}
	
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
		if(companyDb.checkValueExists("username",id))
		{
			return true;
		}
		else
		{
			System.out.println("Error: Invalid employee ID");
			return false;
		}
	}
	
	public boolean validEndTime(String[] endTime_split, String[] startTime_split) {
		if(Integer.parseInt(startTime_split[0]) == Integer.parseInt(endTime_split[0]))
		{
			if(Integer.parseInt(startTime_split[1]) < Integer.parseInt(endTime_split[1]))
			{
				return true;
			}
			else
			{
				System.out.println("Error: End time must be later than start time");
				return false;
			}
		}
		else if(Integer.parseInt(startTime_split[0]) > Integer.parseInt(endTime_split[0]))
		{
			System.out.println("Error: End time must be later than start time");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Boolean validMonth(String monthString){
		try
		{
			int month = Integer.parseInt(monthString);
			if(month > 0 && month <= 12){
				int current_month = comp.getCalendar().getDate().getMonthValue();
				if(month-current_month <= 1 && month-current_month >= 0) 
				{
					return true;
				} 
				else 
				{
					System.out.println("Error: Must be within a month.");
					return false;
				}
			}
			else
			{
				System.out.println("Error: Must be a valid month");
				return false;
			}
		}
		catch(NumberFormatException e)
		{
			System.out.println("Error: Input must be a number");
			return false;
		}
	}
	
	public Boolean validDay(String dayString, int month) {
		try
		{
			int day = Integer.parseInt(dayString);
			int current_day = comp.getCalendar().getDate().getDayOfMonth();
			int current_month = comp.getCalendar().getDate().getMonthValue();
			YearMonth yearMonthObject = YearMonth.of(2017, month);
			int daysInMonth = yearMonthObject.lengthOfMonth();
			if(day > 0 && day <= daysInMonth) {
				if(month == current_month)
				{
					if(day > current_day)
					{
						return true;
					}
					else
					{
						System.out.println("Error: Can only add availabilty for future dates");
						return false;
					}
				}
				return true;
			} else {
				System.out.println("Error: Must be a valid day");
				return false;
			}
		}
		catch(DateTimeException e)
		{
			System.out.println("Error: Must be a valid day");
			return false;
		}
		catch(NumberFormatException e)
		{
			System.out.println("Error: Input must be a number");
			return false;
		}
	}

//	private void addNewEmployee() {
//		String bFname = null, bLname = null, bMobile = null,
//				bAddress = null, bService = null, bNumber = null, bStreet = null,
//				bSuburb = null, bZip = null, bState = null;
//		boolean fNameValid = false, lNameValid = false, 
//				mobileValid = false, suburbValid = false,zipValid = false;
//		
//		//generating username based on number of employees in database
//		int uname = companyDb.checkEmployees() + 1;
//		String bUserName = "e" + uname;
//		System.out.println("This employee will be registered as: " + bUserName);	
//		
//		//getting first name
//		while(!fNameValid){
//			System.out.print("Please enter employee first name: ");
//			bFname = input.nextLine();
//			if(validName(bFname)){
//				fNameValid = true;
//			}
//		}
//		
//		//getting last name
//		while(!lNameValid){
//			System.out.print("Please enter employee last name: ");
//			bLname = input.nextLine();
//			if(validName(bLname)){
//				lNameValid = true;
//			}
//		}
//		
//		//getting mobile
//		while(!mobileValid){
//			System.out.print("Please enter employee Mobile number: ");
//			bMobile = input.nextLine();
//			if(validMobile(bMobile)){
//				mobileValid = true;
//			}
//		}
//				
//		//getting street number
////		while(!streetNumberValid) {
////			System.out.println("Please enter Employee address.");
////			System.out.print("Please enter street number: ");
////			bNumber = input.nextLine();
////			if(validStreetNumber(bNumber)){
////				streetNumberValid = true;
////			}
////		}
////		
////		//getting street name
////		while(!streetValid){
////			System.out.print("Please enter street name: ");
////			bStreet = input.nextLine();
////			if(validStreetName(bStreet)){
////				streetValid = true;
////			}
////		}
//		
//		//getting suburb name
//		while(!suburbValid){
//			System.out.print("Please enter suburb name: ");
//			bSuburb = input.nextLine();
//			if(validSuburb(bSuburb)){
//				suburbValid = true;
//			}
//		}
//		
//		//getting zip code
//		while(!zipValid) {
//			System.out.print("Please enter zip code: ");
//			bZip = input.nextLine();
//			if(validZip(bZip)){
//				zipValid = true;
//			}				
//		}
//		
////		//getting valid state name
////		while(!stateValid) {
////			System.out.print("Please enter State: ");
////			bState = input.nextLine();
////			if(validState(bState)){
////				stateValid = true;
////			}
////		}
//		
//		//joining the address from user input
//		bAddress = bNumber+ " " + bStreet + "," + bSuburb + ", " + bState + " "+ bZip;
//		bService = "femaleCut, maleCut, femaleDye, maleDye, femalePerm, malePerm, femaleWash, maleWash";
//		
//		//sends user input to the arraylist of services
//		ArrayList<Service> services = new ArrayList<Service>();
//		services.add(Employee.Service.femaleCut);
//		services.add(Employee.Service.maleCut);
//		services.add(Employee.Service.femaleDye);
//		services.add(Employee.Service.maleDye);
//		services.add(Employee.Service.femalePerm);
//		services.add(Employee.Service.malePerm);
//		services.add(Employee.Service.femaleWash);
//		services.add(Employee.Service.maleWash);
//		Employee e1 = new Employee(bUserName, bFname, bLname, services);
//		comp.addEmployee(e1);
//		
//		//sends user input to database
//		companyDb.addBusInfo(bUserName, "ABC", bFname, bLname, null, bMobile, bAddress, bService, "employee");
//		System.out.println("\nEmployee Successfully registered..");
////		businessMenu();
//	}
				
	//error checking for valid username
	public boolean validUname(String uName){
		Matcher matcher;
		Pattern uNamePattern = Pattern.compile("^(?=^.{5,}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$");
		matcher = uNamePattern.matcher(uName);
		Boolean uNameValid = matcher.find();
		if(uName.isEmpty() || !uNameValid) {
			System.out.println("Error: username entered incorrectly.");
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
	
	//error checking for valid first name
	public boolean validName(String name){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^[a-zA-Z-//s]*$");
		matcher = namePattern.matcher(name);
		Boolean firstNameValid = matcher.find();
		if(name.isEmpty() || !firstNameValid) {
			System.out.println("Error: Name entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid password
	public boolean validPassword(String password){
		Matcher matcher;
		Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$)(?=.*[@#$%^&+=]).{6,}$");
		matcher = passwordPattern.matcher(password);
		Boolean passwordValid = matcher.find();
		if(password.isEmpty()) {
			System.out.println("Error: Password cannot be null.");
			return false;
		}
		else if (!passwordValid){
			System.out.println("Error: Password must contain atleast a number, capital letters, atleast one unique "
					+ "symbol and must be atleast 6 characters long.");
			return false;
		}
		else{
			return true;
		}
	}
	
	//error checking for mobile number
	public boolean validMobile(String mobile){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^(?:\\+?(61))? ?(?:\\((?=.*\\)))?(0?[2-57-8])\\)? ?(\\d\\d(?:[- ](?=\\d{3})|(?!\\d\\d[- ]?\\d[- ]))\\d\\d[- ]?\\d[- ]?\\d{3})$");
		matcher = namePattern.matcher(mobile);
		Boolean mobileValid = matcher.find();
		if(mobile.isEmpty() || !mobileValid) {
			System.out.println("Error: Mobile number entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid suburb
	public boolean validSuburb(String suburb) {
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^([a-zA-Z](\\s?))*$");
		matcher = namePattern.matcher(suburb);
		Boolean suburbValid = matcher.find();
		if(suburb.isEmpty() || !suburbValid) {
			System.out.println("Error: Suburb entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid zip code
	public boolean validZip(String zip){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^[0-9]{4}$");
		matcher = namePattern.matcher(zip);
		Boolean zipValid = matcher.find();
		if(zip.isEmpty() || !zipValid) {
			System.out.println("Error: zip code entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid start time
	public boolean validTime(String time)
	{
		Matcher matcher;
		Pattern timePattern = Pattern.compile("^([0][8-9]|[1][0-6])[:]([0|3][0]|[1|4][5])$");
		matcher = timePattern.matcher(time);
		Boolean timeValid = matcher.find();
		if(time.isEmpty() || !timeValid) {
			System.out.println("Error: start time entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
}