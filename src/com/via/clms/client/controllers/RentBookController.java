package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class RentBookController implements Controller {
	private GridPane mainPane;
	private TextField scanText;
	private Button rentButton;
	private Label titleLabel;
	private Label dateLabel;
	private Label titleTextLabel;
	private Label dateTextLabel;

	@Override
	public String getTitle() {
		return "Rent Book";
	}

	@Override
	public Parent getComponent() {
		mainPane = new GridPane();
		scanText = new TextField();
		scanText.setPrefColumnCount(15);
		rentButton = new Button("Rent");
		titleLabel = new Label("Title");
		titleLabel.setStyle("-fx-font-weight: bold");
		dateLabel = new Label("Return date");
		dateLabel.setStyle("-fx-font-weight: bold");
		titleTextLabel = new Label();
		dateTextLabel = new Label();

		HBox upperBox = new HBox(scanText, rentButton);
		mainPane.add(upperBox, 0, 0, 2, 1);
		mainPane.add(titleLabel, 0, 1);
		mainPane.add(dateLabel, 1, 1);
		mainPane.add(dateTextLabel, 0, 2);
		mainPane.add(titleTextLabel, 1, 2);
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
}
