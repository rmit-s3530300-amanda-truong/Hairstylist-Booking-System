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
		ArrayList<JFXRadioButton> buttons = new ArrayList<JFXRadioButton>();
		
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
				b.setFont(Font.font(18));
				b.setLayoutX(620.0);
				b.setLayoutY(250.0+(counter*20));
				rootPane.getChildren().add(b);
				buttons.add(b);
				counter++;
			}
			for(JFXRadioButton button : buttons) {
				button.setToggleGroup(group);
			}
			buttons.get(0).setSelected(true);
			company = (Company) buttons.get(0).getUserData();		
		}
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				company = (Company) arg2.getUserData();
			}
			
		});
    }
	
	@FXML
    void goToLogin(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
    	
    	if(company == null)
    	{
    		//put an error dialog
    		System.out.println("error");
    	}
    	else
    	{
    		controller.initiate(company, bms);
    	}
    }
	
	@FXML
    void Login(ActionEvent event) throws IOException{
		
    }
}
