package application;
	
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Business.Company;
import Business.Employee;
import Business.Employee.Service;
import Calendar.Booking;
import Database.AvailabilityDatabase;
import Database.BookingDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;
import Database.ServicesDatabase;
import Menu.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Menu menu;
	
	public Main() {
		CustomerDatabase customerDb = new CustomerDatabase();
		CompanyDatabase companyDb = new CompanyDatabase();
		AvailabilityDatabase availDb = new AvailabilityDatabase();
		BookingDatabase bookingDb = new BookingDatabase();
		ServicesDatabase servDb = new ServicesDatabase();
		Company comp = new Company();
		comp.retrieveDatabaseInfo(customerDb, companyDb, availDb, bookingDb, servDb);
		comp.getCalendar().updateCalendar(comp.getEmployeeList());
		servDb.displayTable();
		menu = new Menu(comp, customerDb, companyDb, availDb);
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			mainController controller = loader.getController();
			controller.initiate(menu);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Team 8 Appointment Booking Program");
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public static void main(String[] args) {
		launch(args);
		new Main();
	}
}
