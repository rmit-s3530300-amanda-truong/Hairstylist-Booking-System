package gui.calendar;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import business.Company;
import business.Employee;
import calendar.Booking;
import calendar.Calendar.Status;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mainController.MainController;

public class ViewAfternoonController {
	
	private MainController menu;
	private Company comp;
	
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
	private Label time16;
	@FXML
	private AnchorPane rootPane;
	
	@FXML
	private Label availLabel;
	@FXML
	private Label unavailLabel;
	@FXML
	private Label bookedLabel;
	@FXML
	private Rectangle availBox;
	@FXML
	private Rectangle unavailBox;
	@FXML
	private Rectangle bookedBox;
	@FXML
    private JFXButton gotoLogout;
	@FXML
	private JFXButton backButton;
	
	private String portal;
	
	private String cust_id;
	
	private BookingManagementSystem bms;
    
	public void initiate(Company comp, String cust_id, String portal, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.portal = portal;
		this.cust_id = cust_id;
		this.bms = bms;
		populateTable();
		createRectangles();
	}
	
	//creates rectangles to populate the calendar and colours according to booked, unavailable or not
	@FXML
	void createRectangles()
	{
		ArrayList<Booking> bookingList = comp.getCalendar().getBookingList();
		HashMap<String, Employee> empList = comp.getEmployeeList();
		int columns = 7, horizontal = 95, vertical = 25;
        Rectangle rect = null;
        LocalDate current = comp.getCalendar().getDate();
        for(int i = 0; i < columns; i++)
        {
        	//only creating rectangles for afternoon times 12:00 - 16:00
        	int j = 0;
        	LocalTime current_time = LocalTime.of(12, 00);
            while(!current_time.equals(LocalTime.of(16, 00)))
            {
            	//(y,x,width,height) = parameters for rectangle
                rect = new Rectangle((220+(135*i)), 117+(30*j), horizontal, vertical);
                Boolean valid = false;
                Boolean availableCheck = false;
                //checking if employee is available at time slot, if yes colour green, if not grey
                for(Entry<String, Employee> x : empList.entrySet()) {
        			String ID = x.getKey();
        			HashMap<DayOfWeek,ArrayList<LocalTime>> availList = comp.getEmployee(ID).getAvailability();
        			for(Entry<DayOfWeek,ArrayList<LocalTime>> y : availList.entrySet())
        			{
        				DayOfWeek day = y.getKey();
        				ArrayList<LocalTime> time = y.getValue();
        				if(day.equals(current.getDayOfWeek()))
        				{
        					for(int t=0;t<time.size();t++)
        					{
        						if(time.get(t).equals(current_time))
        						{
        							availableCheck = true;
        						}
        					}
        				}
        			}
        		}
                //checking if there is booking at time slot, if yes colour red
                for(int x=0; x<bookingList.size(); x++)
                {
	                	Booking book = bookingList.get(x);
	                	if(current.equals(book.getDate()))
	                	{
	                		if(book.getStatus().equals(Status.booked)){
		                		String[] id = book.getID().split("/");
		                		String startTime = id[1];
		                		if(current_time.toString().equals(startTime)) {
		                    		valid = true;
		                        }
	                		}
	            		}
                }
                if(valid) {
                	rect.setFill(Color.web("#cc2e2e"));
                }
                else if(availableCheck){
                	rect.setFill(Color.web("#2ba85f"));
                }
                else {
                	rect.setFill(Color.web("#9e9e9e"));
                }
                displayPane.getChildren().add(rect);
            	current_time = current_time.plusMinutes(15);
            	j++;
            } 
            current = current.plusDays(1);
        }
	}
	
	//create the time and day labels 
	@FXML
	void populateTable()
	{	
		day1 = new Label();
		day2 = new Label();
		day3 = new Label();
		day4 = new Label();
		day5 = new Label();
		day6 = new Label();
		day7 = new Label();
		time1 = new Label();
		time2 = new Label();
		time3 = new Label();
		time4 = new Label();
		time5 = new Label();
		time6 = new Label();
		time7 = new Label();
		time8 = new Label();
		time9 = new Label();
		time10 = new Label();
		time11 = new Label();
		time12 = new Label();
		time13 = new Label();
		time14 = new Label();
		time15 = new Label();
		time16 = new Label();

		LocalDate current = comp.getCalendar().getDate();
		day1.setText(current.toString());
		day1.setLayoutX(225.0);
		day1.setLayoutY(94.0);
		day1.setStyle("-fx-font: 16 Lato;");
		day1.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(day1);
		day2.setText(current.plusDays(1).toString());
		day2.setLayoutX(360.0);
		day2.setLayoutY(94.0);
		day2.setStyle("-fx-font: 16 Lato;");
		day2.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(day2);
		day3.setText(current.plusDays(2).toString());
		day3.setLayoutX(495.0);
		day3.setLayoutY(94.0);
		day3.setStyle("-fx-font: 16 Lato;");
		day3.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(day3);
		day4.setText(current.plusDays(3).toString());
		day4.setLayoutX(630.0);
		day4.setLayoutY(94.0);
		day4.setStyle("-fx-font: 16 Lato;");
		day4.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(day4);
		day5.setText(current.plusDays(4).toString());
		day5.setLayoutX(765.0);
		day5.setLayoutY(94.0);
		day5.setStyle("-fx-font: 16 Lato;");
		day5.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(day5);
		day6.setText(current.plusDays(5).toString());
		day6.setLayoutX(900.0);
		day6.setLayoutY(94.0);
		day6.setStyle("-fx-font: 16 Lato;");
		day6.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(day6);
		day7.setText(current.plusDays(6).toString());
		day7.setLayoutX(1035.0);
		day7.setLayoutY(94.0);
		day7.setStyle("-fx-font: 16 Lato;");
		day7.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(day7);
		time1.setText("12:00-12:15");
		time1.setLayoutX(110.0);
		time1.setLayoutY(120.0);
		time1.setStyle("-fx-font: 16 Lato;");
		time1.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time1);
		time2.setText("12:15-12:30");
		time2.setLayoutX(110.0);
		time2.setLayoutY(150.0);
		time2.setStyle("-fx-font: 16 Lato;");
		time2.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time2);
		time3.setText("12:30-12:45");
		time3.setLayoutX(110.0);
		time3.setLayoutY(180.0);
		time3.setStyle("-fx-font: 16 Lato;");
		time3.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time3);
		time4.setText("12:45-13:00");
		time4.setLayoutX(110.0);
		time4.setLayoutY(210.0);
		time4.setStyle("-fx-font: 16 Lato;");
		time4.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time4);
		time5.setText("13:00-13:15");
		time5.setLayoutX(110.0);
		time5.setLayoutY(240.0);
		time5.setStyle("-fx-font: 16 Lato;");
		time5.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time5);
		time6.setText("13:15-13:30");
		time6.setLayoutX(110.0);
		time6.setLayoutY(270.0);
		time6.setStyle("-fx-font: 16 Lato;");
		time6.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time6);
		time7.setText("13:30-13:45");
		time7.setLayoutX(110.0);
		time7.setLayoutY(300.0);
		time7.setStyle("-fx-font: 16 Lato;");
		time7.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time7);
		time8.setText("13:45-14:00");
		time8.setLayoutX(110.0);
		time8.setLayoutY(330.0);
		time8.setStyle("-fx-font: 16 Lato;");
		time8.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time8);
		time9.setText("14:00-14:15");
		time9.setLayoutX(110.0);
		time9.setLayoutY(360.0);
		time9.setStyle("-fx-font: 16 Lato;");
		time9.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time9);
		time10.setText("14:15-14:30");
		time10.setLayoutX(110.0);
		time10.setLayoutY(390.0);
		time10.setStyle("-fx-font: 16 Lato;");
		time10.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time10);
		time11.setText("14:30-14:45");
		time11.setLayoutX(110.0);
		time11.setLayoutY(420.0);
		time11.setStyle("-fx-font: 16 Lato;");
		time11.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time11);
		time12.setText("14:45-15:00");
		time12.setLayoutX(110.0);
		time12.setLayoutY(450.0);
		time12.setStyle("-fx-font: 16 Lato;");
		time12.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time12);
		time13.setText("15:00-15:15");
		time13.setLayoutX(110.0);
		time13.setLayoutY(480.0);
		time13.setStyle("-fx-font: 16 Lato;");
		time13.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time13);
		time14.setText("15:15-15:30");
		time14.setLayoutX(110.0);
		time14.setLayoutY(510.0);
		time14.setStyle("-fx-font: 16 Lato;");
		time14.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time14);
		time15.setText("15:30-15:45");
		time15.setLayoutX(110.0);
		time15.setLayoutY(540.0);
		time15.setStyle("-fx-font: 16 Lato;");
		time15.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time15);
		time16.setText("15:45-16:00");
		time16.setLayoutX(110.0);
		time16.setLayoutY(570.0);
		time16.setStyle("-fx-font: 16 Lato;");
		time16.setTextFill(Color.web("#FFFFFF"));
		displayPane.getChildren().add(time16);
	}
	
	@FXML
	void goCalendar() throws IOException{
		AnchorPane pane;
    	FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("ViewCalendar.fxml"));
    	pane = viewCalendar.load();
    	rootPane.getChildren().setAll(pane);
    	CalendarController controller = viewCalendar.getController();
		controller.initiate(comp, cust_id, portal, bms);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(comp, bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
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
	    	controller.initiate(comp, cust_id, bms);
		}
	}
}
