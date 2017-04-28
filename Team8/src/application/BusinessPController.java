package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BusinessPController {
	
	private Menu menu;
	
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
	
	public void initiate(Menu menu) {
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
	}
	
	@FXML
	void makeBooking(ActionEvent event) throws IOException{
	}
	
	@FXML
	void viewUpcomingBooking(ActionEvent event) throws IOException{	
		AnchorPane pane;
    	FXMLLoader upBooking = new FXMLLoader(getClass().getResource("UpcomingBooking.fxml"));
    	pane = upBooking.load();
    	rootPane.getChildren().setAll(pane);
    	UpcomingBookingController controller = upBooking.getController();
		controller.initiate(menu);
	}
	
	@FXML
	void viewCalendar(ActionEvent event) throws IOException{	
		AnchorPane pane;
		FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("ViewCalendar.fxml"));
		pane = viewCalendar.load();
		rootPane.getChildren().setAll(pane);
		CalendarController controller = viewCalendar.getController();
		controller.initiate(menu);
	}
	
	@FXML
	void viewHistory(ActionEvent event) throws IOException{	
	}
	
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
	}
}
