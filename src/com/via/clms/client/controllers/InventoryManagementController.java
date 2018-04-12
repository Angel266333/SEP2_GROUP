package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InventoryManagementController implements Controller {
	private GridPane mainPane;
	private ListView foundBooks;

	Button find;
	Button add;
	Button remove;
	Button edit;

	public InventoryManagementController() {

		mainPane = new GridPane();
		foundBooks = new ListView();

	}

	public String getTitle() {
		return "Inventory Management";
	}

	public Parent getComponent() {
		// Labels
		String labelStyle = "-fx-font-weight: bold";

		Label titleLabel = new Label("Book title");
		titleLabel.setStyle(labelStyle);

		Label authorLabel = new Label("Author");
		authorLabel.setStyle(labelStyle);

		Label idLabel = new Label("Book ID");
		idLabel.setStyle(labelStyle);

		Label listLabel = new Label("Found books");
		listLabel.setStyle(labelStyle);

		// TextFields

		TextField titleTextField = new TextField();
		titleTextField.setPrefColumnCount(20);

		TextField authorTextField = new TextField();
		authorTextField.setPrefColumnCount(20);

		TextField idTextField = new TextField();
		idTextField.setPrefColumnCount(20);

		// Buttons

		find = new Button("Find books");
		add = new Button("Add a book");
		remove = new Button("Remove book");
		edit = new Button("Edit book");

		// Setting components

		mainPane.add(titleLabel, 0, 0);
		mainPane.add(titleTextField, 1, 0);
		mainPane.add(find, 2, 0);

		mainPane.add(authorLabel, 0, 1);
		mainPane.add(authorTextField, 1, 1);
		mainPane.add(add, 2, 1);

		mainPane.add(idLabel, 0, 2);
		mainPane.add(idTextField, 1, 2);
		mainPane.add(remove, 2, 2);

		mainPane.add(foundBooks, 1, 4, 1, 1);
		mainPane.add(listLabel, 1, 3);
		mainPane.add(edit, 2, 3);

		return mainPane;

	}

	@Override
	public void onWindowOpen(Window win) {
		// TODO Auto-generated method stub

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

}
