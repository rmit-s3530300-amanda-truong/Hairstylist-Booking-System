package application;

import java.io.IOException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;

import Business.Employee;
import Business.Employee.Service;
import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class MakeBooking5Controller {

	private Menu menu;
	
	private String cust_id;
	
	private Service service;
	
	private Employee employee;
	
	private LocalDate date;
	
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

	public void initiate(Menu menu, String cust_id, Service service, Employee employee, LocalDate date) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb4 = new FXMLLoader(getClass().getResource("MakeBooking4.fxml"));
    	pane = mb4.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking4Controller controller = mb4.getController();
    	controller.initiate(menu, cust_id, service, employee);
	}
	
	@FXML
    void clickMorning(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("MakeBooking6.fxml"));
    	pane = mb6.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking6Controller controller = mb6.getController();
		controller.initiate(menu, cust_id, service, employee, date, "morning");
	}
	
	@FXML
	void clickAfternoon(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("MakeBooking6.fxml"));
    	pane = mb6.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking6Controller controller = mb6.getController();
    	controller.initiate(menu, cust_id, service, employee, date, "afternoon");
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	CustomerPController controller = bussPortal.getController();
    	controller.initiate(menu);
    }
}
