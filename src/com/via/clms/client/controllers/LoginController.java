package com.via.clms.client.controllers;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.views.ResultController;
import com.via.clms.proxy.IUserService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginController extends ResultController<byte[]> {
	private GridPane mainPane;
	private String userName;
	private String password;
	private int lid;

	public LoginController(int libraryid) {
		lid = libraryid;
	}

	public String getTitle() {
		return "Login";
	}

	public Parent getComponent() {

		mainPane = new GridPane();

		// Labels
		String labelStyle = "-fx-font-weight: bold";

		Label loginLabel = new Label("Login");
		loginLabel.setStyle(labelStyle);

		Label userName = new Label("User Name");
		userName.setStyle(labelStyle);

		Label password = new Label("Password");
		password.setStyle(labelStyle);

		// TextFields
		final TextField userNameField = new TextField();
		userNameField.setPrefColumnCount(20);

		final PasswordField passwordField = new PasswordField();
		passwordField.setPrefColumnCount(20);

		// Buttons
		Button loginButton = new Button("Login");
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				// Get the cpr and password from the input fields
				String cpr = userNameField.getText();
				String passwd = passwordField.getText();
				
				// Get the user service
				IUserService service = (IUserService) ServiceManager.getService("user");
				
				try {
					// Get the users token, if the information is valid
					byte[] token = service.getUserToken(Long.parseLong(cpr), passwd);
					
					// If the information is valid, pass the token to the caller
					if (token != null) {
						if (service.checkPermissions(token, lid, IUserService.ROLE_LOGIN)) {
							setResult(token);
							getWindow().close();
						}
						
						// This user is not allowed to login
						
					}
					
				} catch (Exception e) {}

				// Not valid, print an error message to the user
			}

		});

		mainPane.add(loginLabel, 0, 0);
		mainPane.add(userName, 0, 2);
		mainPane.add(userNameField, 0, 3);
		mainPane.add(password, 0, 4);
		mainPane.add(passwordField, 0, 5);
		mainPane.add(loginButton, 0, 6);
		return mainPane;
	}

	
	
}
