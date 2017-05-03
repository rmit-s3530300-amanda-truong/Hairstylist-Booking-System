package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import MainController.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BusinessPController {
	
	private MainController menu;
	
	@FXML
	private AnchorPane rootPane;
	
	 @FXML
	private Label owner_name;

	@FXML
	private JFXButton own_nBEmployee;

	@FXML
	private JFXButton own_nBooking;

	@FXML
	private JFXButton own_vCalendar;

	@FXML
	private JFXButton own_addAvail;

	@FXML
	private JFXButton own_vBookings;

	@FXML
	private JFXButton own_vHistory;

	@FXML
	private JFXButton gotoLogout;
	
	public void initiate(MainController menu) {
		this.menu = menu;
	}
	
	@FXML
	void addEmployee(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addEmp = new FXMLLoader(getClass().getResource("addEmployee.fxml"));
    	pane = addEmp.load();
    	rootPane.getChildren().setAll(pane);
    	AddEmpController controller = addEmp.getController();
		controller.initiate(menu);
	}
	
	@FXML
	void addAvaiTimes(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addAvail = new FXMLLoader(getClass().getResource("AddAvailTime.fxml"));
    	pane = addAvail.load();
    	rootPane.getChildren().setAll(pane);
    	AddAvailTimeController controller = addAvail.getController();
		controller.initiate(menu);
		
	}
	
	@FXML
	void makeBooking(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader makeBook = new FXMLLoader(getClass().getResource("MakeBooking1.fxml"));
    	pane = makeBook.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking1Controller controller = makeBook.getController();
		controller.initiate(menu);
	}
	
	@FXML
	void viewUpcomingBooking(ActionEvent event) throws IOException{	
		AnchorPane pane;
    	FXMLLoader Booking = new FXMLLoader(getClass().getResource("PendingBooking.fxml"));
    	pane = Booking.load();
    	rootPane.getChildren().setAll(pane);
    	PendingBookingController controller = Booking.getController();
		controller.initiate(menu,null);
	}
	
	@FXML
	void viewCalendar(ActionEvent event) throws IOException{	
		AnchorPane pane;
		FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("ViewCalendar.fxml"));
		pane = viewCalendar.load();
		rootPane.getChildren().setAll(pane);
		CalendarController controller = viewCalendar.getController();
		controller.initiate(menu, null, "business");
	}
	
	@FXML
	void viewBooking(ActionEvent event) throws IOException{	
		AnchorPane pane;
    	FXMLLoader pastBooking = new FXMLLoader(getClass().getResource("ViewBooking.fxml"));
    	pane = pastBooking.load();
    	rootPane.getChildren().setAll(pane);
    	ViewBookingController controller = pastBooking.getController();
		controller.initiate(menu,null, "business");
	}
	
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu);
	}
}
