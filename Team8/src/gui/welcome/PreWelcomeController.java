package gui.welcome;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import Main.BookingManagementSystem;
import business.Company;
import business.Employee;
import gui.login.LoginController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import mainController.MainController;

public class PreWelcomeController {

	private MainController menu;
	
	private BookingManagementSystem bms;
	
	private Company company = null;
	
	@FXML
	private AnchorPane rootPane;
	@FXML
	private ToggleGroup group;
	@FXML
	private Rectangle rect = new Rectangle(200, 200);
	@FXML
	private ScrollPane s1 = new ScrollPane();
	
	public void initiate(BookingManagementSystem bms){
		this.bms = bms;
		ArrayList<Company> company_list = bms.getCompanyList();
		ArrayList<JFXButton> buttons = new ArrayList<JFXButton>();
		
		group = new ToggleGroup();
		s1.setPrefSize(120, 120);
		s1.setContent(rect);
		
		int counter =0;
		if(company_list.size() >0) {
			for(Company comp : company_list) {
				JFXRadioButton b = new JFXRadioButton();
				b.setUserData(comp);
				b.setText(comp.getName());
				b.setStyle("-fx-text-fill: white");
				b.setFont(Font.font(16));
				b.setLayoutX(640);
				//b.setLayoutY(184+(counter*20));
				b.setLayoutY(194+(counter*20));
				b.setToggleGroup(group);
				//b.setOnAction(e ->company = (Company) e.getSource());
				rootPane.getChildren().add(b);
				//buttons.add(b);
				counter++;
			}
		}
    }
	
	@FXML
    void goToLogin(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
    	
    	JFXRadioButton selectedRadioButton = (JFXRadioButton) group.getSelectedToggle();
    	if(selectedRadioButton == null)
    	{
    		//put an error dialog
    		System.out.println("error");
    	}
    	else
    	{
    		company = (Company) selectedRadioButton.getUserData();
    	}
		controller.initiate(company, bms);
    }
	
	@FXML
    void Login(ActionEvent event) throws IOException{
		
    }
}
