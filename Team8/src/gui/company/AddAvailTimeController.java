package gui.company;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import business.Employee;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class AddAvailTimeController {
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	private MainController menu;
	private Company comp;
    ObservableList<String> monStartHourList = FXCollections.observableArrayList();
    ObservableList<String> monEndHourList = FXCollections.observableArrayList();
    ObservableList<String> tueStartHourList = FXCollections.observableArrayList();
    ObservableList<String> tueEndHourList = FXCollections.observableArrayList();
    ObservableList<String> wedStartHourList = FXCollections.observableArrayList();
    ObservableList<String> wedEndHourList = FXCollections.observableArrayList();
    ObservableList<String> thurStartHourList = FXCollections.observableArrayList();
    ObservableList<String> thurEndHourList = FXCollections.observableArrayList();
    ObservableList<String> friStartHourList = FXCollections.observableArrayList();
    ObservableList<String> friEndHourList = FXCollections.observableArrayList();
    ObservableList<String> satStartHourList = FXCollections.observableArrayList();
    ObservableList<String> satEndHourList = FXCollections.observableArrayList();
    ObservableList<String> sunStartHourList = FXCollections.observableArrayList();
    ObservableList<String> sunEndHourList = FXCollections.observableArrayList();
	
	@FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton gotoLogout;

    @FXML
    private JFXTextField empUsername;

    @FXML
    private JFXButton addAvailability;

    @FXML
    private JFXRadioButton mon;

    @FXML
    private ToggleGroup days;

    @FXML
    private JFXRadioButton tue;

    @FXML
    private JFXRadioButton wed;

    @FXML
    private JFXRadioButton thu;

    @FXML
    private JFXRadioButton fri;
    
    @FXML
    private JFXRadioButton sat;
    
    @FXML
    private JFXRadioButton sun;
    
    @FXML
    private Label invalidusername;

    @FXML
    private Label invalidday;

    @FXML
    private Label invalidendhour;
    
    @FXML
    private JFXComboBox<String> empUname;

    @FXML
    private JFXComboBox<String> startHour;

    @FXML
    private JFXComboBox<String> startMinute;

    @FXML
    private JFXComboBox<String> endHour;

    @FXML
    private JFXComboBox<String> endMinute;
    
    @FXML
    private Label logoText;

    private BookingManagementSystem bms;
    
    public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
    	menu = comp.getMenu();
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
		
		String busHours = comp.getBusString();
		//need to escape | and \\
		String[] dayandTime = busHours.split("\\|",-1);
		String startTime = null;
		String endTime = null;
		String day;
		int stime = 0;
		int etime = 0;
		for(int i=0; i<dayandTime.length; i++)
		{
			String[] split1 = dayandTime[i].split("=");
			day = split1[0];
			if(!split1[1].equals("empty"))
			{
				String[] split2 = split1[1].split(",");
				startTime = split2[0];
				endTime = split2[1];
				String[] stimesplit = startTime.split(":");
				String[] etimesplit = endTime.split(":");
				stime = Integer.parseInt(stimesplit[0]);
				etime = Integer.parseInt(etimesplit[0]);
			}
			if(day.equals("MONDAY") || day.equals("Monday"))
			{
				for(int t = stime; t<etime; t++)
				{
					monStartHourList.add(String.valueOf(t));
					monEndHourList.add(String.valueOf(t+1));
				}	
				if(split1[1].equals("empty"))
				{
					mon.setDisable(true);
				}
			}
			else if(day.equals("TUESDAY") || day.equals("Tuesday"))
			{
				for(int t = stime; t<etime; t++)
				{
					tueStartHourList.add(String.valueOf(t));	
					tueEndHourList.add(String.valueOf(t+1));
				}
				if(split1[1].equals("empty"))
				{
					tue.setDisable(true);
				}
			}
			else if(day.equals("WEDNESDAY") || day.equals("Wednesday"))
			{
				for(int t = stime; t<etime; t++)
				{
					wedStartHourList.add(String.valueOf(t));	
					wedEndHourList.add(String.valueOf(t+1));
				}
				if(split1[1].equals("empty"))
				{
					wed.setDisable(true);
				}
			}
			else if(day.equals("THURSDAY") || day.equals("Thursday"))
			{
				for(int t = stime; t<etime; t++)
				{
					thurStartHourList.add(String.valueOf(t));	
					thurEndHourList.add(String.valueOf(t+1));
				}
				if(split1[1].equals("empty"))
				{
					thu.setDisable(true);
				}
			}
			else if(day.equals("FRIDAY") || day.equals("Friday"))
			{
				for(int t = stime; t<etime; t++)
				{
					friStartHourList.add(String.valueOf(t));	
					friEndHourList.add(String.valueOf(t+1));
				}
				if(split1[1].equals("empty"))
				{
					fri.setDisable(true);
				}
			}
			else if(day.equals("SATURDAY") ||day.equals("Saturday"))
			{
				for(int t = stime; t<etime; t++)
				{
					satStartHourList.add(String.valueOf(t));	
					satEndHourList.add(String.valueOf(t+1));
					if(split1[1].equals("empty"))
					{
						sat.setDisable(true);
					}
				}
			}
			else if(day.equals("SUNDAY") || day.equals("Sunday"))
			{
				for(int t = stime; t<etime; t++)
				{
					sunStartHourList.add(String.valueOf(t));	
					sunEndHourList.add(String.valueOf(t+1));
				}
				System.out.println(sunStartHourList.isEmpty());
				if(split1[1].equals("empty"))
				{
					sun.setDisable(true);
				}
			}
		}
		mon.setToggleGroup(days);
		tue.setToggleGroup(days);
		wed.setToggleGroup(days);
		thu.setToggleGroup(days);
		fri.setToggleGroup(days);
		sat.setToggleGroup(days);
		sun.setToggleGroup(days);
		
		mon.setSelected(true);
		startHour.setItems(monStartHourList);
		endHour.setItems(monEndHourList);
		
		days.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            	JFXRadioButton button = (JFXRadioButton)newValue.getToggleGroup().getSelectedToggle();
            	if(button.getText().equals("MONDAY"))
            	{
            		startHour.setItems(monStartHourList);
            		endHour.setItems(monEndHourList);
            	}
            	else if(button.getText().equals("TUESDAY"))
            	{
            		startHour.setItems(tueStartHourList);
            		endHour.setItems(tueEndHourList);
            	}
            	else if(button.getText().equals("WEDNESDAY"))
            	{
            		startHour.setItems(wedStartHourList);
            		endHour.setItems(wedEndHourList);
            	}
            	else if(button.getText().equals("THURSDAY"))
            	{
            		startHour.setItems(thurStartHourList);
            		endHour.setItems(thurEndHourList);
            	}
            	else if(button.getText().equals("FRIDAY"))
            	{
            		startHour.setItems(friStartHourList);
            		endHour.setItems(friEndHourList);
            	}
            	else if(button.getText().equals("SATURDAY"))
            	{
            		startHour.setItems(satStartHourList);
            		endHour.setItems(satEndHourList);
            	}
            	else if(button.getText().equals("SUNDAY"))
            	{
            		startHour.setItems(sunStartHourList);
            		endHour.setItems(sunEndHourList);
            	}
            }
        });
		
	}

    ObservableList<String> minuteList = FXCollections.observableArrayList
			("00", "15", "30", "45");

    @FXML
    private void initialize(){
    	//startHour.setValue("08");
    	//startMinute.setValue("00");
    	//startHour.setItems(startHourList);
    	//endHour.setValue("16");
    	//endMinute.setValue("00");
    	//endHour.setItems(endHourList);
    	startMinute.setItems(minuteList);
    	endMinute.setItems(minuteList);
    }
    
    @FXML
    void addAvail(ActionEvent event) throws IOException {
    	DayOfWeek day = null;
    	LocalTime startTime = null;
    	LocalTime endTime = null;
    	String username = empUsername.getText();
    	String sHour = startHour.getValue();
    	String sMinute = startMinute.getValue();
    	String eHour = endHour.getValue();
    	String eMinute = endMinute.getValue();
    	
    	boolean idValid = false, dayValid = false, startTimeValid = false, endTimeValid = false;
    	
    	//checking username
    	Employee emp = comp.getEmployee(username);
    	if(emp != null)
    	{
        	if(menu.idValid(username) && comp.getName().equals(emp.getCompName())){
        		idValid = true;
        		LOGGER.info("True "+username);
        		invalidusername.setText("");
        	}
        	else{
        		LOGGER.info("False "+username);
        		invalidusername.setText("Invalid username.");
    			invalidusername.setAlignment(Pos.CENTER_LEFT);
        	}	
    	}
    	else{
    		LOGGER.info("False "+username);
    		invalidusername.setText("Invalid username.");
			invalidusername.setAlignment(Pos.CENTER_LEFT);
    	}
    	
    	//checking day
    	if(mon.isSelected() || tue.isSelected() || wed.isSelected() || thu.isSelected() || fri.isSelected() || sat.isSelected() || sun.isSelected()){
    		if(mon.isSelected()){
    			LOGGER.info("Monday selected");
    			day = DayOfWeek.of(1);
        	}
        	if(tue.isSelected()){
        		LOGGER.info("Tuesday selected");
        		day = DayOfWeek.of(2);
        	}
        	if(wed.isSelected()){
        		LOGGER.info("Wednesday selected");
        		day = DayOfWeek.of(3);
        	}
        	if(thu.isSelected()){
        		LOGGER.info("Thursday selected");
        		day = DayOfWeek.of(4);
        	}
        	if(fri.isSelected()){
        		LOGGER.info("Friday selected");
        		day = DayOfWeek.of(5);
        	}
        	if(sat.isSelected()){
        		LOGGER.info("Saturday selected");
        		day = DayOfWeek.of(6);
        	}
        	if(sun.isSelected()){
        		LOGGER.info("Sunday selected");
        		day = DayOfWeek.of(7);
        	}
        	dayValid = true;
        	invalidday.setText("");
    	}
    	else{
    		LOGGER.info("Invalid Day");
    		invalidday.setText("Please select a day.");
			invalidday.setAlignment(Pos.CENTER_LEFT);
    	}
    	//checking start hour
    	if(!startHour.getSelectionModel().isEmpty())
    	{
    		startTime = LocalTime.of(Integer.parseInt(sHour), Integer.parseInt(sMinute));
    		LOGGER.info(sHour + " " + sMinute);
    		startTimeValid = true;
    		invalidendhour.setText("");
    	}
    	else
    	{
    		invalidendhour.setText("Please select a start time");
    		invalidendhour.setAlignment(Pos.CENTER);
    	}
    	
    	//checking end time
    	if(!endHour.getSelectionModel().isEmpty())
    	{
    		if(menu.validEndTime(sHour, eHour, sMinute, eMinute)){
    			endTime = LocalTime.of(Integer.parseInt(eHour), Integer.parseInt(eMinute));
				endTimeValid = true;
				invalidendhour.setText("");
			}
    		else{
    			invalidendhour.setText("Invalid End time. Must be atleast 1 hour more than Start time.");
    			LOGGER.info("Invalid End time. Must be atleast 1 hour more than Start time.");
    			invalidendhour.setAlignment(Pos.CENTER);
    		}
		}
		else{
			invalidendhour.setText("Please select an end time");
			invalidendhour.setAlignment(Pos.CENTER);
		}

    	
    	//sending user input to database
    	if(idValid && dayValid && startTimeValid && endTimeValid){
    		LOGGER.info(idValid+" "+dayValid+" "+startTimeValid+" "+endTimeValid);
    		menu.addEmployeeAvailability(username, day, startTime, endTime);
    		goToPortal();
    	}
    }

    @FXML
    void goToLogout(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
		LOGGER.info("Logout");
    }
    
    @FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(comp, bms);
    	LOGGER.info("Go to Portal");
    }

}
