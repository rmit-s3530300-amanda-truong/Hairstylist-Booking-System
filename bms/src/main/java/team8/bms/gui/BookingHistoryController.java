package team8.bms.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import team8.bms.gui.portal.BusinessPController;
import team8.bms.gui.portal.CustomerPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.calendar.Booking;
import team8.bms.calendar.Calendar;
import team8.bms.mainController.MainController;

public class BookingHistoryController {
	
	private MainController menu;
	
	private Company comp;
	
	private String id;
	
	//@FXML
	private TextArea ta;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	private VBox textBox;
	
	@FXML
    private Label logoText;
	
	private BookingManagementSystem bms;
	
	@FXML
	public void initialize() {;
		ta = new TextArea();
	}
	
	public String getPastBooking() {
		Calendar cal = comp.getCalendar();
		ArrayList<Booking> list = cal.getDisplayPastBooking();
		String future="";
		
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
		id = cust_id;
		this.comp = comp;
		menu = bms.getMenu();
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
		getPastBooking();
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
		if(id==null) {
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
	    	controller.initiate(comp, id, bms);
		}
    }
}
