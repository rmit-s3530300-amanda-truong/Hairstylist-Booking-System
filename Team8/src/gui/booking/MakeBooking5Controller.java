package gui.booking;

import java.io.IOException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import business.Employee;
import business.Employee.Service;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class MakeBooking5Controller {

	private MainController menu;
	
	private String cust_id;
	
	private Service service;
	
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
    
    @FXML
    private void initialize(){
    }

	public void initiate(MainController menu, String cust_id, Service service, Employee employee, LocalDate date, String portal) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
		this.portal = portal;
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb4 = new FXMLLoader(getClass().getResource("MakeBooking4.fxml"));
    	pane = mb4.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking4Controller controller = mb4.getController();
    	controller.initiate(menu, cust_id, service, employee, portal);
	}
	
	@FXML
    void clickMorning(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("MakeBooking6.fxml"));
    	pane = mb6.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking6Controller controller = mb6.getController();
		controller.initiate(menu, cust_id, service, employee, date, "morning", portal);
	}
	
	@FXML
	void clickAfternoon(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("MakeBooking6.fxml"));
    	pane = mb6.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking6Controller controller = mb6.getController();
    	controller.initiate(menu, cust_id, service, employee, date, "afternoon", portal);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(menu);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("../portal/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(menu, cust_id);
		}
    }
}
