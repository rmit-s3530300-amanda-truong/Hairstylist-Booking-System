package Main;

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
	
	public BookingManagementSystem() {
		CustomerDatabase customerDb = new CustomerDatabase();
		CompanyDatabase companyDb = new CompanyDatabase();
		AvailabilityDatabase availDb = new AvailabilityDatabase();
		BookingDatabase bookingDb = new BookingDatabase();
		ServicesDatabase servDb = new ServicesDatabase();
		Company comp = new Company();
		comp.retrieveDatabaseInfo(customerDb, companyDb, availDb, bookingDb, servDb);
		LOGGER.info("Retrieved Database Information");
		comp.getCalendar().updateCalendar(comp.getEmployeeList());
		LOGGER.info("Updated Calendar");
		menu = new MainController(comp, customerDb, companyDb, availDb, bookingDb, servDb);
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/welcome/PreWelcome.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/gui/stylesheet.css").toExternalForm());
			
			PreWelcomeController controller = loader.getController();
			controller.initiate(menu);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Team 8 Appointment Booking Program");
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public MainController getMenu() {
		return menu;
	}
	
	public static void main(String[] args) {
		launch(args);
		new BookingManagementSystem();
	}
}
