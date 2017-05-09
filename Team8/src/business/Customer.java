package business;

public class Customer {
	String username;
	String fname;
	String lname;
	
	public Customer(String username, String fname, String lname) {
		this.username = username;
		this.fname = fname;
		this.lname = lname;
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
}
