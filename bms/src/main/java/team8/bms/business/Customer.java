package team8.bms.business;

public class Customer {
	String username;
	String fname;
	String lname;
	String compName;
	
	public Customer(String username, String compName, String fname, String lname) {
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.compName = compName;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getFirstName() {
		return fname;
	}
	
	public String getLastName() {
		return lname;
	}
	
	public String getCompName() {
		return compName;
	}
}
