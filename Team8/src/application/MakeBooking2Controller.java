package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import Business.Employee;
import Business.Employee.Service;
import Menu.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class MakeBooking2Controller {
	private Menu menu;
	
	private String cust_id;
	
	private String services = null;
	
	private String portal;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	@FXML
	private JFXRadioButton femaleCut;
	
	@FXML
	private JFXRadioButton maleCut;
	
	@FXML
	private JFXRadioButton femaleWash;
	
	@FXML
	private JFXRadioButton maleWash;
	
	@FXML
	private JFXRadioButton femaleDye;
	
	@FXML
	private JFXRadioButton maleDye;
	
	@FXML
	private JFXRadioButton femalePerm;
	
	@FXML
	private JFXRadioButton malePerm;

	public void initiate(Menu menu, String cust_id, String portal) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.portal = portal;
		
		femaleWash.setUserData("Female Wash");
		femaleCut.setUserData("Female Cut");
		femaleDye.setUserData("Female Dye");
		femalePerm.setUserData("Female Perm");
		maleWash.setUserData("Male Wash");
		maleDye.setUserData("Male Dye");
		malePerm.setUserData("Male Perm");
		maleCut.setUserData("Male Cut");
		
		femaleWash.setToggleGroup(group);
		femaleDye.setToggleGroup(group);
		femaleCut.setToggleGroup(group);
		femalePerm.setToggleGroup(group);
		maleWash.setToggleGroup(group);
		maleDye.setToggleGroup(group);
		maleCut.setToggleGroup(group);
		malePerm.setToggleGroup(group);

		femaleCut.setSelected(true);
		services = "Female Cut";
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				services = arg2.getUserData().toString();
				
			}
			
		});
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {
		Employee.Service service;
		if(services.equals("Female Cut")) {
			service = Service.femaleCut;
		} 
		else if(services.equals("Male Cut")) {
			service = Service.maleCut;
		}
		else if(services.equals("Female Dye")) {
			service = Service.femaleDye;
		}
		else if(services.equals("Male Dye")) {
			service = Service.maleDye;
		}
		else if(services.equals("Female Perm")) {
			service = Service.femalePerm;
		}
		else if(services.equals("Male Perm")) {
			service = Service.malePerm;
		}
		else if(services.equals("Female Wash")) {
			service = Service.femaleWash;
		}
		else if(services.equals("Male Wash")) {
			service = Service.maleWash;
		} else {
			service = null;
		}
		
		System.out.println(service.toString());
		AnchorPane pane;
    	FXMLLoader mb3 = new FXMLLoader(getClass().getResource("MakeBooking3.fxml"));
    	pane = mb3.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking3Controller controller = mb3.getController();
    	controller.initiate(menu, cust_id, service, portal);
		
		
	}
	@FXML
	void back(ActionEvent event) throws IOException {
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("MakeBooking1.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking1Controller controller = bussPortal.getController();
	    	controller.initiate(menu);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(menu, cust_id);
		}
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
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(menu);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(menu, cust_id);
		}
    }
}
