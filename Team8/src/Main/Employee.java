package Main;
import Role;

public class Employee extends Role {
	public enum Service {
		femaleCut,
		maleCut,
		femaleDye,
		maleDye,
		femalePerm,
		malePerm,
		femaleWash,
		maleWash
	}
	
	private Service[] serviceType;
	
	public Employee(String ID, String firstName, String lastName, Service[] serviceType){
		super(ID, firstName, lastName, null);
		this.serviceType = serviceType.clone(); 
	}
	
	// TODO: Needs Testing
	public Service[] getService() {
		return serviceType;
	}
}
