package application;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import MainController.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

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
    private JFXTextField startHour;

    @FXML
    private JFXTextField startMinute;

    @FXML
    private JFXTextField endHour;

    @FXML
    private JFXTextField endMinute;

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
    private Label invalidstarthour;

    @FXML
    private Label invalidendhour;
    
    public void initiate(MainController menu) {
		this.menu = menu;
	}

    @FXML
    void addAvail(ActionEvent event) throws IOException {
    	DayOfWeek day = null;
    	LocalTime startTime = null;
    	LocalTime endTime = null;
    	String username = empUsername.getText();
    	String sHour = startHour.getText();
    	String sMinute = startMinute.getText();
    	String eHour = endHour.getText();
    	String eMinute = endMinute.getText();
    	
    	boolean idValid = false, dayValid = false, startTimeValid = false, endTimeValid = false;
    	
    	//regex patterns for user input
    	String shour = "0?(8|9|10|11|12|13|14|15)";
    	String ehour = "0?(9|10|11|12|13|14|15|16)";
    	String minute = "00|15|30|45";
    	
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
    	//checking start time
    	if(menu.validate(sHour, shour)){
			if(menu.validate(sMinute, minute)){
				startTime = LocalTime.of(Integer.parseInt(sHour), Integer.parseInt(sMinute));
				LOGGER.info(sHour+" "+sMinute);
				startTimeValid = true;
				invalidstarthour.setText("");
			}
			else{
				invalidstarthour.setText("Invalid Start time. Choose between 15 minute interval.");
				LOGGER.info("Invalid Start time. Choose between 15 minute interval.");
				invalidstarthour.setAlignment(Pos.CENTER);
			}
		}
		else{
			invalidstarthour.setText("Invalid Start time. Choose between 08:00 to 15:00.");
			LOGGER.info("Invalid Start time. Choose between 08:00 to 15:00.");
			invalidstarthour.setAlignment(Pos.CENTER);
		}
    	
    	//checking end time
    	if(menu.validate(eHour, ehour)){
			if(menu.validate(eMinute, minute)){
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
				invalidendhour.setText("Invalid End time. Choose between 15 minute interval.");
				LOGGER.info("Invalid End time. Choose between 15 minute interval.");
				invalidendhour.setAlignment(Pos.CENTER);
			}
		}
		else{
			invalidendhour.setText("Invalid End time. Choose between 09:00 to 16:00");
			LOGGER.info("Invalid End time. Choose between 09:00 to 16:00");
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
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu);
		LOGGER.info("Logout");
    }
    
    @FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
    	LOGGER.info("Go to Portal");
    }

}
