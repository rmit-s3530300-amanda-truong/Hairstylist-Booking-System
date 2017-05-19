package gui.portal;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import business.Company;
import gui.booking.MakeBooking1Controller;
import gui.booking_summary.BookingHistoryController;
import gui.booking_summary.UpcomingBookingController;
import gui.calendar.ViewCalendarController;
import gui.company.AddAvailTimeController;
import gui.company.AddEmpController;
import gui.login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class BusinessPController {
	
	private MainController menu;
	private Company comp;
	
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
	
	private BookingManagementSystem bms;
	
	public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.bms = bms;
	}
	
	@FXML
	void addEmployee(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addEmp = new FXMLLoader(getClass().getResource("../company/addEmployee.fxml"));
    	pane = addEmp.load();
    	rootPane.getChildren().setAll(pane);
    	AddEmpController controller = addEmp.getController();
		controller.initiate(comp, bms);
	}
	
	@FXML
	void addAvaiTimes(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addAvail = new FXMLLoader(getClass().getResource("../company/AddAvailTime.fxml"));
    	pane = addAvail.load();
    	rootPane.getChildren().setAll(pane);
    	AddAvailTimeController controller = addAvail.getController();
		controller.initiate(comp, bms);
		
	}
	
	@FXML
	void makeBooking(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader makeBook = new FXMLLoader(getClass().getResource("../booking/MakeBooking1.fxml"));
    	pane = makeBook.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking1Controller controller = makeBook.getController();
		controller.initiate(comp, bms);
	}
	
	@FXML
	void viewUpcomingBooking(ActionEvent event) throws IOException{	
		AnchorPane pane;
    	FXMLLoader upcomingBooking = new FXMLLoader(getClass().getResource("../booking_summary/UpcomingBooking.fxml"));
    	pane = upcomingBooking.load();
    	rootPane.getChildren().setAll(pane);
    	UpcomingBookingController controller = upcomingBooking.getController();
		controller.initiate(comp,null, bms);
	}
	
	@FXML
	void viewCalendar(ActionEvent event) throws IOException{	
		AnchorPane pane;
		FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("../calendar/ViewCalendar.fxml"));
		pane = viewCalendar.load();
		rootPane.getChildren().setAll(pane);
		ViewCalendarController controller = viewCalendar.getController();
		controller.initiate(comp, null, "business", bms);
	}
	
	@FXML
	void viewPastBooking(ActionEvent event) throws IOException{	
		AnchorPane pane;
    	FXMLLoader pastBooking = new FXMLLoader(getClass().getResource("../booking_summary/BookingHistory.fxml"));
    	pane = pastBooking.load();
    	rootPane.getChildren().setAll(pane);
    	BookingHistoryController controller = pastBooking.getController();
		controller.initiate(comp,null, bms);
	}
	
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
	}
}
