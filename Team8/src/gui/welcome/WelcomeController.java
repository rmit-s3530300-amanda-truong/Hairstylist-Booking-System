package gui.welcome;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

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
	
    @FXML
    void goToLogin(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu);
    }
    
    @FXML
    void goToRegister(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("../register/RegisterCustomer.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterCustomerController controller = register.getController();
		controller.initiate(menu);
    }
    
    public void initiate(MainController menu){
    	this.menu = menu;
    }
}
