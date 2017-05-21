package gui.register;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import Main.BookingManagementSystem;
import business.Company;
import database.CompanyDatabase;
import database.ServicesDatabase;
import gui.login.LoginController;
import gui.welcome.WelcomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import mainController.MainController;

public class RegisterBusiness4Controller {

	private Company comp;
	private BookingManagementSystem bms;
    @FXML
    private AnchorPane rootPane;
    @FXML
	private Pane displayPane;
	@FXML
	private Pane contPane;
	@FXML 
	private ScrollPane scrollPaneCal;
	
    
    public void initiate(Company comp, BookingManagementSystem bms) {
		this.comp = comp;
		comp.setStatus("pending");
		this.bms = bms;
		contPane = new Pane();
		scrollPaneCal = new ScrollPane();
		
		scrollPaneCal.setLayoutX(440);
		scrollPaneCal.setLayoutY(157);
		scrollPaneCal.setPrefHeight(440.0);
		scrollPaneCal.setPrefWidth(500.0);
		scrollPaneCal.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		Label bus_title = new Label("Business Name: ");
		Label bus_service = new Label("Business Service: ");
		Label bus_open = new Label("Business Hours: ");
		Label name = new Label(comp.getName());
		
		HashMap<String, Integer> service = comp.getServiceTime();
		ArrayList<Label> service_list = new ArrayList<Label>();
		LinkedHashMap<DayOfWeek, String> times = comp.getBusHours();
		ArrayList<Label> time_list = new ArrayList<Label>();
		
		
		bus_title.setTextFill(Paint.valueOf("white"));
		bus_title.setLayoutX(50);
		bus_title.setLayoutY(30);
		bus_title.setFont(Font.font(16));
		contPane.getChildren().add(bus_title);
		
		name.setTextFill(Paint.valueOf("white"));
		name.setLayoutX(bus_title.getLayoutX()+170);
		name.setLayoutY(bus_title.getLayoutY());
		name.setFont(Font.font(16));
		contPane.getChildren().add(name);

		bus_open.setTextFill(Paint.valueOf("white"));
		bus_open.setLayoutX(bus_title.getLayoutX());
		bus_open.setLayoutY(name.getLayoutY()+50);
		bus_open.setFont(Font.font(16));
		contPane.getChildren().add(bus_open);
		
		double offsetX = bus_open.getLayoutX();
		double offsetY = bus_open.getLayoutY();
		int counter = 1;
		for(Entry<DayOfWeek, String> entry : times.entrySet()) {
			String s = entry.getKey().toString()+": ";
			if(entry.getValue().equals("")) {
				s+="Closed";
			} else {
				String[] time = entry.getValue().split(",");
				s+=time[0]+"-"+time[1];
			}
			
			Label l = new Label(s);
			l.setTextFill(Paint.valueOf("white"));
			l.setLayoutX(offsetX+170);
			l.setLayoutY(offsetY);
			l.setFont(Font.font(16));
			//time_list.add(l);
			offsetY+=50;
			contPane.getChildren().add(l);
			counter++;
		}
		
		
		bus_service.setTextFill(Paint.valueOf("white"));
		bus_service.setLayoutX(bus_open.getLayoutX());
		bus_service.setLayoutY(offsetY);
		bus_service.setFont(Font.font(16));
		contPane.getChildren().add(bus_service);
		
		offsetX = bus_service.getLayoutX();
		offsetY = bus_service.getLayoutY();
		counter =1;
		for(Entry<String, Integer> entry : service.entrySet()) {
			int time = entry.getValue()*15;
			String s = entry.getKey()+": "+Integer.toString(time)+"min";
			Label l = new Label(s);
			l.setTextFill(Paint.valueOf("white"));
			l.setLayoutX(offsetX+170);
			l.setLayoutY(offsetY);
			l.setFont(Font.font(16));
			//service_list.add(l);
			offsetY+=50;
			contPane.getChildren().add(l);
			counter++;
		}
		
		scrollPaneCal.setContent(contPane);
		displayPane.getChildren().add(scrollPaneCal);
	}

    @FXML
    void backRegister3(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader register = new FXMLLoader(getClass().getResource("../register/RegisterBusiness3.fxml"));
    	pane = register.load();
    	rootPane.getChildren().setAll(pane);
    	RegisterBusiness3Controller register_controller = register.getController();
		register_controller.initiate(comp, bms);
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
    	String name = comp.getName();
    	String username = comp.getUsername();
    	String bFname = comp.getFname();
    	String bLname = comp.getLname();
    	String pw = comp.getPassword();
    	String mobile = comp.getMobile();
    	String address = comp.getAddress();
    	ArrayList<String> service = comp.getService();
    	String serviceStr = service.get(0);
    	for(int i=1; i<service.size(); i++)
    	{
    		String time = String.valueOf(comp.getServiceTime(service.get(i)));
    		ServicesDatabase servDb = comp.getServDb();
    		servDb.addServices(comp.getName(), service.get(i), time);
    		serviceStr = serviceStr + ", " + service.get(i);
    	}
    	LinkedHashMap<DayOfWeek, String> busHour = comp.getBusHours();
    	String busHourStr = null;
    	int count = 0;
    	for(Entry<DayOfWeek,String> entry: busHour.entrySet())
    	{
    		if(count == 0)
    		{
    			busHourStr = entry.getKey().toString() + "=" + entry.getValue().toString();
    		}
    		else
    		{
    			busHourStr = busHourStr + "|" + entry.getKey().toString() + "=" + entry.getValue().toString();
    		}
			count++;
    	}
    	comp.setBusString(busHourStr);
    	String status = comp.getStatus();
    	CompanyDatabase companyDb = bms.getCompDb();
    	companyDb.addBusiness(username, name, bFname, bLname, pw, mobile, address, serviceStr, busHourStr, status);
    	bms.addCompany(comp);
    	goToWelcome();
    }
    
    @FXML
    void goToWelcome() throws IOException
    {
    	AnchorPane pane;
    	FXMLLoader welcome = new FXMLLoader(getClass().getResource("/gui/welcome/Welcome.fxml"));
    	pane = welcome.load();
    	rootPane.getChildren().setAll(pane);
    	WelcomeController controller = welcome.getController();
		controller.initiate(bms);
    }
    
    @FXML
    void goToLogin(ActionEvent event) throws IOException {
    	AnchorPane pane;
    	FXMLLoader login = new FXMLLoader(getClass().getResource("../login/Login.fxml"));
    	pane = login.load();
    	rootPane.getChildren().setAll(pane);
    	LoginController controller = login.getController();
		controller.initiate(bms);
    }

}
