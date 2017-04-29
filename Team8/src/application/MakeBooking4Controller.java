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

public class MakeBooking4Controller {
	private Menu menu;
	
	private Company comp;
	
	private String cust_id;
	
	private Service service;
	
	private Employee employee;
	
	private LocalDate date = null;
	
	@FXML
	private Label invalid;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

	public void initiate(Menu menu, String cust_id, Service service, Employee employee) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		int counter = 0;
		comp = menu.getCompany();
		
		LocalDate current_date = comp.getCalendar().getDate();
		DayOfWeek current_day = current_date.getDayOfWeek();
		ArrayList<LocalDate> week = new ArrayList<LocalDate>();
		HashMap<DayOfWeek, ArrayList<LocalTime>> avail = employee.getAvailability();
		for(int i=0; i <14;i++) {
			if(avail.containsKey(current_day)) {
				week.add(current_date);
			}
			current_day = current_day.plus(1);
			current_date = current_date.plusDays(1);
		}
		HashMap<LocalDate, ArrayList<LocalTime>> booking = employee.getBookings();
		for(int i =0; i<week.size();i++) {
			LocalDate date = week.get(i);
			if(booking.containsKey(date)) {
				week.remove(i);
			}
		}
		
		if(week.size() > 0) {
			ArrayList<RadioButton> buttons = new ArrayList<RadioButton>();
			for(LocalDate date : week) {
				RadioButton b = new RadioButton();
				b.setUserData(date);
				b.setText(date.toString());
				b.setStyle("-fx-text-fill: white");
				b.setFont(Font.font(16));
				if(counter <4) {
					b.setLayoutX(487.0);
					b.setLayoutY(255.0+(counter*50));
				} else {
					b.setLayoutX(715.0);
					b.setLayoutY(255.0+((counter-4)*50));
				}
				rootPane.getChildren().add(b);
				buttons.add(b);
				counter++;
			}
			for(RadioButton button : buttons) {
				button.setToggleGroup(group);
			}
			buttons.get(0).setSelected(true);
			date = (LocalDate) buttons.get(0).getUserData();
		} else {
			invalid.setText("No Dates Available");
			invalid.setAlignment(Pos.CENTER);
		}
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				date = (LocalDate) arg2.getUserData();
				
			}
			
		});
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {
		if(employee== null) {
			invalid.setText("Invalid Option");
			invalid.setAlignment(Pos.CENTER);
		} else {
			System.out.println(date.toString());
			AnchorPane pane;
	    	FXMLLoader mb5 = new FXMLLoader(getClass().getResource("MakeBooking5.fxml"));
	    	pane = mb5.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking5Controller controller = mb5.getController();
	    	controller.initiate(menu, cust_id, service, employee, date);
			
		}
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb3 = new FXMLLoader(getClass().getResource("MakeBooking3.fxml"));
    	pane = mb3.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking3Controller controller = mb3.getController();
    	controller.initiate(menu, cust_id, service);
	}
	
	@FXML
	void Home(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
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
