package gui.calendar;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mainController.MainController;

public class ViewCalendarController {
	
	private Company comp;
	@FXML
	private AnchorPane rootPane;
	@FXML
	private Pane displayPane;
	@FXML
	private Pane contPane;
	@FXML 
	private ScrollPane scrollPaneCal;
	@FXML
	private Label day;
	@FXML
	private Label time1;
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
	
	private String cust_id;
	LocalTime sTime;
	LocalTime eTime;
	private String portal;
	
	private BookingManagementSystem bms;
    
	public void initiate(Company comp, String cust_id, String portal, BookingManagementSystem bms) {
		this.comp = comp;
		this.cust_id = cust_id;
		this.portal = portal;
		this.bms = bms;
		contPane = new Pane();
		scrollPaneCal = new ScrollPane();
		scrollPaneCal.setLayoutX(180);
		scrollPaneCal.setLayoutY(120);
		scrollPaneCal.setPrefHeight(530.0);
		scrollPaneCal.setPrefWidth(1100.0);
		scrollPaneCal.setVbarPolicy(ScrollBarPolicy.NEVER);
		populateTable();
		createRectangles();
		scrollPaneCal.setContent(contPane);
		displayPane.getChildren().add(scrollPaneCal);
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
        	//only creating rectangles for morning times 08:00 - 12:00
        	int j = 0;
        	//LocalTime current_time = LocalTime.of(8, 00);
        	LocalTime current_time = sTime;
            while(!current_time.equals(eTime))
            {
                //(y,x,width,height) = parameters for rectangle
                rect = new Rectangle((150+(135*i)), 40+(30*j), horizontal, vertical);
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
                contPane.getChildren().add(rect);
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
		time1 = new Label();
		LocalDate current = comp.getCalendar().getDate();
		for(int i=0; i<7; i++)
		{
			day = new Label(current.plusDays(i).toString());
			day.setLayoutX(154.0 + (135*i));
			day.setLayoutY(5);
			day.setStyle("-fx-font: 16 Lato;");
			day.setStyle("-fx-text-fill: white");
			contPane.getChildren().add(day);
		}
		String busHours = comp.getBusinessHours();
		//need to escape | and \\
		String[] dayandTime = busHours.split("\\|",-1);
		String startTime = null;
		String endTime = null;
		String day;
		HashMap<String,String> hours = new HashMap<String,String>();
		for(int i=0; i<dayandTime.length; i++)
		{
			String[] split1 = dayandTime[i].split("=");
			day = split1[0];
			if(!split1[1].equals("empty"))
			{
				String[] split2 = split1[1].split(",");
				startTime = split2[0];
				endTime = split2[1];
				hours.put(day, startTime+"-"+endTime);
				sTime = LocalTime.parse(startTime);
				eTime = LocalTime.parse(endTime);
			}
		}
		//checking the earliest and latest business hours to set frame of calendar
		for(Entry<String,String> x: hours.entrySet())
		{
			String[] times = x.getValue().split("-");
			LocalTime start = LocalTime.parse(times[0]);
			LocalTime end = LocalTime.parse(times[1]);
			if(start.isBefore(sTime))
			{
				sTime = start;
			}
			if(end.isBefore(eTime))
			{
				eTime = end;
			}
		}
	    long minutesBetween = ChronoUnit.MINUTES.between(sTime, eTime);
		for(int i=0; i<(minutesBetween)/15; i++)
		{
			time1 = new Label(sTime.plusMinutes(15*i)+"-"+sTime.plusMinutes(15*(i+1)));
			time1.setLayoutX(10.0);
			time1.setLayoutY(40 + (30*i));
			time1.setStyle("-fx-font: 16 Lato;");
			time1.setStyle("-fx-text-fill: white");
			contPane.getChildren().add(time1);
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
