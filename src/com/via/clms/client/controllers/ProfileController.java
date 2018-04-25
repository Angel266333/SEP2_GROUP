package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ProfileController implements Controller {
	private GridPane mainPane;
	private GridPane rentalPane;
	private long cpr;
	private String name;
	private String email;
	Button emailUpdateButton;
	Button changePasswordButton;

	public ProfileController(long cpr) {
		this.cpr = cpr;

		mainPane = new GridPane();
		rentalPane = new GridPane();
		getData();

	}

	@Override
	public String getTitle() {
		return "User profile";
	}

	@Override
	public Parent getComponent() {
		//Labels
		String labelStyle = "-fx-font-weight: bold";
		Label nameLabel = new Label("Name");
		nameLabel.setStyle(labelStyle);
		Label nameTextLabel = new Label(name);
		Label cprLabel = new Label("CPR");
		cprLabel.setStyle(labelStyle);
		Label cprTextLabel = new Label("" + cpr);
		Label emailLabel = new Label("E-mail");
		emailLabel.setStyle(labelStyle);

		//TextFields
		TextField emailTextField = new TextField();
		emailTextField.setPrefColumnCount(20);
		emailTextField.setText(email);

		//Buttons
		emailUpdateButton = new Button("Update");
		changePasswordButton = new Button("Change password");

		mainPane.add(nameLabel, 0, 0);
		mainPane.add(cprLabel, 1, 0);
		mainPane.add(nameTextLabel, 0, 1);
		mainPane.add(cprTextLabel, 1, 1);
		mainPane.add(emailLabel, 0, 2);
		mainPane.add(emailTextField, 0, 3);
		mainPane.add(emailUpdateButton, 1, 3);
		mainPane.add(rentalPane, 0, 4, 2, 1);
		mainPane.add(changePasswordButton, 0, 5);

		return mainPane;
	}

	@Override
	public void onWindowOpen(Window window) {

	}

	@Override
	public void onWindowClose(Window window) {

	}

	@Override
	public void onWindowRefresh(Window window) {

	}

	@Override
	public void onWindowResume(Window win) {
		
	}
	
	@Override
	public void onWindowPause(Window win) {
		
	}

	private void getData() {
		//Test data REMOVE====
		name = "John Hansen";
		email = "whatever@gmail.com";
		rentalPane.add(new Label("Some rental"), 0, 0);
		rentalPane.add(new Label("Some reservation"), 0, 1);
		rentalPane.add(new Label("Some other rental"), 0, 2);
		rentalPane.add(new Label("Some other reservation"), 0, 3);
		//REMOVE==============
	}
}