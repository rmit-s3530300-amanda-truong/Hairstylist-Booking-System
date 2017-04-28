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

public class AddEmpController {
	
	ObservableList<String> re_stateList = FXCollections.observableArrayList
			("VIC", "NSW","Queensland", "WA", "SA", "Tasmania");
	
	private Menu menu;
	
	private Company comp;
	
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

	public void initiate(Menu menu, Company comp) {
		this.menu = menu;
		this.comp = comp;
	}
	
	@FXML
    void addEmp(ActionEvent event) throws IOException{
		String uname = username.getText();
		String fname = re_fname.getText();
		String lname = re_lname.getText();
		String mobile = re_mobile.getText();
		String address = re_address.getText();
		String suburb = re_suburb.getText();
		String postcode = re_postcode.getText();
		String state = re_state.getPromptText();
		
		boolean unValid = false, fNameValid = false, lNameValid = false, mobileValid = false, 
				suburbValid = false ,zipValid = false;
		
//		//checking firstname
//		if(menu.validName(fname)){
//			fNameValid = true;
//		}
//		else{
//			invalid.setText("First Name is invalid. Please try again");
//		}
//		
//		//checking lastname
//		if(menu.validName(lname)){
//			lNameValid = true;
//		}
//		else{
//			invalid.setText("Last Name is invalid. Please try again");
//		}
//		
//		//checking mobile validation
//		if(menu.validMobile(mobile)){
//			mobileValid = true;
//		}
//		else{
//			invalid.setText("mobile number is invalid. Please try again");
//		}
//		
//		//checking suburb validation
//		if(menu.validSuburb(suburb)){
//			suburbValid = true;
//		}
//		else{
//			invalid.setText("suburb is invalid. Please try again");
//		}
//		
//		//checking postcode validation
//		if(menu.validZip(postcode)){
//			zipValid = true;
//		}
//		else{
//			invalid.setText("postcode is invalid. Please try again");
//		}
//		
//		//sending info to database
//		if(fNameValid && lNameValid && unValid && mobileValid && suburbValid && zipValid){
//			invalid.setText("");
//			String fullAddress = address + "," + suburb + ", " + state + " "+ postcode;
//			System.out.println(fname + "\n" + lname + "\n" + mobile + "\n" + fullAddress );
//			
			//sends data to database.
//			menu.addNewEmployee(uname, fname, lname, mobile, fullAddress);
			goToPortal();
//		}
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu, comp);
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu, comp);
    }
}
