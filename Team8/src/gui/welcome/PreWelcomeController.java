package gui.welcome;

import java.io.IOException;

import gui.login.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class PreWelcomeController {

	private MainController menu;
	
	private String company;
	
	@FXML
	private AnchorPane rootPane;
	
	public void initiate(MainController menu){
		this.menu = menu;
    }
	
	@FXML
    void goToLogin(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu);
    }
	
	@FXML
    void Login(ActionEvent event) throws IOException{
		
    }
}
