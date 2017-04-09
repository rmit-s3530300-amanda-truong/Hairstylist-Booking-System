package Main;

import AppoinmentProgram.Company;
import Database.AvailabilityDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;
import Menu.Menu;

public class BookingManagementSystem {
	public static void main(String args[]) {
		CustomerDatabase customerDb = new CustomerDatabase();
		CompanyDatabase companyDb = new CompanyDatabase();
		AvailabilityDatabase availDb = new AvailabilityDatabase();
		Company comp = new Company();
		comp.retrieveDatabaseInfo(customerDb, companyDb,availDb);
		comp.getCalendar().updateCalendar(comp.getEmployeeList());
		
		Menu m1 = new Menu(comp, customerDb, companyDb, availDb);
		m1.mainMenu();
	}
	

}
