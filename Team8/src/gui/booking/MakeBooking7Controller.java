package gui.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import business.Company;
import business.Employee;
import business.Employee.Service;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import mainController.MainController;

public class MakeBooking7Controller {
	private MainController menu;
	
	private Company comp;
	
	private String cust_id;
	
	private Service service;
	
	private Employee employee;
	
	private LocalDate date;
	
	private LocalTime time;
	
	private LocalTime end_time;
	
	private String portal;
	
	@FXML
	private Label invalid;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

	public void initiate(MainController menu, String cust_id, Service service, Employee employee, LocalDate date, LocalTime time, String portal) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
		this.time = time;
		this.portal = portal;
		this.comp = menu.getCompany();
		
		Label id_label = new Label();
		Label service_label = new Label();
		Label emp_label = new Label();
		Label date_label = new Label();
		Label time_label = new Label();
		
		int time_taken = service.getTime();
		System.out.println(time_taken);
		end_time = time;
		for(int i = 0; i<time_taken;i++) {
			end_time = end_time.plusMinutes(15);
		}
		
		id_label.setText("Customer ID: "+cust_id);
		service_label.setText("Service: "+service.toString());
		emp_label.setText("Employee: "+employee.getFirstName()+" "+employee.getLastName());
		date_label.setText("Date: "+date.toString());
		time_label.setText("Time: "+time.toString()+"-"+end_time);
		
		ArrayList<Label> labels = new ArrayList<Label>();
		labels.add(id_label);
		labels.add(service_label);
		labels.add(emp_label);
		labels.add(date_label);
		labels.add(time_label);
		
		int counter = 0;
		for(Label lab : labels) {
			lab.setFont(Font.font(20));
			lab.setStyle("-fx-text-fill: white");
			lab.setLayoutX(580.0);
			lab.setLayoutY(255.0+(counter*70));
			rootPane.getChildren().add(lab);
			counter++;
		}
		
	}
	
	@FXML
	void accept(ActionEvent event) throws IOException {
		String status = "pending";
		comp.getCalendar().requestBooking(date, time, end_time, employee, service, cust_id);
		String bID = comp.getCalendar().getCalendarInfo().get(date).get(time).getID();
		menu.addBooking(bID, cust_id, service.toString(), employee.getID(), date.toString(), time.toString()+"-"+end_time.toString(), status);
		if(portal.equals("business")) {
			status = "booked";
			comp.getCalendar().acceptBooking(bID);
			menu.addBooking(bID, cust_id, service.toString(), employee.getID(), date.toString(), time.toString()+"-"+end_time.toString(), status);
		}
		goToPortal();
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb5 = new FXMLLoader(getClass().getResource("MakeBooking5.fxml"));
    	pane = mb5.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking5Controller controller = mb5.getController();
    	controller.initiate(menu, cust_id, service, employee, date, portal);
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
