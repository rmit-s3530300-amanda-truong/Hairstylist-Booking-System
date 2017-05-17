package gui.register;

import java.io.IOException;

import Main.BookingManagementSystem;
import business.Company;
import gui.login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class RegisterBusiness4Controller {

	private MainController menu;
	private Company comp;
	private BookingManagementSystem bms;
	
    @FXML
    private AnchorPane rootPane;
    
    public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.bms = bms;
	}

    @FXML
    void backRegister3(ActionEvent event) {
    	
    }

    @FXML
    void submit(ActionEvent event) {

    }
    
    @FXML
    void goToLogin(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(comp, bms);
    }

}
