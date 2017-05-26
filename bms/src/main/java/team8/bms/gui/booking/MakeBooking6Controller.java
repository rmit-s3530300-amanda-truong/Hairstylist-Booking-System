package team8.bms.gui.booking;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import team8.bms.gui.portal.BusinessPController;
import team8.bms.gui.portal.CustomerPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.business.Employee;
import team8.bms.gui.LoginController;

public class MakeBooking6Controller {

	private Company comp;
	
	private String cust_id;
	
	private String service;
	
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
	
	@FXML
    private Label logoText;
	
	private BookingManagementSystem bms;
	
	@FXML
	private JFXComboBox<String> chooseTime;
	
	private ObservableList<String> times = FXCollections.observableArrayList();
	
	

	public void initiate(Company comp, String cust_id, String service, Employee employee, LocalDate date, String portal, BookingManagementSystem bms) {
		this.comp = comp;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.date = date;
		this.portal = portal;
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
		
		HashMap<DayOfWeek, ArrayList<LocalTime>> avail = employee.getAvailability();
		DayOfWeek day = date.getDayOfWeek();
		
		HashMap<LocalDate, ArrayList<LocalTime>> booking = employee.getBookings();
		
		ArrayList<LocalTime> avail_times = new ArrayList<LocalTime>();
		
		int service_time_taken = comp.getServiceTime(service);
		
		ArrayList<LocalTime> unavail = new ArrayList<LocalTime>();
		
		for(int i =1;i<=service_time_taken;i++) {
			if(avail.get(day).size()-1 >= i) {
				unavail.add(avail.get(day).get(avail.get(day).size()-i));
			}
		}
		
		Boolean contains = false;
		if(avail.get(day)!= null){
			// check if the times available are not within the 'available time slot'
			for(LocalTime time : avail.get(day)) {
				for(LocalTime t1 : unavail) {
					if(t1.equals(time)) {
						contains = true;
					}
				}
				if(!contains) {
					avail_times.add(time);
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
			for(LocalTime time : avail_times) {
				times.add(time.toString());
			}
			chooseTime.setItems(times);
			chooseTime.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(times.size()>0) {
						time = LocalTime.parse(newValue);
					}
				}
			});
		} else {
			chooseTime.setOpacity(0);
			chooseTime.setDisable(true);
			invalid.setText("No Times Available");
			invalid.setAlignment(Pos.CENTER);
		}
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {
		if(time== null) {
			invalid.setText("Invalid Option");
			invalid.setAlignment(Pos.CENTER);
		} else {
			AnchorPane pane;
	    	FXMLLoader mb7 = new FXMLLoader(getClass().getResource("/MakeBooking7.fxml"));
	    	pane = mb7.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking7Controller controller = mb7.getController();
	    	controller.initiate(comp, cust_id, service, employee, date, time, portal, bms);
		}
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb4 = new FXMLLoader(getClass().getResource("/MakeBooking4.fxml"));
    	pane = mb4.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking4Controller controller = mb4.getController();
    	controller.initiate(comp, cust_id, service, employee, portal, bms);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(comp,bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(comp, cust_id, bms);
		}
    }
}
