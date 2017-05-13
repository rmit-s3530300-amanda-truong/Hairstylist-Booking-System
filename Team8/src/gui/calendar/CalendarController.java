package gui.calendar;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class CalendarController {

	private MainController menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

    @FXML
    private JFXButton viewMorning;
    
    @FXML
    private JFXButton viewAfternoon;
    
	@FXML
	private JFXButton returnButton;
    
    private String cust_id;
    
    private String portal;
    
    private BookingManagementSystem bms;

	public void initiate(MainController menu, String cust_id, String portal, BookingManagementSystem bms) {
		this.menu = menu;
		this.portal = portal;
		this.cust_id = cust_id;
		this.bms = bms;
	}
	
	//load the calendar for morning times
	@FXML
    void clickMorning(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader view_morning = new FXMLLoader(getClass().getResource("ViewMorningCalendar.fxml"));
    	pane = view_morning.load();
    	rootPane.getChildren().setAll(pane);
    	ViewMorningController controller = view_morning.getController();
		controller.initiate(menu, cust_id, portal, bms);
	}
	
	//load the calendar for afternoon times
	@FXML
	void clickAfternoon(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader view_afternoon = new FXMLLoader(getClass().getResource("ViewAfternoonCalendar.fxml"));
    	pane = view_afternoon.load();
    	rootPane.getChildren().setAll(pane);
    	ViewAfternoonController controller = view_afternoon.getController();
		controller.initiate(menu, cust_id, portal,bms);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu, bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(menu, bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("../portal/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(menu, cust_id, bms);
		}
    }
}
