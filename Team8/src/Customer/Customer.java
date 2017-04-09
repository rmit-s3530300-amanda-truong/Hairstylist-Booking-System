package AppoinmentProgram;

public class Customer {
	String username;
	String fname;
	String lname;
	String gender;
	
	public Customer(String username, String fname, String lname, String gender) {
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.gender = gender;
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
	
	public String getGender() {
		return gender;
	}
}
