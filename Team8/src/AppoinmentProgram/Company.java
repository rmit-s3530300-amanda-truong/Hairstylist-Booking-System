package AppoinmentProgram;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Calendar.Calendar;
import Database.CompanyDatabase;

public class Company {
	private String businessName;
	private HashMap<String, Employee> employeeList;
	private String ownerID;
	private Calendar calendar;
	private Scanner input;
		
	CompanyDatabase db2 = new CompanyDatabase();
	
	// TODO: Add in other details that company need here
	public Company(String businessName, String ownerID, Calendar calendar) {
		this.businessName = businessName;
		this.ownerID = ownerID;
		employeeList = new HashMap<String, Employee>();
		this.calendar = calendar;
	}
	
	public void addEmployee() {
//		String ID = employee.getID();
//		employeeList.put(ID, employee);

		/*String userName = "e00" + the calculated length from databse will come here;*/
		System.out.println("This employee will be registered as: e0004" /*+ next number of registration*/);		
		
		//iniliasing the database
		db2.initialise();
		db2.addTest();
		input = new Scanner(System.in);
		String bFname = null, bLname = null, bGender = null, bMobile = null, bAddress = null;
		
		//getting first name input
		boolean fNameValid = false, lNameValid = false, genderValid = false, mobileValid = false;
		while(!fNameValid){
			System.out.print("Please enter employee first name: ");
			bFname = input.nextLine();
			if(validName(bFname)){
				fNameValid = true;
			}
		}
		
		//getting last name input
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
		
		//getting address
		String bNumber = null, bStreet = null, bSuburb = null, bZip = null, bState = null;
		boolean streetNumberValid = false, streetValid = false, suburbValid = false,
				zipValid = false, stateValid = false;
		
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
		
		bAddress = bNumber+ " " + bStreet + "," + bSuburb + ", " + bState + " "+ bZip;
		db2.addBusiness("e0004", "ABC", bFname, bLname, null, bGender, bMobile, bAddress, "employee");
		System.out.println("\nEmployee Successfully registered..");
	}
	
	//error checking for valid first name
	public boolean validName(String bName){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^[a-zA-Z-//s]*$");
		matcher = namePattern.matcher(bName);
		Boolean firstNameValid = matcher.find();
		if(bName.isEmpty() || !firstNameValid) {
			System.out.println("Error: Employee Name entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid gender
	public boolean validGender(String bGender){
		if(!bGender.matches("Male|Female|male|female|m|f|M|F")) {
			System.out.println("Error: Employee Gender entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for mobile number
	public boolean validMobile(String bMobile){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^(04||\\+614)(\\s?)[0-9]{2}(\\s?)[0-9]{3}(\\s?)[0-9]{3}$");
		matcher = namePattern.matcher(bMobile);
		Boolean mobileValid = matcher.find();
		if(bMobile.isEmpty() || !mobileValid) {
			System.out.println("Error: Employee mobile number entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid street number
	public boolean validStreetNumber(String bstreet){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^[0-9-/]*$");
		matcher = namePattern.matcher(bstreet);
		Boolean streetValid = matcher.find();
		if(bstreet.isEmpty() || !streetValid) {
			System.out.println("Error: Street number entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid street name
	public boolean validStreetName(String bStreet) {
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^([a-zA-Z](\\s?))*$");
		matcher = namePattern.matcher(bStreet);
		Boolean streetValid = matcher.find();
		if(bStreet.isEmpty() || !streetValid) {
			System.out.println("Error: Street name entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid suburb
	public boolean validSuburb(String bSuburb) {
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^([a-zA-Z](\\s?))*$");
		matcher = namePattern.matcher(bSuburb);
		Boolean suburbValid = matcher.find();
		if(bSuburb.isEmpty() || !suburbValid) {
			System.out.println("Error: Suburb entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid zip code
	public boolean validZip(String bZip){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^[0-9]{4}$");
		matcher = namePattern.matcher(bZip);
		Boolean zipValid = matcher.find();
		if(bZip.isEmpty() || !zipValid) {
			System.out.println("Error: zip code entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	//error checking for valid state
	public boolean validState(String bState){
		Matcher matcher;
		Pattern namePattern = Pattern.compile("^([a-zA-Z](\\s?))*$");
		matcher = namePattern.matcher(bState);
		Boolean stateValid = matcher.find();
		if(bState.isEmpty() || !stateValid) {
			System.out.println("Error: State entered incorrectly.");
			return false;
		} 
		else{
			return true;
		}
	}
	
	// TODO: Needs Testing
	public void removeEmployee(String ID) {
		
	}
	
	// TODO: Needs Testing
	public Employee getEmployee(String ID) {
		return null;
	}
	
}
