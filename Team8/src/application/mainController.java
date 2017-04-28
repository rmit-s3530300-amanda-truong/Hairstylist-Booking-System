package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class mainController {
	
	private Menu menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton goToLogin;

    @FXML
    private JFXButton goToRegister;
	
    @FXML
    void goToLogin(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
    }
    
    @FXML
    void goToRegister(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("RegisterCustomer.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterController controller = register.getController();
		controller.initiate(menu);
    }
    
    public void initiate(Menu menu){
    	this.menu = menu;
    }
}
