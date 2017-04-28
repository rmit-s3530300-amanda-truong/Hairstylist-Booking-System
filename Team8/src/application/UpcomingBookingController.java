package application;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Calendar.Booking;
import Calendar.Calendar;
import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class UpcomingBookingController {
	
	private Menu menu;
	
	private Company comp;
	
	@FXML
	private TextArea ta;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	public void initialize() {
		ta = new TextArea();
		Calendar cal = comp.getCalendar();
		ArrayList<Booking> list = cal.getFutureBooking();
	}
	
	public String getUpcomingBooking() {
		
		return null;
	}
	
	public void initiate(Menu menu, Company comp) {
		this.menu = menu;
		this.comp = comp;
	}
	
	@FXML
	void Home(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu, comp);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu, comp);
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu, comp);
    }
}
