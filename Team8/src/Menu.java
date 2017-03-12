import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
	
	private Scanner input;
	ArrayList<String> customerData = new ArrayList<String>
	(Arrays.asList("cust00001"));
	
	//main menu displayed at the start of the program
	public void mainMenu(){
		
		System.out.println("Welcome to abc Hairstylist");
		System.out.println("abc hairstylist is a company for you.");
		System.out.println("----------------------------------------");
		System.out.println("Please select options from below");
		System.out.println("1. Go Customer Portal");
		System.out.println("2. Go Business portal");
		System.out.println("3. End Program");
		System.out.print("Please type your option: ");
		
		//reading user's input
		input = new Scanner(System.in);
		int selection = input.nextInt();
		
		//conditional statement to choose appropriate option
		if(selection == 1){
			System.out.println("---------------------------------------------");
			System.out.println("Welcome to Customer Portal of abc Hairstylist");
			System.out.println("---------------------------------------------");
			customerLoginMenu();
			
		}
		else if(selection == 2){
			System.out.println("---------------------------------------------");
			System.out.println("Welcome to Business Portal of abc Hairstylist");
			System.out.println("---------------------------------------------");
			businessLoginMenu();
		}
		else if(selection == 3){
			System.out.println("You have successfully ended the program.");
			System.exit(0);
		}
		else{
			System.out.println("You have selected an invalid option please choose again.");
			System.out.println("========================================================\n");
			mainMenu();
		}
	}
	
	//customer login menu
	public void customerLoginMenu(){
		System.out.println("Please select options from below");
		System.out.println("1. Login to your existing account");
		System.out.println("2. Sign up and create a new account");
		System.out.println("3. Go back");
		System.out.println("4. End Program");
		
		input = new Scanner(System.in);
		int selection = input.nextInt();
		
		switch(selection){
		case 1:
			customerLogin();
			break;
		case 2:
			registerCustomer();
			break;
		case 3:
			mainMenu();
			break;
		case 4:
			System.out.println("You have successfully ended the program.");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid option. Please choose again");
			customerLoginMenu();
			break;
		}	
	}
		
	//customer registration menu
		public String[] registerCustomer(){
			//calculating the next id
			int id = customerData.size() + 1;
			String username = "cust000"+ id;
			
			/*System.out.printf("Your username will be: %s\n", username);
			System.out.print("Please enter your first name: ");
			String cFname = input.next();
			System.out.print("Please enter your last name: ");
			String cLname = input.next();
			System.out.print("Please enter a password: ");
			String cpassword = input.next();
			System.out.print("Please enter mobile number: ");
			String cMobile = input.next();
			System.out.print("Please enter a address: ");
			String cAddress = input.next();*/
			
			// String[] custInfo = {username, cFname, cLname};
			String[] custInfo = {username, "john", "snow"};
			/*System.out.printf("your username: %s \nyour fullname: %s %s \npassword: %s \nmobile: %s \naddress: %s",
					username,cFname,cLname,cpassword, cMobile, cAddress);*/
			
			return custInfo;
		}
		
		//customer login validate
		public boolean customerLogin(){
			/*System.out.print("Please enter your username: ");
			String bUserName = input.next();
			System.out.print("Please enter your password: ");
			String bPassword = input.next();
			if(bUserName.equals("customer")&&bPassword.equals("customer")){
				customerMenu();
			}*/
			return true;
		}
		
		//customer portal
		public void customerMenu(){
			System.out.println("Welcome to Customer Portal of abc Hairstylist");
			System.out.println("---------------------------------------------");
			System.out.println("1. Book appointment");
			System.out.println("2. View Upcoming appointments");
			System.out.println("3. Cancel upcoming appointment");
			System.out.println("4. View history");
			System.out.println("5. Log out");
			System.out.println("6. End program");
		}
		
		//business login menu
		public void businessLoginMenu(){
			System.out.println("Please select options from below");
			System.out.println("1. Login to your existing account");
			System.out.println("2. Employees Portal");
			System.out.println("3. Go back");
			System.out.println("4. End Program");
			
			input = new Scanner(System.in);
			int selection = input.nextInt();
				
			switch(selection){
			case 1: 
				businessLogin();
				break;
			case 2:
				System.out.println("This is employee portal. Still needs attention");
				break;
			case 3:
				mainMenu();
				break;
			case 4:
				System.out.println("You have sucessfully ended the program.");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option. Please choose again");
				businessLoginMenu();
				break;
			}
		}
		
		//business login validate
		public boolean businessLogin(){
			System.out.print("Please enter your username: ");
			String bUserName = input.next();
			System.out.print("Please enter your password: ");
			String bPassword = input.next();
			if(bUserName.equals("admin")&&bPassword.equals("admin")){
				businessOwnerMenu();
				return true;
			}
			else{
				System.out.println("Invalid Password. Please try again");
				System.out.println("----------------------------------");
				businessLogin();
				return false;
			}
			
		}
		
		//business owner menu
		public void businessOwnerMenu(){
			System.out.println("Welcome to Owner's Portal of abc Hairstylist");
			System.out.println("---------------------------------------------");
			System.out.println("1. Add an employee");
			System.out.println("2. Remove an emplyee");
			System.out.println("3. Accept upcoming appoinments");
			System.out.println("4. View history");
			System.out.println("5. Add available times");
			System.out.println("6. Edit Business summary");
			System.out.println("7. Log out");
			System.out.println("8. End program");
		}
}
