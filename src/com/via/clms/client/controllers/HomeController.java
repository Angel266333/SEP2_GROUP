package com.via.clms.client.controllers;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomeController implements Controller {
	private GridPane mainPane;
	private GridPane searchPane;
	private GridPane actionPane;
	private GridPane notificationPane;
	private GridPane profilePane;
	private HBox searchSection;
	private HBox actionButtons;
	private VBox userNotifications;
	private HBox userProfile;

	private TextField tf1Search;
	private TextField tf2Notifications;
	private Label lbl1Notifications;
	private Button btn1Search;
	private Button btn2Rent;
	private Button btn3Renew;
	private Button btn4MyProfile;
	
	public HomeController() {		
				
		mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));
		
		searchPane = new GridPane();
		searchPane.setAlignment(Pos.TOP_LEFT);
		searchPane.setPadding(new Insets(0, 0, 10, 0));
		
		actionPane = new GridPane();
		actionPane.setAlignment(Pos.CENTER_LEFT);
		actionPane.setPadding(new Insets(0, 0, 10, 0));

		notificationPane = new GridPane();
		notificationPane.setPadding(new Insets(0, 0, 10, 0));
		
		profilePane = new GridPane();
		profilePane.setAlignment(Pos.BOTTOM_LEFT);
		
		tf1Search = new TextField();
		tf1Search.setPrefColumnCount(20);

		tf2Notifications = new TextField();
		tf2Notifications.setPrefColumnCount(20);
		tf2Notifications.setEditable(false);
		tf2Notifications.setDisable(true);
		tf2Notifications.setText(this.getNotifications());
		
		lbl1Notifications = new Label("Notifications: ");
			
		Button btn1Search = new Button("Search");
		Button btn2Rent = new Button("Rent");
		Button btn3Return = new Button("Return");
		Button btn4Renew = new Button("Renew");
		Button btn5MyProfile = new Button("My Profile");
		
		searchSection = new HBox(5);
		searchSection.getChildren().addAll(tf1Search, btn1Search);
		searchSection.setSpacing(5);
		
		actionButtons = new HBox(5);
		actionButtons.getChildren().addAll(btn2Rent, btn3Return, btn4Renew);
		actionButtons.setSpacing(5);
		
		userNotifications = new VBox(5);
		userNotifications.getChildren().addAll(lbl1Notifications, tf2Notifications);
		
		userProfile = new HBox();
		userProfile.getChildren().add(btn5MyProfile);
		
		searchPane.add(searchSection, 0, 1);
		actionPane.add(actionButtons, 0, 1);
		
		notificationPane.add(userNotifications, 0, 1);
		
		profilePane.add(userProfile, 0, 1);
		
		mainPane.add(searchPane, 0, 1);
		mainPane.add(actionPane, 0, 2);
		mainPane.add(notificationPane, 0, 3);
		mainPane.add(profilePane, 0, 4);
		
	}
	
	
	public String getTitle() {
		return "Main section";
	}

	public Parent getComponent() {
		return mainPane;
	}


	private String getNotifications() {
		// Example String value
		return "Current books status: Within rental period";
		
	}
	

	@Override
	public void onWindowOpen(Window win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWindowClose(Window win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWindowRefresh(Window win) {
		// TODO Auto-generated method stub
	}

}