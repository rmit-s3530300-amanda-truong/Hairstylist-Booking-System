package application;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import Business.Employee;
import Business.Employee.Service;
import Menu.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class MakeBooking6Controller {
	private Menu menu;
	
	private String cust_id;
	
	private Service service;
	
	private Employee employee;
	
	private LocalDate date=null;
	
	private LocalTime time;
	
	private String portal;
	
	@FXML
	private Label invalid;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

	public void initiate(Menu menu, String cust_id, Service service, Employee employee, LocalDate date, String select, String portal) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
		this.portal = portal;
		
		int counter = 0;
		
		HashMap<DayOfWeek, ArrayList<LocalTime>> avail = employee.getAvailability();
		DayOfWeek day = date.getDayOfWeek();
		
		HashMap<LocalDate, ArrayList<LocalTime>> booking = employee.getBookings();
		
		ArrayList<LocalTime> avail_times = new ArrayList<LocalTime>();
		
		int service_time_taken = service.getTime();
		
		ArrayList<LocalTime> unavail = new ArrayList<LocalTime>();
		
		for(int i =1;i<=service_time_taken;i++) {
			if(avail.get(day).size()-1 >= i) {
				unavail.add(avail.get(day).get(avail.get(day).size()-i));
				System.out.println(avail.get(day).get(avail.get(day).size()-i));
			}
		}
		
		Boolean contains = false;
		
		if(avail.get(day)!= null){
			if(select.equals("morning")) {
				for(LocalTime time : avail.get(day)) {
					for(LocalTime t1 : unavail) {
						if(t1.equals(time)) {
							contains = true;
						}
					}
					if(!contains) {
						if(time.isBefore(LocalTime.of(12, 00))) {
							avail_times.add(time);
						}
					}
				}
					
			} else {
				for(LocalTime time : avail.get(day)) {
					for(LocalTime t1 : unavail) {
						if(t1.equals(time)) {
							contains = true;
						}
					}
					if(!contains) {
						if(time.isAfter(LocalTime.of(11, 45)) && time.isBefore(LocalTime.of(16, 00) )) {
							avail_times.add(time);
						}
					}
				}
			}
			ArrayList<LocalTime> booked_times = booking.get(date);
			
			if(booked_times != null) {
				for(LocalTime times : booked_times) {
					if(avail_times.contains(times)) {
						avail_times.remove(times);
					}
				}
			}
		}
		
		if(avail_times.size() > 0) {
			ArrayList<JFXRadioButton> buttons = new ArrayList<JFXRadioButton>();
			for(LocalTime time : avail_times) {
				JFXRadioButton b = new JFXRadioButton();
				b.setUserData(time);
				b.setText(time.toString());
				b.setStyle("-fx-text-fill: white");
				b.setFont(Font.font(16));
				if(counter <8) {
					b.setLayoutX(487.0);
					b.setLayoutY(255.0+(counter*50));
				} else {
					b.setLayoutX(715.0);
					b.setLayoutY(255.0+((counter-8)*50));
				}
				rootPane.getChildren().add(b);
				buttons.add(b);
				counter++;
			}
			for(JFXRadioButton button : buttons) {
				button.setToggleGroup(group);
			}
			buttons.get(0).setSelected(true);
			time = (LocalTime) buttons.get(0).getUserData();
		} else {
			invalid.setText("No Times Available");
			invalid.setAlignment(Pos.CENTER);
		}
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				time = (LocalTime) arg2.getUserData();
				
			}
			
		});
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {
		if(time== null) {
			invalid.setText("Invalid Option");
			invalid.setAlignment(Pos.CENTER);
		} else {
			System.out.println(time.toString());
			AnchorPane pane;
	    	FXMLLoader mb7 = new FXMLLoader(getClass().getResource("MakeBooking7.fxml"));
	    	pane = mb7.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking7Controller controller = mb7.getController();
	    	controller.initiate(menu, cust_id, service, employee, date, time, portal);
		}
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
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(menu);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(menu, cust_id);
		}
    }
}