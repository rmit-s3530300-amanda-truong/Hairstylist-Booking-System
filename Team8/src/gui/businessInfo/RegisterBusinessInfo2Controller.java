package gui.businessInfo;

import java.io.IOException;

import com.jfoenix.controls.JFXComboBox;

import Main.BookingManagementSystem;
import business.Company;
import gui.welcome.PreWelcomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class RegisterBusinessInfo2Controller {
	
	private MainController menu;
	private Company comp;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXComboBox<String> monshour;

    @FXML
    private JFXComboBox<String> monsminute;

    @FXML
    private JFXComboBox<String> monehour;

    @FXML
    private JFXComboBox<String> moneminute;

    @FXML
    private JFXComboBox<String> tueshour;

    @FXML
    private JFXComboBox<String> tuesminute;

    @FXML
    private JFXComboBox<String> tueehour;

    @FXML
    private JFXComboBox<String> tueeminute;

    @FXML
    private JFXComboBox<String> wedshour;

    @FXML
    private JFXComboBox<String> wedsminute;

    @FXML
    private JFXComboBox<String> wedehour;

    @FXML
    private JFXComboBox<String> wedeminute;

    @FXML
    private JFXComboBox<String> thushour;

    @FXML
    private JFXComboBox<String> thusminute;

    @FXML
    private JFXComboBox<String> thueehour;

    @FXML
    private JFXComboBox<String> thueminute;

    @FXML
    private JFXComboBox<String> frishour;

    @FXML
    private JFXComboBox<String> frisminute;

    @FXML
    private JFXComboBox<String> friehour;

    @FXML
    private JFXComboBox<String> frieminute;

    @FXML
    private JFXComboBox<String> satshour;

    @FXML
    private JFXComboBox<String> satsminute;

    @FXML
    private JFXComboBox<String> satehour;

    @FXML
    private JFXComboBox<String> sateminute;

    @FXML
    private JFXComboBox<String> sunshour;

    @FXML
    private JFXComboBox<String> sunsminute;

    @FXML
    private JFXComboBox<String> sunehour;

    @FXML
    private JFXComboBox<String> suneminute;

    @FXML
    private Label logoText;
    
    private BookingManagementSystem bms;

    @FXML
    private void initialize(){
    	
    }
    
    public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.bms = bms;
	}

    @FXML
    void goToLogout(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader logout = new FXMLLoader(getClass().getResource("../welcome/PreWelcome.fxml"));
    	pane = logout.load();
    	rootPane.getChildren().setAll(pane);
    	PreWelcomeController controller = logout.getController();
		controller.initiate(bms);
    }

    @FXML
    void goToPortal(ActionEvent event) {

    }

    @FXML
    void page1(ActionEvent event) {

    }

    @FXML
    void page3(ActionEvent event) {

    }

}
