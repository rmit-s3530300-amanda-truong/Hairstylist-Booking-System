package gui.portal;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import business.Company;
import database.CustomerDatabase;
import gui.booking.MakeBooking2Controller;
import gui.booking_summary.BookingHistoryController;
import gui.booking_summary.UpcomingBookingController;
import gui.calendar.ViewCalendarController;
import gui.login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class CustomerPController {
	
	CustomerDatabase db1 = new CustomerDatabase();
	
	private MainController menu;
	private Company comp;
	private String cust_id;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private Label cust_name;

    @FXML
    private JFXButton cust_nBooking;

    @FXML
    private JFXButton cust_vCalendar;

    @FXML
    private JFXButton cust_uBooking;

    @FXML
    private JFXButton cust_vHistory;

    @FXML
    private JFXButton gotoLogout;
    
    private BookingManagementSystem bms;

	public void initiate(Company comp, String username, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.bms = bms;
		cust_id = username;
		String fullname = db1.getName(cust_id);
		cust_name.setText(fullname.toUpperCase());
	}
	
	@FXML
	void newBooking(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader mb2 = new FXMLLoader(getClass().getResource("../booking/MakeBooking2.fxml"));
    	pane = mb2.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking2Controller controller = mb2.getController();
		controller.initiate(comp, cust_id, "customer", bms);
	}
	
	@FXML
	void viewCalendar(ActionEvent event) throws IOException{
		AnchorPane pane;
		FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("../calendar/ViewCalendar.fxml"));
		pane = viewCalendar.load();
		rootPane.getChildren().setAll(pane);
		ViewCalendarController controller = viewCalendar.getController();
		controller.initiate(comp, cust_id, "customer", bms);
	}
	
	@FXML
	void upcomingBooking(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader up = new FXMLLoader(getClass().getResource("../booking_summary/UpcomingBooking.fxml"));
    	pane = up.load();
    	rootPane.getChildren().setAll(pane);
    	UpcomingBookingController controller = up.getController();
    	System.out.println(cust_id);
		controller.initiate(comp, cust_id, bms);
	}
	
	@FXML
	void viewHistory(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader bh = new FXMLLoader(getClass().getResource("../booking_summary/BookingHistory.fxml"));
    	pane = bh.load();
    	rootPane.getChildren().setAll(pane);
    	BookingHistoryController controller = bh.getController();
		controller.initiate(comp, cust_id, bms);
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
