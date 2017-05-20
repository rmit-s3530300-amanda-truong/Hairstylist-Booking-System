package gui.booking_summary;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import calendar.Booking;
import calendar.Calendar;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
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

public class UpcomingBookingController {
	
	private MainController menu;
	
	private String id;
	
	final ToggleGroup group = new ToggleGroup();
	
	private Company comp;
	
	//@FXML
	private TextArea ta;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	private VBox textBox;
	
	@FXML
	private Label invalid;
	
	@FXML
	private JFXTextField text;
	
	private Boolean status;
	private ArrayList<Booking> list;
	
	@FXML
	public void initialize() {;
		ta = new TextArea();
	}
	
	private BookingManagementSystem bms;
	
	public String getUpcomingBooking() {
		Calendar cal = comp.getCalendar();
		ArrayList<Booking> list = cal.getDisplayFutureBooking();
		String future="";
		
		System.out.println(id);
		for(Booking book : list) {
			System.out.println(book.getID());
		}
		
		for(Booking book : list) {
			if(id != null) {
				if(book.getCustomerID().equals(id)) {
					future = future + String.format("%-20s %-20s %-20s %10s %25s %28s\n", book.getID(), book.getDate().toString(), book.getStartTime().toString(), book.getEndTime().toString(), book.getCustomerID(), book.getEmployee().getID());
				}
			} else {
				future = future + String.format("%-20s %-20s %-20s %10s %25s %28s\n", book.getID(), book.getDate().toString(), book.getStartTime().toString(), book.getEndTime().toString(), book.getCustomerID(), book.getEmployee().getID());
			}
		}
		ta.setFont(Font.font ("Lato Bold", 16));
		ta.setStyle("-fx-text-fill: white");
		ta.setText(future);
		ta.setEditable(false);
		ta.setLayoutX(340.0);
		ta.setLayoutY(270.0);
		ta.setPrefHeight(430.0);
		ta.setPrefWidth(793.0);
		
		rootPane.getChildren().add(ta);
		return future;
	}
	
	public void initiate(Company comp, String cust_id, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		id = cust_id;
		this.bms = bms;
		getUpcomingBooking();
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
						Boolean s1 = comp.getCalendar().acceptBooking(id);
						if(s1){
							invalid.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
							invalid.setText("Successfully Accepted");
							exist = true;
						} else {
							invalid.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
							invalid.setText("Invalid Booking ID");
						}
					} else {
						Boolean s2 = comp.getCalendar().declineBooking(id);
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
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(id == null) {
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
	    	controller.initiate(comp, id, bms);
		}
	}
}
