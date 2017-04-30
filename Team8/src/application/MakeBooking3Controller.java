package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import Business.Company;
import Business.Employee;
import Business.Employee.Service;
import MainController.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class MakeBooking3Controller {
	private MainController menu;
	
	private Company comp;
	
	private String cust_id;
	
	private Service service;
	
	private Employee employee=null;
	
	private String portal;
	
	@FXML
	private Label invalid;
	
	final ToggleGroup group = new ToggleGroup();
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

	public void initiate(MainController menu, String cust_id, Service service, String portal) {
		this.menu = menu;
		this.cust_id = cust_id;
		this.service = service;
		this.portal = portal;
		int counter = 0;
		comp = menu.getCompany();
		
		ArrayList<Employee> avail_list = new ArrayList<Employee>();
		
		HashMap<String, Employee> empList = comp.getEmployeeList();
		for(Entry<String, Employee> entry : empList.entrySet()) {
			Employee emp = entry.getValue();
			ArrayList<Service> serv = emp.getService();
			for(Service s : serv){
				if(s.equals(service)) {
					avail_list.add(emp);
				}
			}
		}
		if(avail_list.size() > 0) {
			ArrayList<JFXRadioButton> buttons = new ArrayList<JFXRadioButton>();
			JFXRadioButton b1 = new JFXRadioButton();
			b1.setText("ANY");
			b1.setStyle("-fx-text-fill: white");
			b1.setFont(Font.font(16));
			b1.setLayoutX(487.0);
			b1.setLayoutY(305.0+(counter*50));
			rootPane.getChildren().add(b1);
			counter++;
			for(Employee emp : avail_list) {
				JFXRadioButton b = new JFXRadioButton();
				b.setUserData(emp);
				b.setText(emp.getFirstName()+" "+emp.getLastName());
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
			b1.setUserData(buttons.get(0).getUserData());
			b1.setToggleGroup(group);
			employee = (Employee) buttons.get(0).getUserData();
		} else {
			invalid.setText("No Employees Available");
			invalid.setAlignment(Pos.CENTER);
		}
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				employee = (Employee) arg2.getUserData();
				
			}
			
		});
	}
	
	@FXML
	void next(ActionEvent event) throws IOException {
		if(employee== null) {
			invalid.setText("Invalid Option");
			invalid.setAlignment(Pos.CENTER);
		} else {
			System.out.println(employee.getFirstName().toString());
			AnchorPane pane;
	    	FXMLLoader mb4 = new FXMLLoader(getClass().getResource("MakeBooking4.fxml"));
	    	pane = mb4.load();
	    	rootPane.getChildren().setAll(pane);
	    	MakeBooking4Controller controller = mb4.getController();
	    	controller.initiate(menu, cust_id, service, employee, portal);
			
		}
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		AnchorPane pane;
    	FXMLLoader mb2 = new FXMLLoader(getClass().getResource("MakeBooking2.fxml"));
    	pane = mb2.load();
    	rootPane.getChildren().setAll(pane);
    	MakeBooking2Controller controller = mb2.getController();
    	controller.initiate(menu, cust_id, portal);
	}
	
	@FXML
	void goToLogout() throws IOException{
		AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
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
