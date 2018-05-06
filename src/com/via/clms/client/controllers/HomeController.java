package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.DialogWindow;
import com.via.clms.client.views.ResultListener;
import com.via.clms.client.views.Window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private Window window;

	private TextField tf1Search;
	private TextField tf2Notifications;

	private Label lbl1Search;
	private Label lbl2SearchResults;
	private Label lbl3Actions;
	private Label lbl4Notifications;
	private Button btn1Search;
	private Button btn2Rent;
	private Button btn3Return;
	private Button btn4Renew;
	private Button btn5MyProfile;
	private Button btn6Lock;
	private Button btn7Unlock;
	private boolean temp = true;

	private class ResultHandler implements ResultListener<byte[]> {

		@Override
		public void onReturnResult(byte[] result) {

			if (result == null) {
				return;
			}

			temp = true;
			updateUI();
		}

	}

	// Add book search filters in main section of program (?)

	public HomeController() {

	}

	public String getTitle() {
		return "Main section";
	}

	public Parent getComponent() {

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

		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));

		searchPane.setAlignment(Pos.TOP_LEFT);
		searchPane.setPadding(new Insets(0, 0, 10, 0));

		actionPane.setAlignment(Pos.CENTER_LEFT);
		actionPane.setPadding(new Insets(0, 0, 10, 0));

		notificationPane.setPadding(new Insets(0, 0, 10, 0));

		profilePane.setAlignment(Pos.BOTTOM_LEFT);

		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\

		tf1Search.setPrefColumnCount(20);

		tf2Notifications.setPrefColumnCount(20);
		tf2Notifications.setEditable(false);
		tf2Notifications.setDisable(true);
		tf2Notifications.setText(this.getNotifications());

		// \\/\\/\\/\\/\\-=Label Properties=-//\\/\\/\\/\\/\\

		lbl1Search.setPadding(new Insets(0, 0, 5, 0));
		lbl3Actions.setPadding(new Insets(0, 0, 5, 0));

		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1Search = new Button("Search");
		btn2Rent = new Button("Rent");
		btn3Return = new Button("Return");
		btn4Renew = new Button("Renew");
		btn5MyProfile = new Button("My Profile");
		btn6Lock = new Button("Lock session");
		btn7Unlock = new Button("Unlock session");

		// \\/\\/\\/\\/\\-=Event Handlers=-//\\/\\/\\/\\/\\
		
		btn1Search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String resultParse = tf1Search.getText();
				if (tf1Search.getText().isEmpty() == false) {
				SearchResultController src = new SearchResultController();
				Window w = new DialogWindow(src);
				src.tf1Search.setText(resultParse);
				w.open();
			} else {
				Alert alertFailiure = new Alert(AlertType.ERROR);
				alertFailiure.setTitle("Error Dialog");
				alertFailiure.setHeaderText("Search unsuccessful");
				alertFailiure.setContentText("Please enter a book title!");
				alertFailiure.showAndWait();
			}

		}
		});
		
		btn2Rent.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				RentBookController rbc = new RentBookController();
				Window w = new DialogWindow(rbc);
				w.open();

			}

		});
		
		btn3Return.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				ReturnBookController rtrnbc = new ReturnBookController();
				Window w = new DialogWindow(rtrnbc);
				w.open();

			}

		});
		
//		btn4Renew.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent arg0) {
//
//				RenewBookController rnwbc = new RenewBookController();
//				Window w = new DialogWindow(rnwb);
//				w.open();
//
//			}
//
//		});
		
		btn5MyProfile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				ProfileController pc = new ProfileController(4424);
				Window w = new DialogWindow(pc);
				w.open();

			}

		});
		
		btn6Lock.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				
				// Check user name and password later;
				
				temp = false;
				updateUI();
				
			}
			
		});

		btn7Unlock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				LoginController lc = new LoginController();
				lc.setResultListener(new ResultHandler());
				Window w = new DialogWindow(lc);
				w.open();

			}

		});

		// \\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\

		searchSection.getChildren().addAll(tf1Search, btn1Search);
		searchSection.setSpacing(5);

		actionSection.getChildren().addAll(btn2Rent, btn3Return, btn4Renew);
		actionSection.setSpacing(5);

		userNotificationsSection.getChildren().addAll(lbl4Notifications, tf2Notifications);

		userProfileSection.getChildren().addAll(btn5MyProfile, btn6Lock, btn7Unlock);
		userProfileSection.setSpacing(5);

		// \\/\\/\\/\\/\\-=Compact Containers To Panes=-//\\/\\/\\/\\/\\

		searchPane.add(lbl1Search, 0, 0);
		searchPane.add(searchSection, 0, 1);

		actionPane.add(lbl3Actions, 0, 0);
		actionPane.add(actionSection, 0, 1);

		notificationPane.add(userNotificationsSection, 0, 0);

		profilePane.add(userProfileSection, 0, 0);

		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\

		mainPane.add(searchPane, 0, 0);
		mainPane.add(actionPane, 0, 1);
		mainPane.add(notificationPane, 0, 2);
		mainPane.add(profilePane, 0, 3);

		updateUI();
		return mainPane;
	}

	private String getNotifications() {
		// Example String value
		return "Current books status: Within rental period";
	}

	@Override
	public void onWindowOpen(Window win) {

		this.window = win;

	}

	@Override
	public void onWindowClose(Window win) {

	}

	@Override
	public void onWindowRefresh(Window win) {

	}

	@Override
	public void onWindowResume(Window win) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWindowPause(Window win) {
		// TODO Auto-generated method stub

	}

	public void updateUI() {
		btn6Lock.setVisible(temp);
		btn7Unlock.setVisible(!temp);
	}
}