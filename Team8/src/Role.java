
public abstract class Role {
	private String ID;
	private String firstName;
	private String lastName;
	private String password;
	
	public Role(String ID, String firstName, String lastName, String password) {
		this.ID = ID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	// TODO: Needs Testing
	public String getID() {
		return ID;
	}
	
	// TODO: Needs Testing
	public String getPassword() {
		return password;
	}
}
