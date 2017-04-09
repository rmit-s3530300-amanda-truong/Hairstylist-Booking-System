package Menu;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import AppoinmentProgram.Company;
import AppoinmentProgram.Employee;
import AppoinmentProgram.Employee.Service;
import Database.CompanyDatabase;
import Database.CustomerDatabase;

public class Menu {
	
	private Scanner input;
	private Company comp;
	private CustomerDatabase customerDb;
	private CompanyDatabase companyDb;
	
	public Menu(Company company, CustomerDatabase customerDb, CompanyDatabase companyDb){
		comp = company;
		this.companyDb = companyDb;
		this.customerDb = customerDb;
	}
	
	public void mainMenu(){
		input = new Scanner(System.in);
		String selection = null;
		boolean selectValid = false;
		int select = 0;
		
		System.out.println("WELCOME TO ABC HAIRSTYLIST");
		System.out.println("--------------------------");
		System.out.println("This is ABC's Appoinment Booking Program\n"
						 + "You can view available time, select your\n"
						 + "desired haristylist. But before that you\n"
						 + "have to login into our program.");
		System.out.println("----------------------------------------");
		
		while(!selectValid){
			System.out.println("1. Login to your existing account");
			System.out.println("2. Sign up and create a new account");
			System.out.println("3. End this Program");
			System.out.print("Please select an option from above: ");
			selection = input.nextLine();
			
			//converting the string input to an int
			try{
				select = Integer.parseInt(selection);
			}
			catch (NumberFormatException nfe){
				System.out.println("Error: Invalid Option. Please Choose again");
				System.out.println("------------------------------------------");
				continue;
			}
			
			if(validOption(select)){
				if(select == 1){
					login();
					selectValid = true;
				}
				else if(select == 2){
					System.out.println("You will be registered as a customer");
					registerCustomer();
					selectValid = true;
				}
				else if(select == 3){
					System.out.print("Thanks for using our program.");
					System.exit(0);
				}
			}
		}
	}
	
	//error checking if the user input is valid for main menu
	public boolean validOption(int select) {
		if(select > 3 || select < 1) {
			System.out.println("Error: Invalid Option. Please Choose again");
			System.out.println("------------------------------------------");
			return false;
		} 
		else{
			return true;
		}
	}

	//login to program
	public void login(){
		
		String userName, password;
		boolean valid = false;
		
		while(!valid) {
			System.out.print("Please enter your username: ");
			userName = input.nextLine();
			if(validLogin(userName)) {
				System.out.print("Please enter your password: ");
				password = input.nextLine();
				if(authenticate(userName,password)) {
					valid = true;
				}
			}
		}		
	}
	
	//error checking if the username is empty
	public boolean validLogin(String uName){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("\\s");
		matcher = namePattern.matcher(uName);
		Boolean uNameValid = matcher.find();
		if(uName.isEmpty() || uNameValid) {
			System.out.println("Error: username entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	public boolean authenticate(String uName, String pass){
		
		if(customerDb.checkLogin(uName,pass) || companyDb.checkLogin(uName,pass)){
			System.out.println("Login Successful");
			//check if the username was customer or business
			if(customerDb.checkLogin(uName,pass)){
				customerMenu();
			}
			else{
				businessMenu();
			}
			return true;
		}
		else{
			System.out.println("Invalid userName or Password. Please Try again.");
			return false;
		}
	}
	
	private void registerCustomer(){
		String cUname = null, cFname = null, cLname = null, cPassword = null,
				cGender = null, cMobile = null, cAddress = null;
		boolean userNameValid = false, fNameValid = false, lNameValid = false,
				passwordValid = false, genderValid = false, mobileValid = false;
		
		//getting username input
		while(!userNameValid){
			System.out.print("Please enter a username: ");
			cUname = input.nextLine();
			if(validUname(cUname) && uniqueUname(cUname)){
				userNameValid = true;
			}
		}
		
		//getting first name input
		while(!fNameValid){
			System.out.print("Please enter your first name: ");
			cFname = input.nextLine();
			if(validName(cFname)){
				fNameValid = true;
			}
		}
		
		//getting last name input
		while(!lNameValid){
			System.out.print("Please enter your last name: ");
			cLname = input.nextLine();
			if(validName(cLname)){
				lNameValid = true;
			}
		}
		
		//getting password input
		while(!passwordValid){
			System.out.print("Please enter a password: ");
			cPassword = input.nextLine();
			if(validPassword(cPassword)){
				passwordValid = true;
			}
		}
		
		//getting gender input
		while(!genderValid){
			System.out.print("Please enter your Gender(male or female): ");
			cGender = input.nextLine();
			if(validGender(cGender)){
				genderValid = true;
			}
		}
		
		//getting mobile input
		while(!mobileValid){
			System.out.print("Please enter your Mobile number: ");
			cMobile = input.nextLine();
			if(validMobile(cMobile)){
				mobileValid = true;
			}
		}
		
		//getting address input
		String cNumber = null, cStreet = null, cSuburb = null, cZip = null, cState = null;
		boolean streetNumberValid = false, streetValid = false, suburbValid = false,
				zipValid = false, stateValid = false;
		
		//getting street number input
		while(!streetNumberValid) {
			System.out.println("Please enter your address below.");
			System.out.print("Please enter street number: ");
			cNumber = input.nextLine();
			if(validStreetNumber(cNumber)){
				streetNumberValid = true;
			}
		}
		
		//getting street name input
		while(!streetValid){
			System.out.print("Please enter street name: ");
			cStreet = input.nextLine();
			if(validStreetName(cStreet)){
				streetValid = true;
			}
		}
		
		//getting suburb name input
		while(!suburbValid){
			System.out.print("Please enter suburb name: ");
			cSuburb = input.nextLine();
			if(validSuburb(cSuburb)){
				suburbValid = true;
			}
		}
		
		//getting zip code input
		while(!zipValid) {
			System.out.print("Please enter zip code: ");
			cZip = input.nextLine();
			if(validZip(cZip)){
				zipValid = true;
			}				
		}
		
		//getting valid state name input
		while(!stateValid) {
			System.out.print("Please enter State: ");
			cState = input.nextLine();
			if(validState(cState)){
				stateValid = true;
			}
		}
		//joining the address from user input
		cAddress = cNumber+ " " + cStreet + "," + cSuburb + ", " + cState + " "+ cZip;
		
		//adding user input to database
		customerDb.addCustInfo(cUname, cFname, cLname, cPassword, cGender, cMobile, cAddress);
		System.out.println("\nSuccessfully registered..");
		customerMenu();
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

	private void customerMenu() {
		String selection1 = null;
		int select1 = 0;
		boolean selectValid1 = false;
		
		System.out.println("Welcome to Customer Portal to ABC Hairstylist");
		System.out.println("---------------------------------------------");
		
		while(!selectValid1){
			System.out.println("1. View Timeslots");
			System.out.println("2. Log out");
			System.out.println("3. End program");
			System.out.print("Please select an option from above: ");
			selection1 = input.nextLine();
			try{
				select1  = Integer.parseInt(selection1);
			}
			catch (NumberFormatException nfe){
				System.out.println("Error: Invalid Option. Please Choose again");
				System.out.println("------------------------------------------");
				continue;
			}
			
			if(validOption(select1)){
				if(select1 == 1){
					System.out.println("Calendar Display");
					System.out.println(comp.getCalendar().displayCalendar());
				}
				else if(select1 == 2){
					System.out.println("You have been redirected to Main Menu.");
					mainMenu();
				}
				else if(select1 == 3){
					System.out.print("Thanks for using our program.");
					System.exit(0);
				}
			}
		}
	}
	
	private void businessMenu() {
		String selection2 = null;
		int select2 = 0;
		boolean selectValid2 = false;
		System.out.println("Welcome back Owner of ABC Hairstylist");
		System.out.println("---------------------------------------------");
		
		while(!selectValid2){
			System.out.println("---------------------------------------------");
			System.out.println("1. Add a new employee");
			System.out.println("2. Add available Timeslots");
			System.out.println("3. Booking Summary");
			System.out.println("4. Accept/Decline Bookings");
			System.out.println("5. View Employees availability");
			System.out.println("6. Log out");
			System.out.println("7. End program");
			System.out.print("Please select an option from above: ");
			
			selection2 = input.nextLine();
			
			try{
				select2  = Integer.parseInt(selection2);
			}
			catch (NumberFormatException nfe){
				System.out.println("Error: Invalid Option. Please Choose again");
				continue;
			}
			
			switch(select2){
			case 1:
				System.out.println("------------------");
				System.out.println("Add a new Employee");
				System.out.println("------------------");
				addNewEmployee();
				break;
			case 2:
				System.out.println("-----------------------");
				System.out.println("Add available TimeSlots");
				System.out.println("-----------------------");
				addEmployeeAvailability();
				break;
			case 3:
				System.out.println("---------------");
				System.out.println("Booking Summary");
				System.out.println("---------------");
				System.out.println(comp.getCalendar().getBookingSummary());
				businessMenu();
				break;
			case 4:
				System.out.println("-----------------------");
				System.out.println("Accept/Decline Bookings");
				System.out.println("-----------------------");
				updateBooking();
				break;
			case 5:
				System.out.println("---------------------------");
				System.out.println("View Employees availability");
				System.out.println("---------------------------");
				System.out.println(comp.getCalendar().displayCalendar());
				businessMenu();
				break;
			case 6:
				System.out.println("You have been redirected to Main Menu.");
				System.out.println("--------------------------------------");
				mainMenu();
				break;
			case 7:
				System.out.println("Thanks for using our program.");
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}
	
	private void updateBooking() {
		// Enter booking ID
		// comp.getCalendar().containsBooking(String ID);
		// if true continue...
		// press A to accept 
		// comp.getCalendar().acceptBooking(String ID);
		
		// press D to decline
		// comp.getCalendar().acceptBooking(String ID);
		// businessMenu();
	}
	
	private void addEmployeeAvailability() {
		System.out.print("Enter Employee ID: ");
		String id = input.nextLine();
		if(companyDb.checkValueExists("username",id));
		
		System.out.print("Enter Month of the Date (MM): ");
		Boolean validMonth = false;
		int month = input.nextInt();
		if(month > 0 && month <= 12){
			int current_month = comp.getCalendar().getDate().getMonthValue();
			if(month-current_month <= 1 && month-current_month >= 0) {
				validMonth = true;
			} else {
				System.out.println(">> Error: Must be within a month.");
			}
		}
		
		System.out.print("Enter Day of the Date (DD): ");
		Boolean validDay = false;
		int day = input.nextInt();
		int current_day = comp.getCalendar().getDate().getDayOfMonth();
		YearMonth yearMonthObject = YearMonth.of(2017, month);
		int daysInMonth = yearMonthObject.lengthOfMonth();
		if(day > 0 && day <= daysInMonth) {
			validDay = true;
		} else {
			System.out.println(">> Error: Must be a valid day");
		}
		
		// add date and month
		LocalDate date = LocalDate.of(2017, month, day);
		
		System.out.print("Enter Start Time (eg. 24:00): ");
		String startTime_string = input.nextLine();
		String[] startTime_split = startTime_string.split(":");
		//error check
		LocalTime startTime = LocalTime.of(Integer.parseInt(startTime_split[0]), Integer.parseInt(startTime_split[1]));
		
		System.out.print("Enter End Time (eg. 24:00): ");
		String endTime_string = input.nextLine();
		String[] endTime_split = endTime_string.split(":");
		//error check
		LocalTime endTime = LocalTime.of(Integer.parseInt(endTime_split[0]), Integer.parseInt(endTime_split[1]));
		
		//to LocalTime
		System.out.println("Available Time Has Been Added");
		businessMenu();
	}

	private void addNewEmployee() {
		String bFname = null, bLname = null, bGender = null, bMobile = null,
				bAddress = null, bService = null, bNumber = null, bStreet = null,
				bSuburb = null, bZip = null, bState = null;
		boolean fNameValid = false, lNameValid = false, genderValid = false, 
				mobileValid = false, streetNumberValid = false, streetValid = false, 
				suburbValid = false,zipValid = false, stateValid = false;
		
		//generating username based on number of employees in database
		int uname = companyDb.checkEmployees() + 1;
		String bUserName = "e" + uname;
		System.out.println("This employee will be registered as: " + bUserName);	
		
		//getting first name
		while(!fNameValid){
			System.out.print("Please enter employee first name: ");
			bFname = input.nextLine();
			if(validName(bFname)){
				fNameValid = true;
			}
		}
		
		//getting last name
		while(!lNameValid){
			System.out.print("Please enter employee last name: ");
			bLname = input.nextLine();
			if(validName(bLname)){
				lNameValid = true;
			}
		}
		
		//getting gender
		while(!genderValid){
			System.out.print("Please enter employee Gender(male or female): ");
			bGender = input.nextLine();
			if(validGender(bGender)){
				genderValid = true;
			}
		}
		
		//getting mobile
		while(!mobileValid){
			System.out.print("Please enter employee Mobile number: ");
			bMobile = input.nextLine();
			if(validMobile(bMobile)){
				mobileValid = true;
			}
		}
				
		//getting street number
		while(!streetNumberValid) {
			System.out.println("Please enter Employee address.");
			System.out.print("Please enter street number: ");
			bNumber = input.nextLine();
			if(validStreetNumber(bNumber)){
				streetNumberValid = true;
			}
		}
		
		//getting street name
		while(!streetValid){
			System.out.print("Please enter street name: ");
			bStreet = input.nextLine();
			if(validStreetName(bStreet)){
				streetValid = true;
			}
		}
		
		//getting suburb name
		while(!suburbValid){
			System.out.print("Please enter suburb name: ");
			bSuburb = input.nextLine();
			if(validSuburb(bSuburb)){
				suburbValid = true;
			}
		}
		
		//getting zip code
		while(!zipValid) {
			System.out.print("Please enter zip code: ");
			bZip = input.nextLine();
			if(validZip(bZip)){
				zipValid = true;
			}				
		}
		
		//getting valid state name
		while(!stateValid) {
			System.out.print("Please enter State: ");
			bState = input.nextLine();
			if(validState(bState)){
				stateValid = true;
			}
		}
		
		//joining the address from user input
		bAddress = bNumber+ " " + bStreet + "," + bSuburb + ", " + bState + " "+ bZip;
		bService = "femaleCut, maleCut, femaleDye, maleDye, femalePerm, malePerm, femaleWash, maleWash";
		
		//sends user input to the arraylist of services
		ArrayList<Service> services = new ArrayList<Service>();
		services.add(Employee.Service.femaleCut);
		services.add(Employee.Service.maleCut);
		services.add(Employee.Service.femaleDye);
		services.add(Employee.Service.maleDye);
		services.add(Employee.Service.femalePerm);
		services.add(Employee.Service.malePerm);
		services.add(Employee.Service.femaleWash);
		services.add(Employee.Service.maleWash);
		Employee e1 = new Employee(bUserName, bFname, bLname, services);
		comp.addEmployee(e1);
		
		//sends user input to database
		companyDb.addBusInfo(bUserName, "ABC", bFname, bLname, null, bGender, bMobile, bAddress, bService, "employee");
		System.out.println("\nEmployee Successfully registered..");
		businessMenu();
	}
				
	//error checking for valid username
	public boolean validUname(String uName){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^[a-zA-Z0-9._-]*$");
		matcher = namePattern.matcher(uName);
		Boolean uNameValid = matcher.find();
		if(uName.isEmpty() || !uNameValid) {
			System.out.println("Error: username entered incorrectly.");
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
		if(password.isEmpty()) {
			System.out.println("Error: Password cannot be null.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid gender
	public boolean validGender(String gender){
		if(!gender.matches("male|female")) {
			System.out.println("Error: Gender entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for mobile number
	public boolean validMobile(String mobile){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^(04||\\+614)(\\s?)[0-9]{2}(\\s?)[0-9]{3}(\\s?)[0-9]{3}$");
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
	
	//error checking for valid street number
	public boolean validStreetNumber(String sNumber){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^[0-9-/]*$");
		matcher = namePattern.matcher(sNumber);
		Boolean streetValid = matcher.find();
		if(sNumber.isEmpty() || !streetValid) {
			System.out.println("Error: Street number entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid street name
	public boolean validStreetName(String street) {
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^([a-zA-Z](\\s?))*$");
		matcher = namePattern.matcher(street);
		Boolean streetValid = matcher.find();
		if(street.isEmpty() || !streetValid) {
			System.out.println("Error: Street name entered incorrectly.");
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
	
	//error checking for valid state
	public boolean validState(String state){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^([a-zA-Z](\\s?))*$");
		matcher = namePattern.matcher(state);
		Boolean stateValid = matcher.find();
		if(state.isEmpty() || !stateValid) {
			System.out.println("Error: State entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
}