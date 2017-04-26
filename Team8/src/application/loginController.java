package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Business.Company;
import Database.AvailabilityDatabase;
import Database.CompanyDatabase;
import Database.CustomerDatabase;
import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class loginController {

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
    
    CustomerDatabase customerDb = new CustomerDatabase();
    CompanyDatabase companyDb = new CompanyDatabase();
    AvailabilityDatabase availDb = new AvailabilityDatabase();
    Company comp = new Company();
    Menu m1 = new Menu(comp, customerDb, companyDb, availDb);
  //comp.retrieveDatabaseInfo(customerDb, companyDb,availDb));
  //comp.getCalendar().updateCalendar(comp.getEmployeeList());
    
    @FXML
    void login(ActionEvent event) throws IOException{
    	String username = l_username.getText();
    	String password = l_password.getText();
    	
    	if(m1.authenticate(username, password).equals("customer")){
    		AnchorPane pane = FXMLLoader.load(getClass().getResource("CustomerPortal.fxml"));
	    	rootPane.getChildren().setAll(pane);
    	}
    	else if (m1.authenticate(username, password).equals("business")){
    		AnchorPane pane = FXMLLoader.load(getClass().getResource("BusinessPortal.fxml"));
	    	rootPane.getChildren().setAll(pane);
    	}
		else{
			//when user input is wrong
			invalid_id_password.setText("Invalid username or password. Please try again");
			l_username.setText("");
			l_password.setText("");
		}
    }
    
    @FXML
    void goToRegister(ActionEvent event) throws IOException{
    	AnchorPane pane = FXMLLoader.load(getClass().getResource("RegisterCustomer.fxml"));
    	rootPane.getChildren().setAll(pane);
    }
	
}
