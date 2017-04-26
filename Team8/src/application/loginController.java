package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class loginController {

	private Menu menu;
	
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
    
    public void initiate(Menu menu){
    	this.menu = menu;
    }

    @FXML
    void login(ActionEvent event) throws IOException{
    	String username = l_username.getText();
    	String password = l_password.getText();
    	
    	if(menu.authenticate(username, password).equals("customer")){
    		AnchorPane pane = FXMLLoader.load(getClass().getResource("CustomerPortal.fxml"));
	    	rootPane.getChildren().setAll(pane);
    	}
    	else if (menu.authenticate(username, password).equals("business")){
    		AnchorPane pane = FXMLLoader.load(getClass().getResource("BusinessPortal.fxml"));
	    	rootPane.getChildren().setAll(pane);
    	}
		else{
			//when user input is wrong
			invalid_id_password.setText("Invalid username or password. Please try again");
		}
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
	
}
