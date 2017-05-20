package gui.portal;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import business.Company;
import gui.register.PendingRegistrationController;
import gui.welcome.WelcomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class AdminPController {
	
	private MainController menu;
	private BookingManagementSystem bms;
	
	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXButton own_nBEmployee;

	@FXML
	private JFXButton gotoLogout;
	
	@FXML
    private Label logoText;
	
	public void initiate(BookingManagementSystem bms) {
		menu = bms.getMenu();
		this.bms = bms;
	}
	
	//go to add business form
	@FXML
	void addBusiness(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addBuss = new FXMLLoader(getClass().getResource("../register/PendingRegistration.fxml"));
    	pane = addBuss.load();
    	rootPane.getChildren().setAll(pane);
    	PendingRegistrationController controller = addBuss.getController();
		controller.initiate(bms);
	}
	
	//logout
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../welcome/Welcome.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	WelcomeController controller = login.getController();
		controller.initiate(bms);
	}
}
