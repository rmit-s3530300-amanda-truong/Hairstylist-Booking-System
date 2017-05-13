package gui.login;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import gui.portal.AdminPController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import gui.register.RegisterCustomerController;
import gui.welcome.PreWelcomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class LoginController {

	private MainController menu;
	private Company comp;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton goToRegister;
    
    @FXML
    private JFXTextField l_username;

    @FXML
    private JFXPasswordField l_password;

    @FXML
    private JFXButton loginAction;
    
    @FXML
    private Label invalid_id_password;
    
    private BookingManagementSystem bms;
    
    public void initiate(Company comp, BookingManagementSystem bms){
    	this.comp = comp;
    	menu = comp.getMenu();
    	this.bms = bms;
    }

    @FXML
    void login(ActionEvent event){
    	String username = l_username.getText();
    	String password = l_password.getText();
    	
    	if(menu.authenticate(username, password).equals("customer")){
    		goToCustomerPortal(username);
    	}
    	else if (menu.authenticate(username, password).equals("business")){
    		goToBusinessPortal(username);
    	}
    	else if (username.equals("admin") && password.equals("admin")){
    		goToAdminPortal();
    	}
		else{
			invalid_id_password.setText("Invalid username or password. Please try again");
		}
    }
    
    @FXML
    void goToRegister(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("../register/RegisterCustomer.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterCustomerController controller = register.getController();
		controller.initiate(comp, bms);
    }
	
    //loads customer portal scene
	void goToCustomerPortal(String username){
		try {
			AnchorPane pane;
	    	FXMLLoader custPortal = new FXMLLoader(getClass().getResource("../portal/CustomerPortal.fxml"));
	    	pane = custPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = custPortal.getController();
			controller.initiate(comp, username, bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//loads business portal scene
	void goToBusinessPortal(String username){
		try {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
			controller.initiate(comp, bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    void goToBusiness(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader business = new FXMLLoader(getClass().getResource("../welcome/PreWelcome.fxml"));
    	pane = business.load();
    	rootPane.getChildren().setAll(pane);
    	PreWelcomeController controller = business.getController();
		controller.initiate(bms);
    }
	
	void goToAdminPortal(){
		try {
			AnchorPane pane;
	    	FXMLLoader adminPortal = new FXMLLoader(getClass().getResource("../portal/AdminPortal.fxml"));
	    	pane = adminPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	AdminPController controller = adminPortal.getController();
			controller.initiate(menu, bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
