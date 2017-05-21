package gui.register;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Main.BookingManagementSystem;
import business.Company;
import database.CompanyDatabase;
import gui.login.LoginController;
import gui.portal.AdminPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class PendingRegistrationController {

	private MainController menu;
	private Company comp;
	private CompanyDatabase compDb;
	private BookingManagementSystem bms;
	private ArrayList<Company> companyList;
	ObservableList<String> businessList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton gotoLogout;

    @FXML
    private Label username;

    @FXML
    private JFXComboBox<String> chooseBusiness;

    @FXML
    private JFXButton approveButton;

    @FXML
    private JFXButton declineButton;

    @FXML
    private Label invalidbussName;
    
    @FXML
    private void initialize(){
    	chooseBusiness.setItems(businessList);
    }
    
    public void initiate(BookingManagementSystem bms) {
		menu = bms.getMenu();
		this.bms = bms;
		compDb = bms.getCompDb();
		companyList = bms.getCompanyList();
		for(Company comp: companyList)
		{
			if(comp.getStatus().equals("pending"))
			{
				businessList.add(comp.getName());
			}
		}
		
		chooseBusiness.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				comp = bms.getCompany(newValue.toString());
				menu = comp.getMenu();
			}
		});
	}

    @FXML
    void goToLogout(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }

    @FXML
    void goToPortal(ActionEvent event) {
    	try {
			AnchorPane pane;
	    	FXMLLoader adminPortal = new FXMLLoader(getClass().getResource("../portal/AdminPortal.fxml"));
	    	pane = adminPortal.load();
	    	rootPane.getChildren().setAll(pane);
	    	AdminPController controller = adminPortal.getController();
			controller.initiate(bms);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void register(ActionEvent event) 
    {
    	comp.setStatus("verified");
    	compDb.updateStatus("verified", comp.getUsername());
    	goToPortal(event);
    }
    
    @FXML
    void decline(ActionEvent event)
    {
    	companyList.remove(comp);
    	compDb.removeBusiness(comp.getUsername());
    	goToPortal(event);
    }

}
