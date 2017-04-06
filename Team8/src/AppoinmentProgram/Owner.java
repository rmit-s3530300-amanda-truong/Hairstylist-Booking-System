package AppoinmentProgram;


public class Owner {
	private String ID;
	private String firstName;
	private String lastName;
	private String password;
	
	public Owner(String ID, String firstName, String lastName, String password) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public String getID() {
		return ID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getPassword() {
		return password;
	}
	
}
