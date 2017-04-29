package application;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class AddAvailTimeController {
	
	private Menu menu;
	
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
    private Label invalid;

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
    
    public void initiate(Menu menu) {
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
    	String hour = "0?(8|9|10|11|12|13|14|15|16)";
    	String minute = "[0-5][0-9]";
    	
    	//checking username
    	if(menu.idValid(username)){
    		idValid = true;
    	}
    	else{
    		invalid.setText("Employee username is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
    	}
    	
    	//checking day
    	if(mon.isSelected() || tue.isSelected() || wed.isSelected() || thu.isSelected() || fri.isSelected()){
    		if(mon.isSelected()){
    			day = DayOfWeek.of(1);
        	}
        	if(tue.isSelected()){
        		day = DayOfWeek.of(2);
        	}
        	if(wed.isSelected()){
        		day = DayOfWeek.of(3);
        	}
        	if(thu.isSelected()){
        		day = DayOfWeek.of(4);
        	}
        	if(fri.isSelected()){
        		day = DayOfWeek.of(5);
        	}
        	dayValid = true;
    	}
    	else{
    		invalid.setText("Please select a day");
			invalid.setAlignment(Pos.CENTER);
    	}
    	
    	//checking start time
    	if(menu.validate(sHour, hour)){
			if(menu.validate(sMinute, minute)){
				startTime = LocalTime.of(Integer.parseInt(sHour), Integer.parseInt(sMinute));
				startTimeValid = true;
			}
			else{
				invalid.setText("Start time is invalid. Please try again");
				invalid.setAlignment(Pos.CENTER);
			}
		}
		else{
			invalid.setText("Start time is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
    	
    	//checking end time
    	if(menu.validate(eHour, hour)){
			if(menu.validate(eMinute, minute)){
				if(menu.validEndTime(sHour, eHour, sMinute, eMinute)){
					endTime = LocalTime.of(Integer.parseInt(eHour), Integer.parseInt(eMinute));
					endTimeValid = true;
				}
				else{
					invalid.setText("End time is invalid. Please try again");
					invalid.setAlignment(Pos.CENTER);
				}
			}
			else{
				invalid.setText("End time is invalid. Please try again");
				invalid.setAlignment(Pos.CENTER);
			}
		}
		else{
			invalid.setText("End time is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
    	
    	//sending user input to database
    	if(idValid && dayValid && startTimeValid && endTimeValid){
    		invalid.setText("");
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
    	loginController controller = login.getController();
		controller.initiate(menu);
    }
    
    @FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
    }

}
