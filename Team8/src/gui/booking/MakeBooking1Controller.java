package gui.booking;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import business.Customer;
import calendar.Booking;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class MakeBooking1Controller {
	private MainController menu;
	
	private Company comp;
	private HashMap<String, Customer> custMap;
	private String custUser;
	ObservableList<String> custList = FXCollections.observableArrayList();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	private JFXComboBox<String> username;
	
	@FXML
    private Label logoText;
	
	private BookingManagementSystem bms;
	
	@FXML
	private Label invalid;

	public void initiate(Company comp, BookingManagementSystem bms) {
		menu = bms.getMenu();
		this.comp = comp;
		this.bms = bms;
		logoText.setText(comp.getName().toUpperCase());
		custMap = comp.getCustList();
		if(custMap.size() == 0) {
			username.setPromptText("No Customers");
			username.setDisable(true);
		} 
		else 
		{
			username.setDisable(false);
			username.setPromptText("PLEASE SELECT");
			for(Entry<String, Customer> entry: custMap.entrySet()) {
				custList.add(entry.getKey());
			}
			username.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if(custList.size()>0) {
						 custUser = newValue.toString();
						invalid.setText("");
					}
				}
			});
			username.setItems(custList);
			username.autosize();
		}
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {

		Customer c = custMap.get(custUser);
		if(c == null) {
			invalid.setText("Invalid Customer ID.");
			invalid.setAlignment(Pos.CENTER);
		} else {
			AnchorPane pane;
	    	FXMLLoader m2 = new FXMLLoader(getClass().getResource("MakeBooking2.fxml"));
	    	pane = m2.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking2Controller controller = m2.getController();
	    	controller.initiate(comp, custUser, "business", bms);
		}
	}
	
	@FXML
	void Home(ActionEvent event) throws IOException {
		goToPortal();
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/gui/login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("/gui/portal/BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(comp, bms);
    }
}
