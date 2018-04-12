package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class RentBookController implements Controller {
	private BorderPane mainPane;
	private TextField scanText;
	private Button rentButton;
	private Label titleLabel;
	private Label dateLabel;
	private TextArea titleTextArea;
	private Label dateTextLabel;

	@Override
	public String getTitle() {
		return "Rent Book";
	}

	@Override
	public Parent getComponent() {
		mainPane = new BorderPane();
		scanText = new TextField();
		scanText.setPrefColumnCount(20);
		rentButton = new Button("Rent");
		titleLabel = new Label("Title");
		titleLabel.setStyle("-fx-font-weight: bold");
		dateLabel = new Label("Return date");
		dateLabel.setStyle("-fx-font-weight: bold");
		dateTextLabel = new Label();
		titleTextArea = new TextArea();
		titleTextArea.setPrefColumnCount(20);
		titleTextArea.setEditable(false);

		HBox upperBox = new HBox(scanText, rentButton);
		VBox middleBox = new VBox(titleLabel, titleTextArea);
		HBox bottomBox = new HBox(dateLabel, dateTextLabel);

		mainPane.setTop(upperBox);
		mainPane.setCenter(middleBox);
		mainPane.setBottom(bottomBox);
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
}
