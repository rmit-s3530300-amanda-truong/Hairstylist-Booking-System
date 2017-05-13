package gui.welcome;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import gui.login.LoginController;
import gui.register.RegisterCustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class WelcomeController {
	
	private MainController menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton goToLogin;

    @FXML
    private JFXButton goToRegister;
    
    private BookingManagementSystem bms;
	
    @FXML
    void goToLogin(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu, bms);
    }
    
    @FXML
    void goToRegister(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("../register/RegisterCustomer.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterCustomerController controller = register.getController();
		controller.initiate(menu, bms);
    }
    
    public void initiate(MainController menu, BookingManagementSystem bms){
    	this.menu = menu;
    	this.bms = bms;
    }
    
    @FXML
    void goToBusiness(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader business = new FXMLLoader(getClass().getResource("PreWelcome.fxml"));
    	pane = business.load();
    	rootPane.getChildren().setAll(pane);
    	PreWelcomeController controller = business.getController();
		controller.initiate(menu, bms);
    }
}
