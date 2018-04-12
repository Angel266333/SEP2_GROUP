package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginController implements Controller {
	private GridPane mainPane;
	private String userName;
	private String password;

	public LoginController() {

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

		mainPane.add(loginLabel, 0, 0);
		mainPane.add(userName, 0, 2);
		mainPane.add(userNameField, 0, 3);
		mainPane.add(password, 0, 4);
		mainPane.add(passwordField, 0, 5);
		mainPane.add(loginButton, 0, 6);
	}

	public String getTitle() {
		return "Login";
	}

	public Parent getComponent() {
		return mainPane;
	}

	@Override
	public void onWindowResume(Window win) {

	}

	@Override
	public void onWindowPause(Window win) {

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
