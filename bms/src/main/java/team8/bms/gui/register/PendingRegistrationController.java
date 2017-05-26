package team8.bms.gui.register;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import team8.bms.gui.portal.AdminPController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import team8.bms.BookingManagementSystem;
import team8.bms.business.Company;
import team8.bms.database.CompanyDatabase;
import team8.bms.gui.LoginController;
import team8.bms.mainController.MainController;

public class PendingRegistrationController {

	@SuppressWarnings("unused")
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
    
    public void initiate(final BookingManagementSystem bms) {
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

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				comp = bms.getCompany(newValue.toString());
				menu = comp.getMenu();
			}
		});
		
		if(businessList.size() == 0){
			declineButton.setDisable(true);
			approveButton.setDisable(true);
			invalidbussName.setText("Good Job. No pending registration.");
			invalidbussName.setStyle("-fx-text-fill: green; -fx-font-size: 16;");
		}
	}

    @FXML
    void goToLogout(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }

    @FXML
    void goToPortal() throws IOException {
    	AnchorPane pane;
	    FXMLLoader adminPortal = new FXMLLoader(getClass().getResource("/AdminPortal.fxml"));
	    pane = adminPortal.load();
	    rootPane.getChildren().setAll(pane);
	    AdminPController controller = adminPortal.getController();
		controller.initiate(bms);
    }

    @FXML
    void register(ActionEvent event) throws IOException 
    {
    	comp.setStatus("verified");
    	compDb.updateStatus("verified", comp.getUsername());
    	goToPortal();
    }
    
    @FXML
    void decline(ActionEvent event) throws IOException
    {
    	companyList.remove(comp);
    	compDb.removeBusiness(comp.getUsername());
    	goToPortal();
    }

}
