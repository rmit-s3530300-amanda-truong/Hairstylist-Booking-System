package team8.bms.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import team8.bms.gui.portal.AdminPController;
import team8.bms.gui.portal.BusinessPController;
import team8.bms.gui.portal.CustomerPController;
import team8.bms.gui.register.RegisterController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.mainController.MainController;

public class LoginController {

	private MainController menu;
	private Company comp;
	
	ObservableList<String> businessList = FXCollections.observableArrayList();
	
	String business=null;
	
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
    
    @FXML
    private JFXComboBox<String> chooseBusiness;
    
    private BookingManagementSystem bms;
    
    @FXML
    private void initialize(){
    	chooseBusiness.setItems(businessList);
    }
    
    public void initiate(final BookingManagementSystem bms){
    	this.bms = bms;
		ArrayList<Company> company_list = bms.getCompanyList();
		if(company_list.size() >0) {
			for(Company company : company_list) {
				if(company.getStatus().equals("verified"))
				{
					businessList.add(company.getName());	
				}
			}
		}
		
		chooseBusiness.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				comp = bms.getCompany(newValue.toString());
				menu = comp.getMenu();
				business = newValue.toString();
			}
		});
    }

    @FXML
    void login(ActionEvent event){
    	String username = l_username.getText();
    	String password = l_password.getText();
    	
    	
    	if(business == null){
    		if(username.equals("admin") && password.equals("admin")){
    			goToAdminPortal();
    		}
    		else{
    			invalid_id_password.setText("Invalid username or password. Please try again");
    			invalid_id_password.setAlignment(Pos.CENTER);
    		}
    	}
    	else{
    		if(menu.authenticate(username, password, business).equals("customer")){
    			comp = bms.getCompany(business);
    			goToCustomerPortal(username);
        	}
        	else if (menu.authenticate(username, password, business).equals("business")){
        		comp = bms.getCompany(business);
        		goToBusinessPortal(username);
        	}
        	else if (username.equals("admin") && password.equals("admin")){
        		goToAdminPortal();
        	}
    		else{
    			invalid_id_password.setText("Invalid username or password. Please try again");
    			invalid_id_password.setAlignment(Pos.CENTER);
    		}
    	}
    }
    
    @FXML
    void goToRegister(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("/Register.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterController controller = register.getController();
		controller.initiate(bms);
    }
	
    //loads customer portal scene
	void goToCustomerPortal(String username){
		try {
			AnchorPane pane;
	    	FXMLLoader custPortal = new FXMLLoader(getClass().getResource("/CustomerPortal.fxml"));
	    	pane = custPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = custPortal.getController();
			controller.initiate(comp, username, bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//loads business portal scene
	void goToBusinessPortal(String username){
		try {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
			controller.initiate(comp, bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void goToAdminPortal(){
		try {
			AnchorPane pane;
	    	FXMLLoader adminPortal = new FXMLLoader(getClass().getResource("/AdminPortal.fxml"));
	    	pane = adminPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	AdminPController controller = adminPortal.getController();
			controller.initiate(bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
