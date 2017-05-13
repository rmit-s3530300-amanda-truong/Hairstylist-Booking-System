package gui.booking;

import java.io.IOException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import business.Company;
import business.Employee;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;
import Main.BookingManagementSystem;

public class MakeBooking5Controller {

	private MainController menu;
	private Company comp;
	
	private String cust_id;
	
	private String service;
	
	private Employee employee;
	
	private LocalDate date;
	
	private String portal;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

    @FXML
    private JFXButton viewMorning;
    
    @FXML
    private JFXButton viewAfternoon;
    
    private BookingManagementSystem bms;

	public void initiate(Company comp, String cust_id, String service, Employee employee, LocalDate date, String portal, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
		this.portal = portal;
		this.bms = bms;
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb4 = new FXMLLoader(getClass().getResource("MakeBooking4.fxml"));
    	pane = mb4.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking4Controller controller = mb4.getController();
    	controller.initiate(comp, cust_id, service, employee, portal,bms);
	}
	
	@FXML
    void clickMorning(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("MakeBooking6.fxml"));
    	pane = mb6.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking6Controller controller = mb6.getController();
		controller.initiate(comp, cust_id, service, employee, date, "morning", portal, bms);
	}
	
	@FXML
	void clickAfternoon(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("MakeBooking6.fxml"));
    	pane = mb6.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking6Controller controller = mb6.getController();
    	controller.initiate(comp, cust_id, service, employee, date, "afternoon", portal, bms);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(comp,bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(comp, bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("../portal/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(comp, cust_id, bms);
		}
    }
}
