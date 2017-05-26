package team8.bms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import team8.bms.business.Company;
import team8.bms.database.CompanyDatabase;
import team8.bms.database.CustomerDatabase;
import team8.bms.gui.WelcomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import team8.bms.mainController.MainController;

public class BookingManagementSystem extends Application {
	private static final Logger LOGGER = Logger.getLogger("InfoLogging");
	private MainController menu;
	private ArrayList<Company> company_list;
	private CompanyDatabase companyDb = new CompanyDatabase();
	private CustomerDatabase customerDb = new CustomerDatabase();

	
	public BookingManagementSystem() {
		company_list = new ArrayList<Company>();
		createCompanyList();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Welcome.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
			
			WelcomeController controller = loader.getController();
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
		String compName = null;
		String username = null;
		String password = null;
		String owner_fname = null;
		String owner_lname = null;
		String mobile = null;
		String address = null;
		String service = null;
		String busHours = null;
		String status = null;
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
			status = busInfo.get("status");
			Company comp = new Company(compName, username, password, owner_fname, owner_lname, mobile, address, service, busHours, status);
			addCompany(comp);
			comp.retrieveDatabaseInfo(customerDb, companyDb);
			LOGGER.info("Retrieved Database Information");
			comp.getCalendar().updateCalendar(comp.getEmployeeList());
			LOGGER.info("Updated Calendar");
		}
		LOGGER.info("Set Business Values");
	}
	
	public CompanyDatabase getCompDb()
	{
		return companyDb;
	}
	
	public void addCompany(Company company) {
		company_list.add(company);
	}
	
	public ArrayList<Company> getCompanyList() {
		return company_list;
	}
	
	public MainController getMenu() {
		menu = new MainController(null,customerDb, companyDb, null, null, null);
		return menu;
	}
	
	public static void main(String[] args) {
		launch(args);
		new BookingManagementSystem();
	}

	public Company getCompany(String business) {
		for(Company c: company_list) {
			if(c.getName().equals(business)) {
				return c;
			}
		}
		return null;
	}
}
