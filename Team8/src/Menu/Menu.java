package Menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Database.CompanyDatabase;
import Database.Database;

public class Menu {
	
	private Scanner input;
	Database db1 = new Database();
	CompanyDatabase db2 = new CompanyDatabase();
	
	//main menu displayed at the start of the program
public void mainMenu(){
		
		input = new Scanner(System.in);
		int selection = 0;
		
		System.out.println("Welcome to abc Hairstylist");
		System.out.println("ABC hairstylist is a company for you.");
		System.out.println("----------------------------------------");
		System.out.println("1. Login to your existing account");
		System.out.println("2. Sign up and create a new account");
		System.out.println("3. End this Program");
		System.out.print("Please select an option from above: ");

		try{
			selection = input.nextInt();
		}
		catch(InputMismatchException imme) {
			System.out.print("Invalid Option. Please choose again \n");
			System.out.println("----------------------------------------\n");
			input.next();
			mainMenu();
		}		
		
		if(selection == 1){
			login();
		}
		else if(selection == 2){
			System.out.println("You will be registered as a customer");
			registerCustomer();
		}
		else if(selection == 3){
			System.out.println("Thanks for using our program.");
			System.exit(0);
		}
		else{	
			System.out.print("Invalid Option. Please choose again \n");
			System.out.println("----------------------------------------\n");
			mainMenu();
		}
	}
	
	public void login(){
		db1.initialise();
		db1.addTest();
		db2.initialise();
		db2.addTest();
		String userName, password;
		Pattern pattern = Pattern.compile("\\s");
		boolean valid = false;
		if(input.hasNextLine()){
			input.nextLine();
		}
		while(!valid) {
			
			System.out.print("Please enter your username: ");
			userName = input.nextLine();
			Matcher matcher = pattern.matcher(userName);
			boolean whiteSpaceFound = matcher.find();
			if(!userName.isEmpty() && !whiteSpaceFound) {
				System.out.print("Please enter your password: ");
				password = input.nextLine();
				if(authenticate(userName,password)) {
					valid = true;
				}
				
			} else {
				System.out.println("Error: Username has been entered incorrectly.");
			}
		}
		
	}
	
	//checks if the username,password is found in both database
	public boolean authenticate(String uName, String pass){
		
		if(db1.checkLogin(uName,pass) || db2.checkLogin(uName,pass)){
			System.out.println("Login Successfull");
			//check if the username was customer or business
			if(db1.checkLogin(uName,pass)){
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
	
	//customer registration menu
	public void registerCustomer(){
		
		Boolean result;
		//iniliasing the database
		db1.initialise();
		db1.addTest();
		Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9._-]*$");
		Pattern namePattern = Pattern.compile("^[a-zA-Z-//s]*$");
		Matcher matcher;
		Boolean usernameValid = false;
		Boolean passwordValid = false;
		Boolean nameValid = false;
		String cUsername = null, cFname = null, cLname = null, cPassword = null, cGender = null, cMobile = null;
		
		if(input.hasNextLine()) {
			input.nextLine();
		}
		while(!usernameValid){
			//Username
			System.out.print("Please enter a username: ");
			cUsername = input.nextLine();
			matcher = usernamePattern.matcher(cUsername);
			Boolean status = matcher.find();
			if(cUsername.isEmpty() || cUsername.contains(" ") || !status ) {
				System.out.println("Error: Username is entered incorrectly.");
			} else {
				result = db1.checkExists("username",cUsername);
				if(result) {
					System.out.println("This username is already taken, please enter another: ");
				} else {
					usernameValid = true;
				}
			}
		}
		while(!nameValid){
			//firstname
			
			System.out.print("Please enter your first name: ");
			cFname = input.nextLine();
			matcher = namePattern.matcher(cFname);
			Boolean firstNameValid = matcher.find();
			if(cFname.isEmpty() || !firstNameValid) {
				System.out.println("Error: First name entered incorrectly.");
			} else {
				while(!nameValid){
					System.out.print("Please enter your last name: ");
					cLname = input.nextLine();
					matcher = namePattern.matcher(cLname);
					Boolean lnameValid = matcher.find();
					if(cLname.isEmpty() || !lnameValid) {
						System.out.println("Error: Last name entered incorrectly.");
					} else {
						nameValid = true;
					}
				}
			}
		}
		
		while(!passwordValid){
			//password
			System.out.print("Please enter a password: ");
			cPassword = input.nextLine();
			if(cPassword.isEmpty()) {
				System.out.println("Error: Password cannot be null.");
			} else {
				passwordValid = true;
			}
			
		}
			
		Boolean genderValid = false;		
		//Gender
		while(!genderValid){
		System.out.print("Please enter Gender(Male or Female): ");
		cGender = input.nextLine();
		//checking if the Gender is correct
			if(!cGender.matches("Male|Female|male|female|m|f|M|F")){
				System.out.println("Invalid Gender. Please try again.");
			} else {
				genderValid = true;
			}
		}
		Boolean numberValid = false;	
		//Mobile Number
		while(!numberValid){
			System.out.print("Please enter mobile number: ");
			cMobile = input.nextLine();
			//checking if the mobile number is valid
			Pattern p1 = Pattern.compile("^(04||\\+614)(\\s?)[0-9]{2}(\\s?)[0-9]{3}(\\s?)[0-9]{3}$");
			matcher = p1.matcher(cMobile);
			Boolean status1 = matcher.find();
			if(!status1){
				System.out.println("Invalid mobile number. Please try again.");
			} else {
				numberValid = true;
			}
		}
			
		//address
		Boolean addressNValid = false;
		String cNumber = null, cStreet = null, cSuburb = null,cZip = null, cState = null;
		while(!addressNValid) {
			System.out.print("Please enter address number: ");
			cNumber = input.nextLine();
			if(!cNumber.matches("^[0-9-/]*$") || cNumber.isEmpty()) {
				System.out.println("Error: Address Number entered incorrectly");
			} else {
				addressNValid=true;
			}
		}
		
		Boolean streetValid = false;
		while(!streetValid){
				System.out.print("Please enter street name: ");
				cStreet = input.nextLine();
				if(!cStreet.matches("^([a-zA-Z](\\s?))*$") || cStreet.isEmpty()) {
					System.out.println("Error: Address Street entered incorrectly");
				} else {
					streetValid = true;
				}
		}
		
		Boolean suburbValid = false;
		while(!suburbValid){
			System.out.print("Please enter suburb: ");
			cSuburb = input.nextLine();
			if(!cSuburb.matches("^([a-zA-Z](\\s?))*$") || cSuburb.isEmpty()) {
				System.out.println("Error: Address Suburb entered incorrectly");
			} else {
				suburbValid = true;
			}
		}
		
		Boolean zipValid = false;
		while(!zipValid) {
			System.out.print("Please enter zip code: ");
			cZip = input.nextLine();
			if(!cZip.matches("^[0-9]{4}$")) {
				System.out.println("Error: Zip code entered incorrectly");
			} else {
				zipValid = true;		
			}				
		}
		
		Boolean stateValid = false;
		while(!stateValid) {
			System.out.print("Please enter State: ");
			cState = input.nextLine();
			if(!cState.matches("^([a-zA-Z](\\s?))*$") || cState.isEmpty()) {
				System.out.println("Error: State has been entered incorrectly");
			} else {
				stateValid = true;
			}
		}
		String cAddress = cNumber+cStreet+cSuburb+cZip +cState;
		
		//adding user input to database
		db1.addCustInfo(cUsername, cFname, cLname, cPassword, cGender, cMobile, cAddress);
		
		System.out.println("\nSuccessfully registered..");
		customerMenu();
	}

	//customer menu
	private void customerMenu() {
		int selection1 = 0;
		System.out.println("Welcome back Customer to ABC Hairstylist");
		System.out.println("---------------------------------------------");
		System.out.println("1. View Timeslots");
		System.out.println("2. Log out");
		System.out.println("3. End program");
		System.out.print("Please select an option from above: ");
		
		try{
			selection1 = input.nextInt();
		}
		catch(InputMismatchException imme){
			System.out.print("Invalid Option. Please choose again \n");
			System.out.println("----------------------------------------\n");
			input.next();
			customerMenu();
		}
			
		if(selection1 == 1){
			System.out.println("Calendar will be displayed here.");
		}
		else if(selection1 == 2){
			//save everything in database
			System.out.println("You have been redirected to Main Menu.");
			mainMenu();
		}
		else if(selection1 == 3){
			//save everything in database
			System.out.println("Thanks for using our program.");
			System.exit(0);
		}
	}
	
	
	//business menu
	private void businessMenu() {
		
		int selection2 = 0;
		
		System.out.println("Welcome back Owner of ABC Hairstylist");
		System.out.println("---------------------------------------------");
		System.out.println("1. Add an employee");
		System.out.println("2. Remove an employee");
		System.out.println("3. Add available Timeslots");
		System.out.println("4. Booking Summary");
		System.out.println("5. Accept/Decline Bookings");
		System.out.println("6. View Employees availability");
		System.out.println("7. Log out");
		System.out.println("8. End program");
		System.out.print("Please select an option from above: ");
		
		try{
			selection2 = input.nextInt();
		}
		catch(InputMismatchException imme){
			System.out.print("Invalid Option. Please choose again \n");
			System.out.println("----------------------------------------\n");
			input.next();
			businessMenu();
		}
		
		switch(selection2){
		case 1:
			System.out.println("------------------");
			System.out.println("Add a new Employee");
			System.out.println("------------------");
			break;
		case 2:
			System.out.println("------------------");
			System.out.println("Remove an Employee");
			System.out.println("------------------");
			break;
		case 3:
			System.out.println("-----------------------");
			System.out.println("Add available TimeSlots");
			System.out.println("-----------------------");
			break;
		case 4:
			System.out.println("---------------");
			System.out.println("Booking Summary");
			System.out.println("---------------");
			break;
		case 5:
			System.out.println("-----------------------");
			System.out.println("Accept/Decline Bookings");
			System.out.println("-----------------------");
			break;
		case 6:
			System.out.println("---------------------------");
			System.out.println("View Employees availability");
			System.out.println("---------------------------");
			break;
		case 7:
			//save everything in database
			System.out.println("You have been redirected to Main Menu.");
			mainMenu();
			break;
		case 8:
			//save everything in database
			System.out.println("Thanks for using our program.");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid Option. Please choose again");
			businessMenu();
			break;
		}
	}
}