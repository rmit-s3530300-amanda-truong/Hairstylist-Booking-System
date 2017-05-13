package gui.booking;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import Main.BookingManagementSystem;
import business.Company;
import business.Employee;
import gui.login.LoginController;
import gui.portal.BusinessPController;
import gui.portal.CustomerPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import mainController.MainController;

public class MakeBooking2Controller {
	private MainController menu;
	private Company comp;
	
	private String cust_id;
	
	private String service = null;
	
	private String portal;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;
	
	/*@FXML
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
	private JFXRadioButton malePerm;*/
	
	private BookingManagementSystem bms;

	public void initiate(Company comp, String cust_id, String portal, BookingManagementSystem bms) {
		this.comp = comp;
		menu = comp.getMenu();
		this.cust_id = cust_id;
		this.portal = portal;
		this.bms = bms;
		int counter = 0;
		ArrayList<String> services_list = comp.getService();
		ArrayList<JFXRadioButton> buttons = new ArrayList<JFXRadioButton>();
		if(services_list.size() > 0) {
			for(String serv : services_list) {
				JFXRadioButton b = new JFXRadioButton();
				b.setUserData(serv);
				b.setText(serv);
				b.setStyle("-fx-text-fill: white");
				b.setFont(Font.font(16));
				if(counter <4) {
					b.setLayoutX(487.0);
					b.setLayoutY(305.0+(counter*50));
				} else {
					b.setLayoutX(715.0);
					b.setLayoutY(305.0+((counter-4)*50));
				}
				rootPane.getChildren().add(b);
				buttons.add(b);
				counter++;
			}
			for(JFXRadioButton button : buttons) {
				button.setToggleGroup(group);
			}
			buttons.get(0).setSelected(true);
			service = (String)buttons.get(0).getUserData();
		}
		
		/*femaleWash.setUserData("Female Wash");
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
		services = "Female Cut";*/
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				service = arg2.getUserData().toString();
			}
			
		});
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {
		/*Employee.Service service;
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
		}*/
		
		// System.out.println(service.toString());
		AnchorPane pane;
    	FXMLLoader mb3 = new FXMLLoader(getClass().getResource("MakeBooking3.fxml"));
    	pane = mb3.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking3Controller controller = mb3.getController();
    	controller.initiate(comp, cust_id, service, portal, bms);
		
		
	}
	@FXML
	void back(ActionEvent event) throws IOException {
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("MakeBooking1.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking1Controller controller = bussPortal.getController();
	    	controller.initiate(comp, bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("../portal/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(comp, cust_id,bms);
		}
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(comp,bms);
    }
	
	@FXML
	void goToPortal() throws IOException{
		if(portal.equals("business")) {
			AnchorPane pane;
	    	FXMLLoader bussPortal = new FXMLLoader(getClass().getResource("../portal/BusinessPortal.fxml"));
	    	pane = bussPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	BusinessPController controller = bussPortal.getController();
	    	controller.initiate(comp, bms);
		} else {
			AnchorPane pane;
	    	FXMLLoader cusPortal = new FXMLLoader(getClass().getResource("../portal/CustomerPortal.fxml"));
	    	pane = cusPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	CustomerPController controller = cusPortal.getController();
	    	controller.initiate(comp, cust_id, bms);
		}
    }
}
