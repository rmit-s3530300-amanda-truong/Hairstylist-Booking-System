package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Business.Company;
import Menu.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class RegisterController {

	ObservableList<String> rc_stateList = FXCollections.observableArrayList
			("VIC", "NSW","Queensland", "WA", "SA", "Tasmania");
	
	private Menu menu;
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXTextField rc_fname;

    @FXML
    private JFXPasswordField rc_password;

    @FXML
    private JFXButton registerAction;

    @FXML
    private JFXTextField rc_username;

    @FXML
    private JFXTextField rc_lname;

    @FXML
    private JFXPasswordField rc_confirmpass;

    @FXML
    private JFXTextField rc_mobile;

    @FXML
    private JFXTextField rc_address;

    @FXML
    private JFXTextField rc_suburb;

    @FXML
    private JFXTextField rc_postcode;

    @FXML
    private JFXComboBox<String> rc_state;

    @FXML
    private JFXButton gotoRegister;

    @FXML
    private JFXButton gotoLogin;
    
    @FXML
    private Label invalid_fn;
    
    @FXML
    private Label invalid_ln;
    
    @FXML
    private Label invalid_add;
    
    @FXML
    private Label invalid_num;
    
    @FXML
    private Label invalid_pass;
    
    @FXML
    private Label invalid_pass2;
    
    @FXML
    private Label invalid_pc;
    
    @FXML
    private Label invalid_sub;
    
    @FXML
    private Label invalid_un;
    
    @FXML
    private void initialize(){
    	rc_state.setValue("VIC");
    	rc_state.setItems(rc_stateList);
    }

	public void initiate(Menu menu) {
		this.menu = menu;
	}
	
	@FXML
    void register(ActionEvent event) throws IOException{
		String username = rc_username.getText();
		String fname = rc_fname.getText();
		String lname = rc_lname.getText();
		String password = rc_password.getText();
		String confirmPass = rc_confirmpass.getText();
		String mobile = rc_mobile.getText();
		String address = rc_address.getText();
		String suburb = rc_suburb.getText();
		String postcode = rc_postcode.getText();
		String state = rc_state.getValue();
		
		boolean unameValid = false, fnameValid = false, lnameValid = false, passValid = false, 
				mobileValid = false, suburbValid = false ,zipValid = false, addressLineValid = false;
		
		//regex patterns for user input
		String uname = "^(?=^.{5,}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$";
		String name = "^[a-zA-Z-//s]*$";
		String pass = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=\\S+$)(?=.*[@#$%^&+=]).{6,}$";
		String mobileNo = "^(?:\\+?61|0)4 ?(?:(?:[01] ?[0-9]|2 ?[0-57-9]|3 ?[1-9]|4 ?[7-9]|5 ?[018]) ?[0-9]|3 ?0 ?[0-5])(?: ?[0-9]){5}$";
		String suburbName = "^([a-zA-Z](\\s?))*$";
		String zipCode = "^[0-9]{4}$";
		String addressLine = "^\\d+\\s[A-z]+\\s[A-z]+";
		
		//checking postcode
		if(menu.validate(postcode, zipCode)){
			zipValid = true;
			invalid_pc.setText("");
		}
		else{
			invalid_pc.setText("Invalid Postcode.");
			invalid_pc.setAlignment(Pos.CENTER);
		}
		
		//checking suburb
		if(menu.validate(suburb, suburbName)){
			suburbValid = true;
			invalid_sub.setText("");
		}
		else{
			invalid_sub.setText("Invalid Suburb.");
			invalid_sub.setAlignment(Pos.CENTER);
		}
		
		//checking address
		if(menu.validate(address, addressLine)){
			addressLineValid = true;
			invalid_add.setText("");
		}
		else{
			invalid_add.setText("Invalid Address. eg. 1 Smith st");
			invalid_add.setAlignment(Pos.CENTER);
		}
		
		//checking mobile
		if(menu.validate(mobile, mobileNo)){
			mobileValid = true;
			invalid_num.setText("");
		}
		else{
			invalid_num.setText("Invalid Number");
			invalid_num.setAlignment(Pos.CENTER);
		}
		
		//checking password
		if(menu.validate(password, pass)){
			if(password.equals(confirmPass)){
				passValid = true;
				invalid_pass.setText("");
				invalid_pass2.setText("");
			}
			else{
				invalid_pass.setText("");
				invalid_pass2.setText("Password doesn't match.");
				invalid_pass2.setAlignment(Pos.CENTER);
			}
		}
		else{
			invalid_pass2.setText("");
			invalid_pass.setText("Invalid. Must be at least 6 characters and contain at least one uppercase, number and symbol.");
			invalid_pass.setAlignment(Pos.CENTER_LEFT);
		}
		
		//checking username input
		if(menu.validate(username, uname)){
			if(menu.uniqueUname(username)){
				unameValid = true;
				invalid_un.setText("");
			}
			else{
				invalid_un.setText("Username is already taken.");
				invalid_un.setAlignment(Pos.CENTER);
			}
		}
		else{
			invalid_un.setText("Invalid Username. Must contain least 5 characters.");
		}
		
		//checking lastname
		if(menu.validate(lname, name)){
			lnameValid = true;
			invalid_ln.setText("");
		}
		else{
			invalid_ln.setText("Invalid Last Name");
			invalid_ln.setAlignment(Pos.CENTER);
		}
		
		//checking firstname
		if(menu.validate(fname, name)){
			fnameValid = true;
			invalid_fn.setText("");
		}
		else{
			invalid_fn.setText("Invalid First Name");
			invalid_fn.setAlignment(Pos.CENTER);
		}
		
		//sending info to database
		if(fnameValid && lnameValid && unameValid && passValid && mobileValid && addressLineValid && suburbValid && zipValid){
			String fullAddress = address + ", " + suburb + ", " + state + " "+ postcode;
			menu.registerCustomer(username, fname, lname, password, mobile, fullAddress);
			goToPortal();
		}
	}
	
	@FXML
    void goToLogin(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
    }
	
    void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader customerPortal = new FXMLLoader(getClass().getResource("CustomerPortal.fxml"));
    	pane = customerPortal.load();
    	rootPane.getChildren().setAll(pane);
    	CustomerPController controller = customerPortal.getController();
    	controller.initiate(menu, rc_username.getText());
    }
}
