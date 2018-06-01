package com.via.clms.client.controllers;

import java.rmi.RemoteException;

import com.via.clms.Log;
import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.DialogWindow;
import com.via.clms.client.views.ResultListener;
import com.via.clms.client.views.Window;
import com.via.clms.proxy.IUserService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private Window window;

	private TextField tf1Search;
	private TextField tf2Notifications;

	private Label lbl1Search;
	private Label lbl2Actions;
	private Label lbl3Notifications;
	private Button btn1Search;
	private Button btn2Rent;
	private Button btn3Return;
	private Button btn4Renew;
	private Button btn5GodMode;
	private Button btn6MyProfile;
	private Button btn7Lock;
	private Button btn8Unlock;

	private UserSession session;

	private class ResultHandler implements ResultListener<UserSession> {

		@Override
		public void onReturnResult(UserSession session) {

			if (session == null) {
				return;
			}

			HomeController.this.session = session;
			updateUI();
		}

	}

	// Add book search filters in main section of program (?)

	public HomeController(UserSession session) {
		this.session = session;
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
		lbl2Actions = new Label("Actions:");
		lbl3Notifications = new Label("Notifications:");

		searchSection = new HBox();
		actionSection = new HBox();
		userNotificationsSection = new VBox();
		userProfileSection = new HBox();

		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(-20, 5, 20, 5));

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
		lbl2Actions.setPadding(new Insets(0, 0, 5, 0));

		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1Search = new Button("Search");
		btn2Rent = new Button("Rent");
		btn3Return = new Button("Return");
		btn4Renew = new Button("Renew");
		btn5GodMode = new Button("Administrative features");
		btn5GodMode.setVisible(false);
		btn6MyProfile = new Button("My Profile");
		btn7Lock = new Button("Lock session");
		btn8Unlock = new Button("Unlock session");

		// \\/\\/\\/\\/\\-=Event Handlers=-//\\/\\/\\/\\/\\

		btn1Search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String resultParse = tf1Search.getText();
				if (tf1Search.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search unsuccessful");
					alertFailiure.setContentText("Please enter a book title!");
					alertFailiure.showAndWait();
				} else {
					window.launchController(new RentBookController());

					SearchResultController src = new SearchResultController(null);
					Window w = new DialogWindow(src);
					src.tf1Search.setText(resultParse);
					w.open();
				}

			}
		});

		btn2Rent.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				window.launchController(new RentBookController());
			}

		});

		btn3Return.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				window.launchController(new ReturnBookController());
			}

		});

		btn5GodMode.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				window.launchController(new AdministrativeFeatureSetController(session));
			}
		});

		btn6MyProfile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				window.launchController(new ProfileController(session));
			}

		});

		btn7Lock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				IUserService user = (IUserService) ServiceManager.getService("user");

				try {
					byte[] newtoken = user.getSpecialToken(session.token, session.lid, IUserService.ROLE_BOOKRENT);

					if (newtoken != null && user.checkPermissions(newtoken, session.lid, IUserService.ROLE_BOOKRENT)) {
						session = new UserSession(newtoken, -1L, session.lid);
					}

				} catch (RemoteException e) {
				}

				updateUI();

			}

		});

		btn8Unlock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				LoginController lc = new LoginController(session.lid);
				lc.setResultListener(new ResultHandler());
				Window w = new DialogWindow(lc);
				w.open();

			}

		});

		// \\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\

		searchSection.getChildren().addAll(tf1Search, btn1Search);
		searchSection.setSpacing(5);

		actionSection.getChildren().addAll(btn2Rent, btn3Return, btn4Renew, btn5GodMode);
		actionSection.setSpacing(5);

		userNotificationsSection.getChildren().addAll(lbl3Notifications, tf2Notifications);

		userProfileSection.getChildren().addAll(btn6MyProfile, btn7Lock, btn8Unlock);
		userProfileSection.setSpacing(5);

		// \\/\\/\\/\\/\\-=Compact Containers To Panes=-//\\/\\/\\/\\/\\

		searchPane.add(lbl1Search, 0, 0);
		searchPane.add(searchSection, 0, 1);

		actionPane.add(lbl2Actions, 0, 0);
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
		window = win;

	}

	@Override
	public void onWindowClose(Window win) {

	}

	@Override
	public void onWindowRefresh(Window win) {

	}

	@Override
	public void onWindowResume(Window win) {

	}

	@Override
	public void onWindowPause(Window win) {

	}

	public void updateUI() {
		IUserService user = (IUserService) ServiceManager.getService("user");
		boolean locked = false;

		try {
			locked = user.isSpecialToken(session.token);

		} catch (RemoteException e) {
		}

		btn5GodMode.setVisible(!locked);
		btn7Lock.setVisible(!locked);
		btn8Unlock.setVisible(locked);

		try {
			if (!locked && user.checkPermissions(session.token, session.lid, IUserService.ROLE_BOOKRENT)) {
				btn7Lock.setVisible(false);
			}

		} catch (RemoteException e) {
			Log.error(e);
		}

		try {
			if (user.checkPermissions(session.token, session.lid, IUserService.ROLE_ADMIN)) {
				btn5GodMode.setVisible(true);

			} else {
				btn5GodMode.setVisible(false);
			}

		} catch (RemoteException e) {
			Log.error(e);
		}

		btn2Rent.setVisible(session.lid > 0);
		btn3Return.setVisible(session.lid > 0);
		btn4Renew.setVisible(session.lid > 0);
	}
}