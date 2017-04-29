package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Calendar.Booking;
import Menu.Menu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ViewAfternoonController {
	
	private Menu menu;
	
	@FXML
	private Pane displayPane;
	
	@FXML
	private Label day1;
	@FXML
	private Label day2;
	@FXML
	private Label day3;
	@FXML
	private Label day4;
	@FXML
	private Label day5;
	@FXML
	private Label day6;
	@FXML
	private Label day7;
	@FXML
	private Label time1;
	@FXML
	private Label time2;
	@FXML
	private Label time3;
	@FXML
	private Label time4;
	@FXML
	private Label time5;
	@FXML
	private Label time6;
	@FXML
	private Label time7;
	@FXML
	private Label time8;
	@FXML
	private Label time9;
	@FXML
	private Label time10;
	@FXML
	private Label time11;
	@FXML
	private Label time12;
	@FXML
	private Label time13;
	@FXML
	private Label time14;
	@FXML
	private Label time15;
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

	@FXML
	private JFXButton returnButton;
    
	public void initiate(Menu menu) {
		this.menu = menu;
	}
	
	@FXML
	void createRectangles()
	{
		ArrayList<Booking> bookingList = menu.getCompany().getCalendar().getPendingBooking();
		int columns = 7, rows = 15 , horizontal = 95, vertical = 25;
		LocalTime startTime = null;
        Rectangle rect = null;
        LocalDate current = menu.getCompany().getCalendar().getDate();
        for(int i = 0; i < columns; i++)
        {
        	int j = 0;
        	LocalTime current_time = LocalTime.of(12, 00);
            while(!current_time.equals(LocalTime.of(16, 00)))
            {
                //(y,x,width,height)
                rect = new Rectangle((220+(135*i)), 117+(30*j), horizontal, vertical);
                Boolean valid = false;
                for(int x=0; x<bookingList.size(); x++)
                {
	                	Booking book = bookingList.get(x);
	                	if(current.equals(book.getDate()))
	                	{
	                		if(current_time.toString().equals(book.getStartTime().toString())) {
	                    		valid = true;
	                        }
	            		}
                }
                if(valid) {
                	rect.setFill(Color.RED);
                } else {
                	rect.setFill(Color.WHITE);
                }
                displayPane.getChildren().add(rect);
            	current_time = current_time.plusMinutes(15);
            	j++;
            } 
            current = current.plusDays(1);
        }
	}
	
	@FXML
	void goCalendar() throws IOException{
		AnchorPane pane;
    	FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("ViewCalendar.fxml"));
    	pane = viewCalendar.load();
    	rootPane.getChildren().setAll(pane);
    	CalendarController controller = viewCalendar.getController();
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
    	CustomerPController controller = bussPortal.getController();
    	controller.initiate(menu);
	}
}
