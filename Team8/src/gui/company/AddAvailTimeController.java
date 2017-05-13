package gui.company;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class AddAvailTimeController {
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	private MainController menu;
	
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

    private BookingManagementSystem bms;
    
    public void initiate(MainController menu, BookingManagementSystem bms) {
		this.menu = menu;
		this.bms = bms;
	}
    
    ObservableList<String> startHourList = FXCollections.observableArrayList
			("08", "09","10", "11", "12", "13", "14", "15");
    ObservableList<String> endHourList = FXCollections.observableArrayList
			("09","10", "11", "12", "13", "14", "15", "16");
    ObservableList<String> minuteList = FXCollections.observableArrayList
			("00", "15", "30", "45");

    @FXML
    private void initialize(){
    	startHour.setValue("08");
    	startMinute.setValue("00");
    	startHour.setItems(startHourList);
    	endHour.setValue("16");
    	endMinute.setValue("00");
    	endHour.setItems(endHourList);
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
    	if(menu.idValid(username)){
    		idValid = true;
    		LOGGER.info("True "+username);
    		invalidusername.setText("");
    	}
    	else{
    		LOGGER.info("False "+username);
    		invalidusername.setText("Invalid username.");
			invalidusername.setAlignment(Pos.CENTER_LEFT);
    	}
    	
    	//checking day
    	if(mon.isSelected() || tue.isSelected() || wed.isSelected() || thu.isSelected() || fri.isSelected()){
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
        	dayValid = true;
        	invalidday.setText("");
    	}
    	else{
    		LOGGER.info("Invalid Day");
    		invalidday.setText("Please select a day.");
			invalidday.setAlignment(Pos.CENTER_LEFT);
    	}
    	startTime = LocalTime.of(Integer.parseInt(sHour), Integer.parseInt(sMinute));
		LOGGER.info(sHour + " " + sMinute);
		startTimeValid = true;
    	
    	//checking end time
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
		controller.initiate(menu, bms);
		LOGGER.info("Logout");
    }
    
    @FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu, bms);
    	LOGGER.info("Go to Portal");
    }

}
