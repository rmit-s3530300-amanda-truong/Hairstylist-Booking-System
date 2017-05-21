package gui.register;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import gui.login.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class RegisterBusiness2Controller {
	
	ObservableList<String> hourList = FXCollections.observableArrayList
			("Closed", "00", "01","02", "03", "04", "05", "06","07", "08", "09", "10", "11",
			"12", "13", "14", "15", "16","17", "18", "19", "20", "21","22", "23");
	ObservableList<String> minuteList = FXCollections.observableArrayList
			("Closed","00", "15", "30","45");
	
	private Company comp;
	
	private BookingManagementSystem bms;
	
	 @FXML
	 private AnchorPane rootPane;
	 
	 @FXML
	 private JFXComboBox<String> monshour;
	    
	 @FXML
	 private JFXComboBox<String> monsminute;

	 @FXML
	 private JFXComboBox<String> monehour;

	 @FXML
	 private JFXComboBox<String> moneminute;

	 @FXML
	 private JFXComboBox<String> tueshour;

	 @FXML
	 private JFXComboBox<String> tuesminute;

	 @FXML
	 private JFXComboBox<String> tueehour;

	 @FXML
	 private JFXComboBox<String> tueeminute;

	 @FXML
	 private JFXComboBox<String> wedshour;

	 @FXML
	 private JFXComboBox<String> wedsminute;

	 @FXML
	 private JFXComboBox<String> wedehour;

	 @FXML
	 private JFXComboBox<String> wedeminute;

	 @FXML
	 private JFXComboBox<String> thushour;

	 @FXML
	 private JFXComboBox<String> thusminute;

	 @FXML
	 private JFXComboBox<String> thuehour;

	 @FXML
	 private JFXComboBox<String> thueminute;

	 @FXML
	 private JFXComboBox<String> frishour;

	 @FXML
	 private JFXComboBox<String> frisminute;

	 @FXML
	 private JFXComboBox<String> friehour;

	 @FXML
	 private JFXComboBox<String> frieminute;

	 @FXML
	 private JFXComboBox<String> satshour;

	 @FXML
	 private JFXComboBox<String> satsminute;
	 
	 @FXML
	 private JFXComboBox<String> satehour;

	 @FXML
	 private JFXComboBox<String> sateminute;

	 @FXML
	 private JFXComboBox<String> sunshour;

	 @FXML
	 private JFXComboBox<String> sunsminute;

	 @FXML
	 private JFXComboBox<String> sunehour;
	 
	 @FXML
	 private JFXComboBox<String> suneminute;
	 
	 /*@FXML
	 private Label uniInvalid;*/
	 
	 @FXML
	 private Label monInvalid;

	 @FXML
	 private Label tueInvalid;
	 
	 @FXML
	 private Label wedInvalid;
	 
	 @FXML
	 private Label thuInvalid;
	 
	 @FXML
	 private Label friInvalid;
	 	
	 @FXML
	 private Label satInvalid;

	 @FXML
	 private Label sunInvalid;
	 
	 @FXML
	 private JFXTextField businessName;

	 @FXML
	 private Label businessNameInvalid;
	 
	 @FXML
	 private Label invalid_times;
	 
	 ArrayList<Label> invalid_days = new ArrayList<Label>();
	 
	 LinkedHashMap<JFXComboBox<String>, JFXComboBox<String>> times= new LinkedHashMap<JFXComboBox<String>, JFXComboBox<String>>();
	 
	 LinkedHashMap<DayOfWeek, String> avail_times = new LinkedHashMap<DayOfWeek, String>();
	 
	 String name;
    
	 @FXML
	 private void initialize(){
		//populating combobox
		 times.put(monshour, monsminute);
		 times.put(monehour, moneminute);
		 times.put(tueshour, tuesminute);
		 times.put(tueehour, tueeminute);
		 times.put(wedshour, wedsminute);
		 times.put(wedehour, wedeminute);
		 times.put(thushour, thusminute);
		 times.put(thuehour, thueminute);
		 times.put(frishour, frisminute);
		 times.put(friehour, frieminute);
		 times.put(satshour, satsminute);
		 times.put(satehour, sateminute);
		 times.put(sunshour, sunsminute);
		 times.put(sunehour, suneminute);
		 
		 int counter =1;
		 for(Entry<JFXComboBox<String>, JFXComboBox<String>> entry : times.entrySet()) {
			 entry.getKey().setItems(hourList);
			 // if it is even then it is the end time 16
			 if(counter%2 == 0){
				 entry.getKey().setValue("16");
			// if it is odd then it is the start time and set 08
			 } else {
				 entry.getKey().setValue("08");
			 }
			 entry.getValue().setItems(minuteList);
			 entry.getValue().setValue("00");
			 counter++;
		 }
		 
    }
    
	 public void initiate(BookingManagementSystem bms, Company comp) {
		this.bms = bms;
		this.comp = comp;
		
		invalid_days.add(monInvalid);
		invalid_days.add(tueInvalid);
		invalid_days.add(wedInvalid);
		invalid_days.add(thuInvalid);
		invalid_days.add(friInvalid);
		invalid_days.add(satInvalid);
		invalid_days.add(sunInvalid);
	}
    
    @FXML
    void backRegister(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("../register/Register.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterController controller = register.getController();
		controller.initiate(bms);
    }

    @FXML
    void goToLogin(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }

    @FXML
    void nextRegister3(ActionEvent event) throws IOException {
    	Boolean valid = true;
    	
    	invalid_times.setText("");
    	businessNameInvalid.setText("");
    	for(Label l : invalid_days){
			l.setText("");
		}
    	
    	name = businessName.getText();
    	if(name == null || name.isEmpty()) {
    		valid = false;
    		businessNameInvalid.setText("Invalid Business Name");
    	} else {
    		for(Company c :bms.getCompanyList()) {
    			if(c.getName().equals(name)) {
    				businessNameInvalid.setText("Business Name has been taken");
    				valid = false;
    			}
    		}
    	}
    	// Checking if the start time is before the end time
    	if(valid) {
    		ArrayList<JFXComboBox<String>> keyList = new ArrayList<JFXComboBox<String>>(times.keySet());
        	ArrayList<JFXComboBox<String>> valueList = new ArrayList<JFXComboBox<String>>(times.values());
    		String s_hour;
    		String e_hour;
    		String s_min;
    		String e_min;
    		for(int i=1;i<keyList.size();i=i+2){
    			int j = i;
    			j--;
    			s_hour = keyList.get(j).getValue();
    			e_hour = keyList.get(i).getValue();
    			s_min = valueList.get(j).getValue();
    			e_min = valueList.get(i).getValue();
    			if(!s_hour.equals("Closed") && !e_hour.equals("Closed") && !s_min.equals("Closed") && !e_min.equals("Closed")) {	
	    			if(!validTime(s_hour, s_min, e_hour, e_min)) {
	    				valid = false;
	    				invalid_times.setText("Invalid Times: End Times must be after Start Time eg. 08:00 - 14:00");
	        			invalid_days.get((i)/2).setText("Invalid Times");
	    			} else {
	    				String start = s_hour+":"+s_min;
	    				String end = e_hour+":"+e_min;
	    				String time = start+"-"+end;
	    				if(j==0) {
	    					avail_times.put(DayOfWeek.of(1), time);
	    				} else if(j%2==0) {
	    					avail_times.put(DayOfWeek.of((j/2)+1), time);
	    				}
	    			}
    			} else {
    				if(s_hour.equals("Closed") && e_hour.equals("Closed") && s_min.equals("Closed") && e_min.equals("Closed")) {
    					if(j==0) {
	    					avail_times.put(DayOfWeek.of(1),"");
	    				} else if(j%2==0) {
	    					avail_times.put(DayOfWeek.of((j/2)+1), "");
	    				}
    				} else {
    					valid = false;
    					invalid_times.setText("Invalid Times: Must choose Closed for Start and End Times");
    					invalid_days.get(i/2).setText("Invalid Times");
    					comp.clearService();
    				}
    			}
    		}
    	}
    	if(valid) {
    		comp.setBusinessName(name);
    		comp.setBusHours(avail_times);
    		nextStep(comp);
    	}
    }
    
    public Boolean validTime(String s_hour, String s_min, String e_hour, String e_min) {
    	LocalTime start = LocalTime.of(Integer.parseInt(s_hour), Integer.parseInt(s_min));
    	LocalTime end = LocalTime.of(Integer.parseInt(e_hour), Integer.parseInt(e_min));
    	if(start.isAfter(end) || start.equals(end)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public void nextStep(Company comp) throws IOException {
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("../register/RegisterBusiness3.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterBusiness3Controller register_controller = register.getController();
		register_controller.initiate(comp, bms);
    }
    
}
