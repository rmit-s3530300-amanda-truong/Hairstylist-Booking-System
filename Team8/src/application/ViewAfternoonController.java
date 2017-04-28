package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Menu.Menu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ViewAfternoonController {
	
	private Menu menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

	@FXML
	private JFXButton returnButton;
    
	public void initiate(Menu menu) {
		this.menu = menu;
	}
	
	@FXML
	void goCalendar() throws IOException{
		AnchorPane pane;
    	FXMLLoader viewCalendar = new FXMLLoader(getClass().getResource("ViewCalendar.fxml"));
    	pane = viewCalendar.load();
    	rootPane.getChildren().setAll(pane);
    	CalendarController controller = viewCalendar.getController();
		controller.initiate(menu);
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
    	CustomerPController controller = bussPortal.getController();
    	controller.initiate(menu);
	}
}
