package team8.bms.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import team8.bms.gui.portal.BusinessPController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.business.Employee;
import team8.bms.mainController.MainController;

public class AddEmpController {
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	ObservableList<String> re_stateList = FXCollections.observableArrayList
			("VIC", "NSW","Qld", "WA", "SA", "Tas");
	
	private MainController menu;
	private Company comp;
	
	ArrayList<String> serviceList = new ArrayList<String>();
	
	ArrayList<JFXCheckBox> serviceBoxes;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

    @FXML
    private JFXTextField re_fname;

    @FXML
    private JFXTextField re_lname;

    @FXML
    private JFXTextField re_mobile;

    @FXML
    private JFXTextField re_address;

    @FXML
    private JFXTextField re_suburb;

    @FXML
    private JFXTextField re_postcode;

    @FXML
    private JFXComboBox<String> re_state;

    @FXML
    private JFXButton addEmployee;

    @FXML
    private Label invalidfname;

    @FXML
    private Label invalidlname;

    @FXML
    private Label invalidmobile;

    @FXML
    private Label invalidaddressline;

    @FXML
    private Label invalidsuburb;

    @FXML
    private Label invalidpostcode;

    @FXML
    private Label username;
    
    @FXML
    private Label logoText;

    @FXML
    private Label invalidservices;
    
    @FXML
    private void initialize(){
    	re_state.setValue("VIC");
    	re_state.setItems(re_stateList);   	
    }

    private BookingManagementSystem bms;
    
	public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.bms = bms;
		username.setText(menu.getEmpUname());
		logoText.setText(comp.getName().toUpperCase());
		int counter = 0;
		ArrayList<String> services_list = comp.getService();
		serviceBoxes = new ArrayList<JFXCheckBox>();
		
		if(services_list.size() > 0) {
			for(String serv : services_list) {
				JFXCheckBox b = new JFXCheckBox();
				b.setUserData(serv);
				b.setText(serv);
				b.setStyle("-fx-text-fill: white");
				b.setFont(Font.font(16));
				if(counter <4) {
					b.setLayoutX(383+(counter*153));
					b.setLayoutY(553);
				} else {
					b.setLayoutX(383+((counter-4)*153));
					b.setLayoutY(588);
				}
				rootPane.getChildren().add(b);
				serviceBoxes.add(b);
				counter++;
			}
		}
	}
	
	@FXML
    void addEmp(ActionEvent event) throws IOException{
		serviceList.clear();
		String services = "";
    	String username = menu.getEmpUname();
		String fname = re_fname.getText();
		String lname = re_lname.getText();
		String mobile = re_mobile.getText();
		String address = re_address.getText();
		String suburb = re_suburb.getText();
		String postcode = re_postcode.getText();
		String state = re_state.getPromptText();
		
		boolean fnameValid = false, lnameValid = false, mobileValid = false, 
				suburbValid = false ,zipValid = false, addressLineValid = false, serviceValid = false;
		
		//regex patterns for user input
		String name = "^[a-zA-Z-\\s]*$";
		String mobileNo = "^(?:\\+?61|0)4 ?(?:(?:[01] ?[0-9]|2 ?[0-57-9]|3 ?[1-9]|4 ?[7-9]|5 ?[018]) ?[0-9]|3 ?0 ?[0-5])(?: ?[0-9]){5}$";
		String suburbName = "^([a-zA-Z](\\s?))*$";
		String zipCode = "^[0-9]{4}$";
		String addressLine = "^\\d+\\s[A-z]+\\s[A-z]+";
		
		//checking postcode
		if(menu.validate(postcode, zipCode)){
			zipValid = true;
			LOGGER.info(postcode);
			invalidpostcode.setText("");
		}
		else{
			LOGGER.info("Invalid Postcode");
			invalidpostcode.setText("Invalid Postcode.");
			invalidpostcode.setAlignment(Pos.CENTER_LEFT);
		}
		
		//checking suburb
		if(menu.validate(suburb, suburbName)){
			LOGGER.info(suburb);
			suburbValid = true;
			invalidsuburb.setText("");
		}
		else{
			LOGGER.info("Invalid Suburb");
			invalidsuburb.setText("Invalid Suburb.");
			invalidsuburb.setAlignment(Pos.CENTER_LEFT);
		}
		
		//checking address
		if(menu.validate(address, addressLine)){
			LOGGER.info(address);
			addressLineValid = true;
			invalidaddressline.setText("");
		}
		else{
			LOGGER.info("Invalid Address");
			invalidaddressline.setText("Invalid Address. eg. 1 Smith st");
			invalidaddressline.setAlignment(Pos.CENTER_LEFT);
		}
		
		//checking mobile
		if(menu.validate(mobile, mobileNo)){
			LOGGER.info(mobile);
			mobileValid = true;
			invalidmobile.setText("");
		}
		else{
			LOGGER.info("Invalid Mobile Number");
			invalidmobile.setText("Invalid Mobile Number.");
			invalidmobile.setAlignment(Pos.CENTER_LEFT);
		}
		
		//checking lastname
		if(menu.validate(lname, name)){
			LOGGER.info(lname);
			lnameValid = true;
			invalidlname.setText("");
		}
		else{
			LOGGER.info("Invalid Last Name");
			invalidlname.setText("Invalid Last Name.");
			invalidlname.setAlignment(Pos.CENTER_LEFT);
		}
		
		//checking firstname
		if(menu.validate(fname, name)){
			LOGGER.info(fname);
			fnameValid = true;
			invalidfname.setText("");
		}
		else{
			LOGGER.info("Invalid First Name");
			invalidfname.setText("Invalid First Name.");
			invalidfname.setAlignment(Pos.CENTER_LEFT);
		}
		
		Boolean selected = false;
		for(JFXCheckBox serv : serviceBoxes) {
			if(serv.isSelected()) {
				selected = true;
				serviceList.add((String) serv.getUserData());
			}
		}
		if(selected) {
			invalidservices.setText("");
			serviceValid = true;
			
			//coverting arraylist to string
			for(int i = 0; i < serviceList.size(); i++) {
				String s = serviceList.get(i);
				if(i == serviceList.size() - 1){
					services += s;
				} 
				else {
					services += s+", ";
				}
			}	
		}
		else{
			invalidservices.setText("Please select at least one service.");
			invalidservices.setAlignment(Pos.CENTER_LEFT);
		}
		
		//sending info to database and hashmap
		if(fnameValid && lnameValid && mobileValid && addressLineValid && suburbValid && zipValid && serviceValid){
			String fullAddress = address + ", " + suburb + ", " + state + " "+ postcode;
			menu.addEmployee(username, fname, lname, mobile, fullAddress, services);
			comp.addEmployee(new Employee(username, comp.getName(), fname, lname, serviceList));
			goToPortal();
		}
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
		LOGGER.info("Logout");
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("/BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(comp, bms);
    	LOGGER.info("Go to portal");
    }
}
