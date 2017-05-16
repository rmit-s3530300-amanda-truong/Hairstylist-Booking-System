package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import business.Company;
import database.AvailabilityDatabase;
import database.BookingDatabase;
import database.CompanyDatabase;
import database.CustomerDatabase;
import database.ServicesDatabase;
import gui.welcome.PreWelcomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mainController.MainController;

public class BookingManagementSystem extends Application {
	private static final Logger LOGGER = Logger.getLogger("InfoLogging");
	private MainController menu;
	private ArrayList<Company> company_list;
	
	public BookingManagementSystem() {
		company_list = new ArrayList<Company>();
		createCompanyList();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/welcome/PreWelcome.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/gui/stylesheet.css").toExternalForm());
			
			PreWelcomeController controller = loader.getController();
			controller.initiate(this);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Team 8 Appointment Booking Program");
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//TODO: Need a function to pull info from db and fill company_list
	public void createCompanyList()
	{
		HashMap<String, HashMap<String,String>> busValues;
		CustomerDatabase customerDb = new CustomerDatabase();
		CompanyDatabase companyDb = new CompanyDatabase();
		AvailabilityDatabase availDb = new AvailabilityDatabase();
		BookingDatabase bookingDb = new BookingDatabase();
		ServicesDatabase servDb = new ServicesDatabase();
		
		String compName = null;
		String username = null;
		String password = null;
		String owner_fname = null;
		String owner_lname = null;
		String mobile = null;
		String address = null;
		String service = null;
		String busHours = null;
		Boolean startup = true;
		busValues = companyDb.storeBusValues();
		LOGGER.info("Set Business List");
		for(Entry<String, HashMap<String,String>> entry: busValues.entrySet())
		{
			HashMap<String, String> busInfo = entry.getValue();
			compName = entry.getKey();
			username = busInfo.get("username");
			password = busInfo.get("password");
			owner_fname = busInfo.get("fname");
			owner_lname = busInfo.get("lname");
			mobile = busInfo.get("mobile");
			address = busInfo.get("address");
			service = busInfo.get("service");
			busHours = busInfo.get("busHours");
			Company comp = new Company(compName, username, password, owner_fname, owner_lname, mobile, address, service, busHours);
			addCompany(comp);
			if(startup == true)
			{
				comp.retrieveDatabaseInfo(customerDb, companyDb, availDb, bookingDb, servDb);
				LOGGER.info("Retrieved Database Information");
				comp.getCalendar().updateCalendar(comp.getEmployeeList());
				LOGGER.info("Updated Calendar");
				startup = false;
			}
		}
		LOGGER.info("Set Business Values");
	}
	
	public void addCompany(Company company) {
		company_list.add(company);
	}
	
	public ArrayList<Company> getCompanyList() {
		return company_list;
	}
	
	public MainController getMenu() {
		return menu;
	}
	
	public static void main(String[] args) {
		launch(args);
		new BookingManagementSystem();
	}
}
