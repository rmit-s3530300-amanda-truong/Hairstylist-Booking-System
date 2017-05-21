package gui.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class RegisterBusiness3Controller {

	private Company comp;
	private BookingManagementSystem bms;
	private HashMap<Integer, HashMap<JFXTextField, JFXComboBox<String>>> services = new HashMap<Integer, HashMap<JFXTextField, JFXComboBox<String>>>();
	private int counter = 0;
	
	
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private JFXComboBox<String> service_time;
    
    ObservableList<String> times = FXCollections.observableArrayList
			("15", "30","45", "60");
    
    @FXML
    private JFXButton add_button;
    
    @FXML
    private JFXTextField name;
    
    @FXML
	private Pane displayPane;
	@FXML
	private Pane contPane;
	@FXML 
	private ScrollPane scrollPaneCal;
	
	@FXML
	private Label invalid;
    
    public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		this.bms = bms;
		comp.clearService();
		service_time.setItems(times);
		service_time.setValue("15");
		contPane = new Pane();
		scrollPaneCal = new ScrollPane();
		scrollPaneCal.setLayoutX(340);
		scrollPaneCal.setLayoutY(257);
		scrollPaneCal.setPrefHeight(330.0);
		scrollPaneCal.setPrefWidth(650.0);
		scrollPaneCal.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	}
    
    @FXML
    void add(ActionEvent event) {
    	invalid.setText("");
    	if(name.getText() == null || name.getText().isEmpty()) {
    		invalid.setText("Service Name cannot be empty.");
    	} else {
    		JFXComboBox<String> serv = new JFXComboBox<String>();
    		serv.setItems(times);
    		JFXTextField text = new JFXTextField();
    		
    		text.setText(name.getText());
    		serv.setValue(service_time.getValue());
    		
    		text.setPrefColumnCount(15);
    		serv.setPrefWidth(178);

    		serv.setUnFocusColor(Paint.valueOf("white"));
    		serv.setFocusColor(Paint.valueOf("#aa9144"));

    		text.setStyle("-fx-text-inner-color: white; -fx-font-size: 16;");
    		text.setUnFocusColor(Paint.valueOf("white"));
    		text.setFocusColor(Paint.valueOf("#aa9144"));
    		
    		if(services.isEmpty()) {
	    		serv.setLayoutX(320);
	    		serv.setLayoutY(6);
	    		
	    		text.setLayoutX(74);
	    		text.setLayoutY(10);
    		} else {
    			Double s_offsetX = null;
    			Double s_offsetY = null;
    			
    			Double t_offsetX = null;
    			Double t_offsetY = null;
    			for(Entry<JFXTextField, JFXComboBox<String>>  entry : services.get(counter-1).entrySet()) {
    				t_offsetX = entry.getKey().getLayoutX();
    				t_offsetY = entry.getKey().getLayoutY();
    				
    				s_offsetX = entry.getValue().getLayoutX();
    				s_offsetY = entry.getValue().getLayoutY();
    			}
    			serv.setLayoutX(s_offsetX);
	    		serv.setLayoutY(s_offsetY+60);
	    		
	    		text.setLayoutX(t_offsetX);
	    		text.setLayoutY(t_offsetY+60);
    		}
    		
    		Boolean unique = true;
    		for(int i=0; i<services.size();i++) {
    			for(Entry<JFXTextField, JFXComboBox<String>> entry :services.get(i).entrySet()) {
    				if(entry.getKey().getText().equals(text.getText())) {
    					unique = false;
    					invalid.setText("Service Name Must Be Unique");
    				}
    			}
    		}
    		if(unique) {
    			HashMap<JFXTextField, JFXComboBox<String>> inner = new HashMap<JFXTextField, JFXComboBox<String>>();
	    		inner.put(text, serv);
	    		services.put(counter, inner);
	    		contPane.getChildren().add(serv);
	    		contPane.getChildren().add(text);
	    		scrollPaneCal.setContent(contPane);
	    		displayPane.getChildren().remove(scrollPaneCal);
	    		displayPane.getChildren().add(scrollPaneCal);
	    		counter++;
    		}
    		name.setText("");
    	}
    	
    }

    @FXML
    void backRegister(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("RegisterBusiness2.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterBusiness2Controller controller = login.getController();
		controller.initiate(bms, comp);
    }
    
    @FXML
    void nextRegister4(ActionEvent event) throws IOException {
    	invalid.setText("");
    	if(services.isEmpty()) {
    		invalid.setText("Service Name cannot be empty.");
    	} else {
    		//checking for empty service name
    		Boolean valid = true;
    		int i =0;
			while(valid&&i<services.size()) {
    			for(Entry<JFXTextField, JFXComboBox<String>> entry :services.get(i).entrySet()) {
    				String serv_name = entry.getKey().getText();
    				if(serv_name == null || serv_name.isEmpty()) {
    					valid = false;
    					invalid.setText("Service Name Cannot Be Empty");
    					break;
    				} else {
	    				Integer time = Integer.parseInt(entry.getValue().getValue())/15;
	    				comp.addService(serv_name);
	    				comp.addServiceTime(serv_name, time);
    				}
    			}
    			i++;
			}
    		if(valid) {
    			AnchorPane pane;
    	    	FXMLLoader register = new FXMLLoader(getClass().getResource("../register/RegisterBusiness4.fxml"));
    	    	pane = register.load();
    	    	rootPane.getChildren().setAll(pane);
    	    	RegisterBusiness4Controller controller = register.getController();
    			controller.initiate(comp, bms);
    		}
    	}
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
}
