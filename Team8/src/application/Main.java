package application;
	
import Business.Company;
import Database.AvailabilityDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;
import Menu.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	private Menu menu;
	
	public Main() {
		CustomerDatabase customerDb = new CustomerDatabase();
		CompanyDatabase companyDb = new CompanyDatabase();
		AvailabilityDatabase availDb = new AvailabilityDatabase();
		Company comp = new Company();
		comp.retrieveDatabaseInfo(customerDb, companyDb,availDb);
		comp.getCalendar().updateCalendar(comp.getEmployeeList());
		menu = new Menu(comp, customerDb, companyDb, availDb);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("welcome.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Team 8 Appoinment Booking Program");
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
