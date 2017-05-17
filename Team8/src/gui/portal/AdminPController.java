package gui.portal;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Main.BookingManagementSystem;
import gui.welcome.PreWelcomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	
	public void initiate(MainController menu, BookingManagementSystem bms) {
		this.menu = menu;
		this.bms = bms;
	}
	
	//go to add business form
	@FXML
	void addBusiness(ActionEvent event) throws IOException{
//		AnchorPane pane;
//    	FXMLLoader addBuss = new FXMLLoader(getClass().getResource("../register/RegisterBusiness.fxml"));
//    	pane = addBuss.load();
//    	rootPane.getChildren().setAll(pane);
//    	RegisterBusinessController controller = addBuss.getController();
//		controller.initiate(menu, bms);
	}
	
	//logout
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../welcome/PreWelcome.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	PreWelcomeController controller = login.getController();
		controller.initiate(bms);
	}
}
