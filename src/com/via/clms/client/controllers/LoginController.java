package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.ResultController;
import com.via.clms.client.views.Window;

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

	public LoginController() {

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
		TextField userNameField = new TextField();
		userNameField.setPrefColumnCount(20);

		PasswordField passwordField = new PasswordField();
		passwordField.setPrefColumnCount(20);

		// Buttons
		Button loginButton = new Button("Login");
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				// Check user name and password later;

				setResult(new byte[0]);
				getWindow().close();

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
