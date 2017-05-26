package team8.bms.gui.portal;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.gui.AddAvailTimeController;
import team8.bms.gui.AddEmpController;
import team8.bms.gui.BookingHistoryController;
import team8.bms.gui.LoginController;
import team8.bms.gui.UpcomingBookingController;
import team8.bms.gui.ViewCalendarController;
import team8.bms.gui.booking.MakeBooking1Controller;

public class BusinessPController {
	
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
	
	@FXML
    private Label logoText;
	
	private BookingManagementSystem bms;
	
	public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
	}
	
	@FXML
	void addEmployee(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addEmp = new FXMLLoader(getClass().getResource("/addEmployee.fxml"));
    	pane = addEmp.load();
    	rootPane.getChildren().setAll(pane);
    	AddEmpController controller = addEmp.getController();
		controller.initiate(comp, bms);
	}
	
	@FXML
	void addAvaiTimes(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addAvail = new FXMLLoader(getClass().getResource("/AddAvailTime.fxml"));
    	pane = addAvail.load();
    	rootPane.getChildren().setAll(pane);
    	AddAvailTimeController controller = addAvail.getController();
		controller.initiate(comp, bms);
		
	}
	
	@FXML
	void makeBooking(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader makeBook = new FXMLLoader(getClass().getResource("/MakeBooking1.fxml"));
    	pane = makeBook.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking1Controller controller = makeBook.getController();
		controller.initiate(comp, bms);
	}
	
	@FXML
	void viewUpcomingBooking(ActionEvent event) throws IOException{	
		AnchorPane pane;
    	FXMLLoader upcomingBooking = new FXMLLoader(getClass().getResource("/UpcomingBooking.fxml"));
    	pane = upcomingBooking.load();
    	rootPane.getChildren().setAll(pane);
    	UpcomingBookingController controller = upcomingBooking.getController();
		controller.initiate(comp,null, bms);
	}
	
	@FXML
	void viewCalendar(ActionEvent event) throws IOException{	
		AnchorPane pane;
		FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("/ViewCalendar.fxml"));
		pane = viewCalendar.load();
		rootPane.getChildren().setAll(pane);
		ViewCalendarController controller = viewCalendar.getController();
		controller.initiate(comp, null, "business", bms);
	}
	
	@FXML
	void viewPastBooking(ActionEvent event) throws IOException{	
		AnchorPane pane;
    	FXMLLoader pastBooking = new FXMLLoader(getClass().getResource("/BookingHistory.fxml"));
    	pane = pastBooking.load();
    	rootPane.getChildren().setAll(pane);
    	BookingHistoryController controller = pastBooking.getController();
		controller.initiate(comp,null, bms);
	}
	
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
	}
}
