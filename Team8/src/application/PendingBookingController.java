package application;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import business.Company;
import calendar.Booking;
import calendar.Calendar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mainController.MainController;

public class PendingBookingController {
	
	final ToggleGroup group = new ToggleGroup();
	
	private MainController menu;
	
	private String id;
	
	private Company comp;
	
	private Boolean status;
	
	private ArrayList<Booking> list;
	
	@FXML
	private Label invalid;
	
	private TextArea ta;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	private VBox textBox;
	
	@FXML
	private JFXTextField text;
	
	@FXML
	private JFXRadioButton accept;
	
	@FXML 
	private JFXRadioButton decline;
	
	@FXML
	public void initialize() {;
		ta = new TextArea();
	}
	
	public String getPendingBooking() {
		Calendar cal = comp.getCalendar();
		list = cal.getDisplayBookedList();
		String pending="";
		
		for(Booking book : list) {
			pending = pending + String.format("%-20s %-20s %-20s %10s %25s %28s\n", book.getID(), book.getDate().toString(), book.getStartTime().toString(), book.getEndTime().toString(), book.getCustomerID(), book.getEmployee().getID());
		}
		ta.setFont(Font.font ("Lato Bold", 16));
		ta.setStyle("-fx-text-fill: white");
		ta.setText(pending);
		ta.setEditable(false);
		ta.setLayoutX(299.0);
		ta.setLayoutY(230.0);
		ta.setPrefHeight(250.0);
		ta.setPrefWidth(793.0);
		
		rootPane.getChildren().add(ta);
		return pending;
	}
	
	public void initiate(MainController menu, String cust_id) {
		this.menu = menu;
		id = cust_id;
		comp = menu.getCompany();
		text.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
		accept.setToggleGroup(group);
		accept.setUserData(true);
		decline.setToggleGroup(group);
		decline.setUserData(false);
		accept.setSelected(true);
		status = (Boolean) accept.getUserData();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				status = (Boolean) arg2.getUserData();
			}
		});
		getPendingBooking();
		
		
	}
	
	@FXML
	void apply(ActionEvent event) {
		System.out.println("entered");
		String id = text.getText();
		if(list.isEmpty()) {
			invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			invalid.setText("Invalid Booking ID");
		}
		Boolean exist = false;
		for(Booking b : list) {
			String current = b.getID();
			if(current.equals(id)) {
				if(!exist) {
					System.out.println("valid "+status);
					invalid.setText("");
					if(status) {
						Boolean s1 = menu.getCompany().getCalendar().acceptBooking(id);
						if(s1){
							invalid.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
							invalid.setText("Successfully Accepted");
							exist = true;
						} else {
							invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
							invalid.setText("Invalid Booking ID");
						}
					} else {
						Boolean s2 = menu.getCompany().getCalendar().declineBooking(id);
						if(s2) {
							invalid.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
							invalid.setText("Successfully Declined");
							exist = true;
						} else {
							invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
							invalid.setText("Invalid Booking ID");
						}
						
					}
				}
			}
		} 
		if(!exist) {
			System.out.println("invalid");
			invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
			invalid.setText("Invalid Booking ID");
		}
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(id == null) {
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
	    	controller.initiate(menu, id);
		}
	}
}
