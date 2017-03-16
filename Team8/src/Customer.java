
public class Customer extends Role {
	
	enum Gender {
		male,
		female
	}
	
	private Gender gender;
	
	public Customer(String ID, String firstName, String lastName, String password, Gender gender) {
		super(ID, firstName, lastName, password);
		this.gender = gender;
	}
	
	// TODO: Needs Testing
	public Gender getGender() {
		return gender;
	}
}
