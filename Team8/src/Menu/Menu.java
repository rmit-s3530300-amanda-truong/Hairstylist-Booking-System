package Menu;

import java.util.Scanner;

import Calendar.Calendar;
import Database.CompanyDatabase;
import Database.Database;

public class Menu {
	
	private Scanner input;
	Database db1 = new Database();
	CompanyDatabase db2 = new CompanyDatabase();
	
	//main menu displayed at the start of the program
	public void mainMenu(){
		
		System.out.println("Welcome to abc Hairstylist");
		System.out.println("ABC hairstylist is a company for you.");
		System.out.println("----------------------------------------");
		System.out.println("1. Login to your existing account");
		System.out.println("2. Sign up and create a new account");
		System.out.println("3. End this Program");
		System.out.print("Please select an option from above: ");
		
		input = new Scanner(System.in);
		int selection = input.nextInt();
		
		if(selection == 1){
			//code to authenticate will go here.
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
			System.out.println("Invalid option. Please choose again");
			mainMenu();
		}
	}
	
	public void login(){
		db1.initialise();
		db1.addTest();
		db2.initialise();
		db2.addTest();
		String userName,password;
		
		do{
			System.out.print("Please enter your username: ");
			userName = input.next();
			System.out.print("Please enter your password: ");
			password = input.next();
			
		}while(!authenticate(userName,password));
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
			
			//Username
			System.out.print("Please enter a username: ");
			String cUserName = input.next();
			//checking if the username already exists in database
			result = db1.checkValue(cUserName);
			while(result == true){
				System.out.println("This username is already taken, please enter another: ");
				cUserName = input.next();
				result = db1.checkValue(cUserName);
			}
			
			//firstname
			System.out.print("Please enter your first name: ");
			String cFname = input.next();
					
			//lastname
			System.out.print("Please enter your last name: ");
			String cLname = input.next();
			
			//password
			System.out.print("Please enter a password: ");
			String cPassword = input.next();
			
			
			//Gender
			System.out.print("Please enter Gender(Male or Female): ");
			String cGender = input.next();
			//checking if the Gender is correct
			while(!cGender.matches("Male|Female|male|female|m|f|M|F")){
				System.out.print("Invalid Gender. Please try again.");
				cGender = input.next();
			}
			
			//Mobile Number
			System.out.print("Please enter mobile number: ");
			String cMobile = input.next();
			//checking if the mobile number is valid
			while(!cMobile.matches("04[0-9]*{9}")){
				System.out.print("Invalid mobile number. Please try again.");
				cMobile = input.next();
			}
			
			//address
			input.nextLine(); //this is to consume \n that is added when pressing enter key
			System.out.println("Please enter a address: ");
			String cAddress = input.nextLine();

			//adding user input to database
			db1.addCustInfo(cUserName, cFname, cLname, cPassword, cGender, cMobile, cAddress);
			
			System.out.println("\nSuccessfully registered..");
			customerMenu();
		}

	//customer menu
	private void customerMenu() {
		System.out.println("Welcome back Customer to ABC Hairstylist");
		System.out.println("---------------------------------------------");
		System.out.println("1. View Timeslots");
		System.out.println("2. Log out");
		System.out.println("3. End program");
		System.out.print("Please select an option from above: ");
		
		input = new Scanner(System.in);
		int selection1 = input.nextInt();
		
		if(selection1 == 1){
			Calendar c1 = new Calendar(null, null);
			c1.displayCalendar();
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
		
		input = new Scanner(System.in);
		int selection2 = input.nextInt();
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