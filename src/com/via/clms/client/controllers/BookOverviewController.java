package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * 
 */
public class BookOverviewController implements Controller {

	/**
	 * 
	 */
	public BookOverviewController(/* Some book object goes here */) {

	}

	/**
	 * 
	 */
	@Override
	public String getTitle() {
		return "A Book"; // Should return the title from the book object
	}

	/**
	 * 
	 */
	@Override
	public Parent getComponent() {
		Label titleLabel = new Label("Author");
		titleLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 14;");

		Label authorLabel = new Label("Author");
		authorLabel.setStyle("-fx-font-weight: bold");

		Label isbnLabel = new Label("ISBN");
		isbnLabel.setStyle("-fx-font-weight: bold");

		Label what1Label = new Label("What!?");
		what1Label.setStyle("-fx-font-weight: bold");

		Label what2Label = new Label("What!?");
		what2Label.setStyle("-fx-font-weight: bold");

		Label descLabel = new Label("Description");
		what2Label.setStyle("-fx-font-weight: bold");

		Label authorValue = new Label("Some value");
		Label isbnValue = new Label("Some value");
		Label what1Value = new Label("Some value");
		Label what2Value = new Label("Some value");
		TextArea descValue = new TextArea();

		Button btn = new Button("Save");

		GridPane innerGrid = new GridPane();

		innerGrid.add(authorLabel, 0, 0);
		innerGrid.add(authorValue, 1, 0);

		innerGrid.add(isbnLabel, 2, 0);
		innerGrid.add(isbnValue, 3, 0);

		innerGrid.add(what1Label, 0, 1);
		innerGrid.add(what1Value, 1, 1);

		innerGrid.add(what2Label, 2, 1);
		innerGrid.add(what2Value, 3, 1);

		GridPane descGrid = new GridPane();

		descGrid.add(descLabel, 0, 0);
		descGrid.add(descValue, 0, 1);

		GridPane mainGrid = new GridPane();
		mainGrid.add(titleLabel, 0, 0);
		mainGrid.add(innerGrid, 0, 1);
		mainGrid.add(descGrid, 0, 2);
		mainGrid.add(btn, 0, 3);

		return mainGrid;
	}

	/**
	 * 
	 */
	@Override
	public void onWindowOpen(Window win) {

	}

	/**
	 * 
	 */
	@Override
	public void onWindowClose(Window win) {

	}

	/**
	 * 
	 */
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
