package application;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Calendar.Booking;
import Calendar.Calendar;
import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UpcomingBookingController {
	
	private Menu menu;
	
	private String id;
	
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
	public void initialize() {;
		ta = new TextArea();
	}
	
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
		ta.setLayoutX(299.0);
		ta.setLayoutY(230.0);
		ta.setPrefHeight(430.0);
		ta.setPrefWidth(793.0);
		
		rootPane.getChildren().add(ta);
		return future;
	}
	
	public void initiate(Menu menu, String cust_id) {
		this.menu = menu;
		id = cust_id;
		comp = menu.getCompany();
		getUpcomingBooking();
	}
	
	@FXML
	void Home(ActionEvent event) throws IOException {
		if(id==null) {
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
