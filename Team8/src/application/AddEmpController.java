package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Business.Company;
import Business.Employee;
import Business.Employee.Service;
import Menu.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class AddEmpController {
	private Logger LOGGER = Logger.getLogger("InfoLogging");
	
	ObservableList<String> re_stateList = FXCollections.observableArrayList
			("VIC", "NSW","Queensland", "WA", "SA", "Tasmania");
	
	private MainController menu;
	Company comp = new Company();
	
	ArrayList<Service> serviceList = new ArrayList<Service>();
	
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
    private JFXCheckBox sfcut;

    @FXML
    private JFXCheckBox smcut;

    @FXML
    private JFXCheckBox sfwash;

    @FXML
    private JFXCheckBox smwash;

    @FXML
    private JFXCheckBox sfperm;

    @FXML
    private JFXCheckBox smperm;

    @FXML
    private JFXCheckBox sfdye;

    @FXML
    private JFXCheckBox smdye;

    @FXML
    private Label invalidservices;
    
    @FXML
    private void initialize(){
    	re_state.setValue("VIC");
    	re_state.setItems(re_stateList);   	
    }

	public void initiate(MainController menu) {
		this.menu = menu;
		username.setText(menu.getEmpUname());
		sfcut.setUserData(Service.femaleCut);
		smcut.setUserData(Service.maleCut);
		sfwash.setUserData(Service.femaleWash);
		smwash.setUserData(Service.maleWash);
		sfperm.setUserData(Service.femalePerm);
		smperm.setUserData(Service.malePerm);
		sfdye.setUserData(Service.femaleDye);
		smdye.setUserData(Service.maleDye);
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
		String name = "^[a-zA-Z-//s]*$";
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
			invalidfname.setText("nvalid First Name.");
			invalidfname.setAlignment(Pos.CENTER_LEFT);
		}
		
		//checking services
		if(sfcut.isSelected() || smcut.isSelected() || sfwash.isSelected() || smwash.isSelected() || sfperm.isSelected() 
				|| smperm.isSelected() || sfdye.isSelected() || smdye.isSelected()){
			invalidservices.setText("");
			if(sfcut.isSelected()){
				serviceList.add((Service) sfcut.getUserData());
			}
			if(smcut.isSelected()){
				serviceList.add((Service) smcut.getUserData());
			}
			if(sfwash.isSelected()){
				serviceList.add((Service) sfwash.getUserData());
			}
			if(smwash.isSelected()){
				serviceList.add((Service) smwash.getUserData());
			}
			if(sfperm.isSelected()){
				serviceList.add((Service) sfperm.getUserData());
			}
			if(smperm.isSelected()){
				serviceList.add((Service) smperm.getUserData());
			}
			if(sfdye.isSelected()){
				serviceList.add((Service) sfdye.getUserData());
			}
			if(smdye.isSelected()){
				serviceList.add((Service) smdye.getUserData());
			}
			invalidservices.setText("");
			serviceValid = true;
			
			//coverting arraylist to string
			for(int i = 0; i < serviceList.size(); i++) {
				Service s = serviceList.get(i);
				if(i == serviceList.size() - 1){
					services += s;
				} 
				else {
					services += s+", ";
				}
			}	
		}
		else{
			invalidservices.setText("Please select atleastone service.");
			invalidservices.setAlignment(Pos.CENTER_LEFT);
		}
		
		//sending info to database and hashmap
		if(fnameValid && lnameValid && mobileValid && addressLineValid && suburbValid && zipValid && serviceValid){
			String fullAddress = address + ", " + suburb + ", " + state + " "+ postcode;
			menu.addEmployee(username, fname, lname, mobile, fullAddress, services);
			comp.addEmployee(new Employee(username, fname, lname, serviceList));
			goToPortal();
		}
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(menu);
		LOGGER.info("Logout");
    }
	
	@FXML
	void goToPortal() throws IOException{
    	AnchorPane pane;
    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
    	pane = bussPortal.load();
    	rootPane.getChildren().setAll(pane);
    	BusinessPController controller = bussPortal.getController();
    	controller.initiate(menu);
    	LOGGER.info("Go to portal");
    }
}
