package gui.businessInfo;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

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

public class RegisterBusinessInfo1Controller {
	
	ObservableList<String> stateList = FXCollections.observableArrayList
			("VIC", "NSW","Queensland", "WA", "SA", "Tasmania");
	
	private MainController menu;
	private Company comp;
	
	@FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField fname;

    @FXML
    private JFXTextField lname;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXTextField addressline;

    @FXML
    private JFXTextField suburb;

    @FXML
    private JFXTextField postcode;

    @FXML
    private JFXComboBox<String> state;

    @FXML
    private Label invalid_fn;

    @FXML
    private Label invalid_ln;

    @FXML
    private Label invalid_num;

    @FXML
    private Label invalid_add;

    @FXML
    private Label invalid_sub;

    @FXML
    private Label invalid_pc;

    @FXML
    private Label logoText;
    
 private BookingManagementSystem bms;
    
    @FXML
    private void initialize(){
    	state.setValue("VIC");
    	state.setItems(stateList);
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
    void page2(ActionEvent event) {
    	
    }
    
    void goToPage2(ActionEvent event) throws IOException{
    	AnchorPane pane;
    	FXMLLoader info2 = new FXMLLoader(getClass().getResource("RegisterBusinessInfo2.fxml"));
    	pane = info2.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterBusinessInfo2Controller controller = info2.getController();
//		controller.initiate(bms);
    }

}
