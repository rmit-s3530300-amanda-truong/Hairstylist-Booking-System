package team8.bms.gui.portal;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import team8.bms.BookingManagementSystem;
import team8.bms.gui.WelcomeController;
import team8.bms.gui.register.PendingRegistrationController;
public class AdminPController {
	
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
		this.bms = bms;
	}
	
	//go to add business form
	@FXML
	void addBusiness(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader addBuss = new FXMLLoader(getClass().getResource("/PendingRegistration.fxml"));
    	pane = addBuss.load();
    	rootPane.getChildren().setAll(pane);
    	PendingRegistrationController controller = addBuss.getController();
		controller.initiate(bms);
	}
	
	//logout
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/Welcome.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	WelcomeController controller = login.getController();
		controller.initiate(bms);
	}
}
