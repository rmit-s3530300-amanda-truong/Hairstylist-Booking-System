package team8.bms.gui.booking;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import team8.bms.gui.portal.BusinessPController;
import team8.bms.gui.portal.CustomerPController;
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
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.business.Employee;
import team8.bms.gui.LoginController;

public class MakeBooking4Controller {
	
	private Company comp;
	
	private String cust_id;
	
	private String service;
	
	private Employee employee;
	
	private String portal;
	
	private LocalDate date = null;
	
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

	public void initiate(Company comp, String cust_id, String
			service, Employee employee, String portal, BookingManagementSystem bms) {
		this.comp = comp;
		this.cust_id = cust_id;
		this.service = service;
		this.employee = employee;
		this.portal = portal;
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
		int counter = 0;
		
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
		
		if(week.size() > 0) {
			ArrayList<JFXRadioButton> buttons = new ArrayList<JFXRadioButton>();
			for(LocalDate date : week) {
				JFXRadioButton b = new JFXRadioButton();
				b.setUserData(date);
				b.setText(date.toString());
				b.setStyle("-fx-text-fill: white");
				b.setFont(Font.font(16));
				if(counter <4) {
					b.setLayoutX(487.0);
					b.setLayoutY(305.0+(counter*50));
				} else {
					b.setLayoutX(715.0);
					b.setLayoutY(305.0+((counter-4)*50));
				}
				rootPane.getChildren().add(b);
				buttons.add(b);
				counter++;
			}
			for(JFXRadioButton button : buttons) {
				button.setToggleGroup(group);
			}
			buttons.get(0).setSelected(true);
			date = (LocalDate) buttons.get(0).getUserData();
		} else {
			invalid.setText("No Dates Available");
			invalid.setAlignment(Pos.CENTER);
		}
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

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
			AnchorPane pane;
	    	FXMLLoader mb6 = new FXMLLoader(getClass().getResource("/MakeBooking6.fxml"));
	    	pane = mb6.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking6Controller controller = mb6.getController();
	    	controller.initiate(comp, cust_id, service, employee, date, portal, bms);
			
		}
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb3 = new FXMLLoader(getClass().getResource("/MakeBooking3.fxml"));
    	pane = mb3.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking3Controller controller = mb3.getController();
    	controller.initiate(comp, cust_id, service, portal, bms);
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
	    	controller.initiate(comp, bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(comp, cust_id,bms);
		}
    }
}
