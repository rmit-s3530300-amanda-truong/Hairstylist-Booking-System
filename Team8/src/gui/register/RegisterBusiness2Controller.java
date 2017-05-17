package gui.register;

import java.io.IOException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Main.BookingManagementSystem;
import business.Company;
import gui.login.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class RegisterBusiness2Controller {
	
	ObservableList<String> hourList = FXCollections.observableArrayList
			("Please Select","00", "01","02", "03", "04", "05", "06","07", "08", "09", "10", "11",
			"12", "13", "14", "15", "16","17", "18", "19", "20", "21","22", "23");
	ObservableList<String> minuteList = FXCollections.observableArrayList
			("Please Select", "00", "15", "30","45");
	
	private MainController menu;
	private Company comp;
	private BookingManagementSystem bms;
	private String fname;
	private String lname;
	private String username;
	private String password;
	private String mobile;
	private String address;
	
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
	 private JFXComboBox<String> thuehour;

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
	 private Label uniInvalid;
	 
	 @FXML
	 private Label monInvalid;

	 @FXML
	 private Label tueInvalid;
	 
	 @FXML
	 private Label wedInvalid;
	 
	 @FXML
	 private Label thuInvalid;
	 
	 @FXML
	 private Label friInvalid;
	 	
	 @FXML
	 private Label satInvalid;

	 @FXML
	 private Label sunInvalid;
	 
	 @FXML
	 private JFXTextField businessName;

	 @FXML
	 private Label businessNameInvalid;
    
	 @FXML
	 private void initialize(){
		//populating combobox
    	monshour.setItems(hourList);
    	monshour.setValue("08");
    	monehour.setItems(hourList);
    	monehour.setValue("16");
    	tueshour.setItems(hourList);
    	tueshour.setValue("08");
    	tueehour.setItems(hourList);
    	tueehour.setValue("16");
    	wedshour.setItems(hourList);
    	wedshour.setValue("08");
    	wedehour.setItems(hourList);
    	wedehour.setValue("16");
    	thushour.setItems(hourList);
    	thushour.setValue("08");
    	thuehour.setItems(hourList);
    	thuehour.setValue("16");
    	frishour.setItems(hourList);
    	frishour.setValue("08");
    	friehour.setItems(hourList);
    	friehour.setValue("16");
    	satshour.setItems(hourList);
    	satshour.setValue("08");
    	satehour.setItems(hourList);
    	satehour.setValue("16");
    	sunshour.setItems(hourList);
    	sunshour.setValue("08");
    	sunehour.setItems(hourList);
    	sunehour.setValue("16");
    	
    	monsminute.setItems(minuteList);
    	monsminute.setValue("00");
    	moneminute.setItems(minuteList);
    	moneminute.setValue("00");
    	tuesminute.setItems(minuteList);
    	tuesminute.setValue("00");	
    	tueeminute.setItems(minuteList);
    	tueeminute.setValue("00");
    	wedsminute.setItems(minuteList);
    	wedsminute.setValue("00");
    	wedeminute.setItems(minuteList);
    	wedeminute.setValue("00");
    	thusminute.setItems(minuteList);
    	thusminute.setValue("00");
    	thueminute.setItems(minuteList);
    	thueminute.setValue("00");
    	frisminute.setItems(minuteList);
    	frisminute.setValue("00");
    	frieminute.setItems(minuteList);
    	frieminute.setValue("00");
    	satsminute.setItems(minuteList);
    	satsminute.setValue("00");
    	sateminute.setItems(minuteList);
    	sateminute.setValue("00");
    	sunsminute.setItems(minuteList);
    	sunsminute.setValue("00");
    	suneminute.setItems(minuteList);
    	suneminute.setValue("00");
    }
    
	 public void initiate(Company comp, BookingManagementSystem bms, String fName, String lName, 
    		String uName, String passWord,String mobileNo,String address) {
		this.comp = comp;
		menu = comp.getMenu();
		this.bms = bms;
		this.fname = fName;
		this.lname = lName;
		this.username = uName;
		this.password = passWord;
		this.mobile = mobileNo;
		this.address = address;
	}
    
    @FXML
    void backRegister(ActionEvent event) {

    }

    @FXML
    void goToLogin(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(comp, bms);
    }

    @FXML
    void nextRegister3(ActionEvent event) {

    }
}
