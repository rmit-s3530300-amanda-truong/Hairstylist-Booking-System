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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class RegisterController {

	ObservableList<String> rc_stateList = FXCollections.observableArrayList
			("VIC", "NSW","Queensland", "WA", "SA", "Tasmania");
	
	private Menu menu;
	
	private Company comp;
	
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
    private Label invalid;
    
    @FXML
    private void initialize(){
    	rc_state.setValue("VIC");
    	rc_state.setItems(rc_stateList);
    }

	public void initiate(Menu menu, Company comp) {
		this.menu = menu;
		this.comp = comp;
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
		String state = rc_state.getPromptText();
		
		boolean unValid = false, fNameValid = false, lNameValid = false, passValid = false, 
				mobileValid = false, suburbValid = false ,zipValid = false, passconfirm = false;
		
		//checking firstname
		if(menu.validName(fname)){
			invalid.setText("");
			fNameValid = true;
		}
		else{
			invalid.setText("First Name is invalid. Please try again");
		}
		
		//checking lastname
		if(menu.validName(lname)){
			invalid.setText("");
			lNameValid = true;
		}
		else{
			invalid.setText("Last Name is invalid. Please try again");
		}
		
		//checking username
		if(menu.validUname(username) && menu.uniqueUname(username)){
			invalid.setText("");
			unValid = true;
		}
		else{
			invalid.setText("username is invalid or already taken. Please try again");
		}
		
		//checking password validation
		if(menu.validPassword(password)){
			invalid.setText("");
			passValid = true;
		}
		else{
			invalid.setText("Password should contatin atleast 1 Capital, 1 symbol, 1 number");
		}
		
		//checking password equals confirm password
		if(password.equals(confirmPass)){
			invalid.setText("");
			passconfirm = true;
		}
		else{
			invalid.setText("Password Doesn't match. Please try again");
		}
		
		//checking mobile validation
		if(menu.validMobile(mobile)){
			invalid.setText("");
			mobileValid = true;
		}
		else{
			invalid.setText("mobile number is invalid. Please try again");
		}
		
		//checking suburb validation
		if(menu.validSuburb(suburb)){
			invalid.setText("");
			suburbValid = true;
		}
		else{
			invalid.setText("suburb is invalid. Please try again");
		}
		
		//checking postcode validation
		if(menu.validZip(postcode)){
			invalid.setText("");
			zipValid = true;
		}
		else{
			invalid.setText("postcode is invalid. Please try again");
		}
		
		//sending info to database
		if(fNameValid && lNameValid && unValid && passValid && passconfirm && mobileValid && suburbValid && zipValid){
			String fullAddress = address + "," + suburb + ", " + state + " "+ postcode;
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
    	controller.initiate(menu);
    }
}
