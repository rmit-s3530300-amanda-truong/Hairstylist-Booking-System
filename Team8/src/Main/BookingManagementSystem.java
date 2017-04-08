package Main;
import java.security.KeyStore.Entry;
import java.util.HashMap;

import AppoinmentProgram.Company;
import Database.CompanyDatabase;
import Database.CustomerDatabase;
import Menu.Menu;

public class BookingManagementSystem {
	public static void main(String args[]) {
		CustomerDatabase customerDb = new CustomerDatabase();
		CompanyDatabase companyDb = new CompanyDatabase();
		Company comp = new Company();
		comp.retrieveDatabaseInfo(customerDb, companyDb);
		
		Menu m1 = new Menu(comp, customerDb, companyDb);
		m1.mainMenu();
		
	}
	

}
