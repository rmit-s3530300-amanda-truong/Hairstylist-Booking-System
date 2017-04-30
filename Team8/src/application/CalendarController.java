package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import Business.Company;
import Menu.Menu;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CalendarController {

	private Menu menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

    @FXML
    private JFXButton viewMorning;
    
    @FXML
    private JFXButton viewAfternoon;
    
	@FXML
	private JFXButton returnButton;
    
    @FXML
    private void initialize(){
    }

	public void initiate(Menu menu) {
		this.menu = menu;
	}
	
	@FXML
    void clickMorning(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader view_morning = new FXMLLoader(getClass().getResource("ViewMorningCalendar.fxml"));
    	pane = view_morning.load();
    	rootPane.getChildren().setAll(pane);
    	ViewMorningController controller = view_morning.getController();
		controller.initiate(menu);
	}
	
	@FXML
	void clickAfternoon(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader view_afternoon = new FXMLLoader(getClass().getResource("ViewAfternoonCalendar.fxml"));
    	pane = view_afternoon.load();
    	rootPane.getChildren().setAll(pane);
    	ViewAfternoonController controller = view_afternoon.getController();
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
