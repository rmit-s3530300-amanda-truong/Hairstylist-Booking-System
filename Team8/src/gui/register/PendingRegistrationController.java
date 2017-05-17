package gui.register;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Main.BookingManagementSystem;
import business.Company;
import gui.login.LoginController;
import gui.portal.AdminPController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class PendingRegistrationController {

	private MainController menu;
	private Company comp;
	private BookingManagementSystem bms;
	
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton gotoLogout;

    @FXML
    private Label username;

    @FXML
    private JFXComboBox<?> chooseBusiness;

    @FXML
    private JFXButton registerAction;

    @FXML
    private JFXButton registerAction1;

    @FXML
    private Label invalidbussName;
    
    public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.bms = bms;
	}

    @FXML
    void goToLogout(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(comp, bms);
    }

    @FXML
    void goToPortal(ActionEvent event) {
    	try {
			AnchorPane pane;
	    	FXMLLoader adminPortal = new FXMLLoader(getClass().getResource("../portal/AdminPortal.fxml"));
	    	pane = adminPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	AdminPController controller = adminPortal.getController();
			controller.initiate(comp, bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void register(ActionEvent event) {

    }

}
