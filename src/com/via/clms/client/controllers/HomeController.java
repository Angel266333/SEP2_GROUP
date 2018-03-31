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
	private HBox actionSection;
	private VBox userNotificationsSection;
	private HBox userProfileSection;

	private TextField tf1Search;
	private TextField tf2Notifications;
	
	private final Label lbl1Search;
	private final Label lbl2SearchResults;
	private final Label lbl3Actions;
	private final Label lbl4Notifications;
	
	private Button btn1Search;
	private Button btn2Rent;
	private Button btn3Return;
	private Button btn4Renew;
	private Button btn5MyProfile;
	
	// Add book search filters in main section of program (?)
	
	public HomeController() {		
				
		mainPane = new GridPane();
		searchPane = new GridPane();
		actionPane = new GridPane();
		notificationPane = new GridPane();
		profilePane = new GridPane();

		tf1Search = new TextField();
		tf2Notifications = new TextField();

		lbl1Search = new Label("Search for books:");
		lbl2SearchResults = new Label("Search Results");
		lbl3Actions = new Label("Actions:");
		lbl4Notifications = new Label("Notifications:");

		searchSection = new HBox();
		actionSection = new HBox();
		userNotificationsSection = new VBox();
		userProfileSection = new HBox();
		
	}
	
	
	public String getTitle() {
		return "Main section";
	}

	public Parent getComponent() {
		
		//\\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\
		
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));
		
		searchPane.setAlignment(Pos.TOP_LEFT);
		searchPane.setPadding(new Insets(0, 0, 10, 0));
		
		actionPane.setAlignment(Pos.CENTER_LEFT);
		actionPane.setPadding(new Insets(0, 0, 10, 0));

		notificationPane.setPadding(new Insets(0, 0, 10, 0));
		
		profilePane.setAlignment(Pos.BOTTOM_LEFT);
		
		//\\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\
		
		tf1Search.setPrefColumnCount(20);

		tf2Notifications.setPrefColumnCount(20);
		tf2Notifications.setEditable(false);
		tf2Notifications.setDisable(true);
		tf2Notifications.setText(this.getNotifications());
		
		//\\/\\/\\/\\/\\-=Label Properties=-//\\/\\/\\/\\/\\
		
		lbl1Search.setPadding(new Insets(0, 0, 5, 0));
		lbl3Actions.setPadding(new Insets(0, 0, 5, 0));
		
		//\\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\
		
		btn1Search = new Button("Search");
		btn2Rent = new Button("Rent");
		btn3Return = new Button("Return");
		btn4Renew = new Button("Renew");
		btn5MyProfile = new Button("My Profile");
		
		//\\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\
		
		searchSection.getChildren().addAll(tf1Search, btn1Search);
		searchSection.setSpacing(5);
		
		actionSection.getChildren().addAll(btn2Rent, btn3Return, btn4Renew);
		actionSection.setSpacing(5);
		
		userNotificationsSection.getChildren().addAll(lbl4Notifications, tf2Notifications);
		
		userProfileSection.getChildren().add(btn5MyProfile);
		
		//\\/\\/\\/\\/\\-=Compact Containers To Panes=-//\\/\\/\\/\\/\\
		
		searchPane.add(lbl1Search, 0, 0);
		searchPane.add(searchSection, 0, 1);
		
		actionPane.add(lbl3Actions, 0, 0);
		actionPane.add(actionSection, 0, 1);
			
		notificationPane.add(userNotificationsSection, 0, 0);
		
		profilePane.add(userProfileSection, 0, 0);
		
		//\\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\
		
		mainPane.add(searchPane, 0, 0);
		mainPane.add(actionPane, 0, 1);
		mainPane.add(notificationPane, 0, 2);
		mainPane.add(profilePane, 0, 3);
		
		return mainPane;
	}


	private String getNotifications() {
		// Example String value
		return "Current books status: Within rental period";
	}
	

	@Override
	public void onWindowOpen(Window win) {
		
	}

	@Override
	public void onWindowClose(Window win) {
		
	}

	@Override
	public void onWindowRefresh(Window win) {
		
	}

}