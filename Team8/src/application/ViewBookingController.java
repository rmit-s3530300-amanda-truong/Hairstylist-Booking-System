package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mainController.MainController;

public class ViewBookingController {

	private MainController menu;
	
	@FXML
	private AnchorPane rootPane;
	
	@FXML
    private JFXButton gotoLogout;

    @FXML
    private JFXButton viewMorning;
    
    @FXML
    private JFXButton viewAfternoon;
    
	@FXML
	private JFXButton returnButton;
    
    private String cust_id;
    
    private String portal;

	public void initiate(MainController menu, String cust_id, String portal) {
		this.menu = menu;
		this.portal = portal;
		this.cust_id = cust_id;
	}
	
	@FXML
    void past(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader bh = new FXMLLoader(getClass().getResource("BookingHistory.fxml"));
    	pane = bh.load();
    	rootPane.getChildren().setAll(pane);
    	BookingHistoryController controller = bh.getController();
		controller.initiate(menu, cust_id);
	}
	
	@FXML
	void future(ActionEvent event) throws IOException
	{
		AnchorPane pane;
    	FXMLLoader up = new FXMLLoader(getClass().getResource("UpcomingBooking.fxml"));
    	pane = up.load();
    	rootPane.getChildren().setAll(pane);
    	UpcomingBookingController controller = up.getController();
		controller.initiate(menu, cust_id);
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
