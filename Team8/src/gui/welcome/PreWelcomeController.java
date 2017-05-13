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
import javafx.scene.control.Toggle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import mainController.MainController;

public class PreWelcomeController {

	private MainController menu;
	
	private BookingManagementSystem bms;
	
	private Company company=null;
	
	@FXML
	private AnchorPane rootPane;
	
	public void initiate(MainController menu, BookingManagementSystem bms){
		this.menu = menu;
		this.bms = bms;
		ArrayList<Company> company_list = bms.getCompanyList();
		ArrayList<JFXButton> buttons = new ArrayList<JFXButton>();
		int counter =0;
		if(company_list.size() >0) {
			for(Company comp : company_list) {
				JFXButton b = new JFXButton();
				b.setUserData(comp);
				b.setText(comp.toString());
				b.setStyle("-fx-text-fill: white");
				b.setFont(Font.font(16));
				b.setLayoutX(640);
				b.setLayoutY(184+(counter*20));
				b.setOnAction(e ->company = (Company) e.getSource());
				rootPane.getChildren().add(b);
				buttons.add(b);
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
		controller.initiate(menu);
    }
	
	@FXML
    void Login(ActionEvent event) throws IOException{
		
    }
}
