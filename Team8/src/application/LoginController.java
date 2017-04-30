package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Business.Company;
import Menu.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class LoginController {

	private MainController menu;
	
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
    
    public void initiate(MainController menu){
    	this.menu = menu;
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
		else{
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
	
    //loads customer portal scene
	void goToCustomerPortal(String username){
		try {
			AnchorPane pane;
	    	FXMLLoader custPortal = new FXMLLoader(getClass().getResource("CustomerPortal.fxml"));
	    	pane = custPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = custPortal.getController();
			controller.initiate(menu, username);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//loads business portal scene
	void goToBusinessPortal(String username){
		try {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
			controller.initiate(menu);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
