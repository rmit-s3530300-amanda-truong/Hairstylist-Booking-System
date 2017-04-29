package application;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.prism.paint.Color;

import Business.Company;
import Business.Customer;
import Business.Employee;
import Business.Employee.Service;
import Menu.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class MakeBooking7Controller {
	private Menu menu;
	
	private Company comp;
	
	private String cust_id;
	
	private Service service;
	
	private Employee employee;
	
	private LocalDate date;
	
	private LocalTime time;
	
	private LocalTime end_time;
	
	@FXML
	private Label invalid;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

	public void initiate(Menu menu, String cust_id, Service service, Employee employee, LocalDate date, LocalTime time) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
		this.time = time;
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
			lab.setLayoutX(487.0);
			lab.setLayoutY(255.0+(counter*70));
			rootPane.getChildren().add(lab);
			counter++;
		}
		
	}
	
	@FXML
	void accept(ActionEvent event) throws IOException {
		
		
		comp.getCalendar().requestBooking(date, time, end_time, employee, service, cust_id);
		String bID = comp.getCalendar().getCalendarInfo().get(date).get(time).getID();
		
		comp.getCalendar().acceptBooking(bID);
		goToPortal();
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb5 = new FXMLLoader(getClass().getResource("MakeBooking5.fxml"));
    	pane = mb5.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking5Controller controller = mb5.getController();
    	controller.initiate(menu, cust_id, service, employee, date);
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
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
    }
}
