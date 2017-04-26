package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Menu.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CustomerPController {
	
	private Menu menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private Label cust_name;

    @FXML
    private JFXButton cust_nBooking;

    @FXML
    private JFXButton cust_vCalendar;

    @FXML
    private JFXButton cust_uBooking;

    @FXML
    private JFXButton cust_vHistory;

    @FXML
    private JFXButton gotoLogout;

	public void initiate(Menu menu) {
		this.menu = menu;
	}
	
	@FXML
	void newBooking(ActionEvent event) throws IOException{
		
	}
	
	@FXML
	void viewCalendar(ActionEvent event) throws IOException{
		
	}
	
	@FXML
	void upcomingBooking(ActionEvent event) throws IOException{
		
	}
	
	@FXML
	void viewHistory(ActionEvent event) throws IOException{
		
	}
	
	@FXML
	void goToLogout(ActionEvent event) throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	loginController controller = login.getController();
		controller.initiate(menu);
	}

}
