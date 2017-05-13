package gui.register;

import java.io.IOException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Customer;
import gui.portal.AdminPController;
import gui.welcome.PreWelcomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class RegisterBusinessController {

	private MainController menu;
	private BookingManagementSystem bms;
	
	@FXML
    private AnchorPane rootPane;

    @FXML
    private Label logoText;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Label invalidConfirmPass;

    @FXML
    private JFXPasswordField confirmpassword;

    @FXML
    private Label invalidPass;

    @FXML
    private Label invalidUname;
    
    @FXML
    private Label validRegister;

    public void initiate(MainController menu, BookingManagementSystem bms) {
		this.menu = menu;
		this.bms = bms;
	}
    
    @FXML
    void register(ActionEvent event) throws IOException {
    	String uname = username.getText();
    	String pass = password.getText();
    	String confirmpass = confirmpassword.getText();
    	
    	boolean unameValid = false, passValid = false;
		
		//regex patterns for user input
		String unameRegex = "^(?=^.{5,}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$";
		String passRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$)(?=.*[@#$%!^&+=]).{6,}$";
		
		//checking password
		if(menu.validate(pass, passRegex)){
			if(pass.equals(confirmpass)){
				passValid = true;
				invalidPass.setText("");
				invalidConfirmPass.setText("");
			}
			else{
				invalidPass.setText("");
				invalidConfirmPass.setText("Password doesn't match.");
			}
		}
		else{
			invalidConfirmPass.setText("");
			invalidPass.setText("Invalid Must be >6 char & contain atleast 1 uppercase, number, symbol.");
		}
		
		//checking username input
		if(menu.validate(uname, unameRegex)){
			//TODO: check for username in database
//			if(menu.uniqueUname(username)){
				unameValid = true;
				invalidUname.setText("");
//			}
//			else{
//				invalidUname.setText("Username is already taken.");
//			}
		}
		else{
			invalidUname.setText("Invalid Username. Must contain least 5 characters.");
		}
		
		//sending info to database
		if(unameValid && passValid){
			//menu.registerBusiness(uname, pass);
			//TODO: send info to hashmap
			validRegister.setText("Business was Successfully created.");
		}
    }
    
    @FXML
    void goToLogout(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader welcome = new FXMLLoader(getClass().getResource("../welcome/PreWelcome.fxml"));
    	pane = welcome.load();
    	rootPane.getChildren().setAll(pane);
    	PreWelcomeController controller = welcome.getController();
		controller.initiate(menu, bms);
    }
    
    @FXML
    void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader adminPortal = new FXMLLoader(getClass().getResource("../portal/AdminPortal.fxml"));
    	pane = adminPortal.load();
    	rootPane.getChildren().setAll(pane);
    	AdminPController controller = adminPortal.getController();
    	controller.initiate(menu, bms);
    }

}
