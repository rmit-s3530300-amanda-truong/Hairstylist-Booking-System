package gui.welcome;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import business.Company;
import gui.login.LoginController;
import gui.register.RegisterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class WelcomeController {
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton goToLogin;

    @FXML
    private JFXButton goToRegister;
    
    private BookingManagementSystem bms;
    
    public void initiate(BookingManagementSystem bms){
    	this.bms = bms;
    }
	
    @FXML
    void goToLogin(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/gui/login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }
    
    @FXML
    void goToRegister(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("/gui/register/Register.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterController controller = register.getController();
		controller.initiate(bms);
    }
}
