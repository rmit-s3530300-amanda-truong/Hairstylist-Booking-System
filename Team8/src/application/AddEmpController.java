package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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

public class AddEmpController {
	
	ObservableList<String> re_stateList = FXCollections.observableArrayList
			("VIC", "NSW","Queensland", "WA", "SA", "Tasmania");
	
	private Menu menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

    @FXML
    private JFXTextField re_fname;

    @FXML
    private JFXTextField re_lname;

    @FXML
    private JFXTextField re_mobile;

    @FXML
    private JFXTextField re_address;

    @FXML
    private JFXTextField re_suburb;

    @FXML
    private JFXTextField re_postcode;

    @FXML
    private JFXComboBox<String> re_state;

    @FXML
    private JFXButton addEmployee;

    @FXML
    private Label invalid;

    @FXML
    private Label username;
    
    @FXML
    private void initialize(){
    	re_state.setValue("VIC");
    	re_state.setItems(re_stateList);   	
    }

	public void initiate(Menu menu) {
		this.menu = menu;
		username.setText(menu.getEmpUname());
	}
	
	@FXML
    void addEmp(ActionEvent event) throws IOException{
    	String username = menu.getEmpUname();
		String fname = re_fname.getText();
		String lname = re_lname.getText();
		String mobile = re_mobile.getText();
		String address = re_address.getText();
		String suburb = re_suburb.getText();
		String postcode = re_postcode.getText();
		String state = re_state.getPromptText();
		
		boolean fnameValid = false, lnameValid = false, mobileValid = false, suburbValid = false ,zipValid = false, addressLineValid = false;
		
		//regex patterns for user input
		String name = "^[a-zA-Z-//s]*$";
		String mobileNo = "^(?:\\+?61|0)4 ?(?:(?:[01] ?[0-9]|2 ?[0-57-9]|3 ?[1-9]|4 ?[7-9]|5 ?[018]) ?[0-9]|3 ?0 ?[0-5])(?: ?[0-9]){5}$";
		String suburbName = "^([a-zA-Z](\\s?))*$";
		String zipCode = "^[0-9]{4}$";
		String addressLine = "^\\d+\\s[A-z]+\\s[A-z]+";
		
		//checking postcode
		if(menu.validate(postcode, zipCode)){
			zipValid = true;
		}
		else{
			invalid.setText("Postcode is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
		
		//checking suburb
		if(menu.validate(suburb, suburbName)){
			suburbValid = true;
		}
		else{
			invalid.setText("Suburb name is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
		
		//checking address
		if(menu.validate(address, addressLine)){
			addressLineValid = true;
		}
		else{
			invalid.setText("Address Line is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
		
		//checking mobile
		if(menu.validate(mobile, mobileNo)){
			mobileValid = true;
		}
		else{
			invalid.setText("Mobile number is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
		
		//checking lastname
		if(menu.validate(lname, name)){
			lnameValid = true;
		}
		else{
			invalid.setText("Last Name is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
		
		//checking firstname
		if(menu.validate(fname, name)){
			fnameValid = true;
		}
		else{
			invalid.setText("First Name is invalid. Please try again");
			invalid.setAlignment(Pos.CENTER);
		}
		
		
		//sending info to database
		if(fnameValid && lnameValid && mobileValid && addressLineValid && suburbValid && zipValid){
			invalid.setText("");
			String fullAddress = address + ", " + suburb + ", " + state + " "+ postcode;
			String services = "femaleCut, maleCut, femaleDye, maleDye, femalePerm, malePerm, femaleWash, maleWash";
			menu.addEmployee(username, fname, lname, mobile, fullAddress, services);
			goToPortal();
		}
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
    }
}
