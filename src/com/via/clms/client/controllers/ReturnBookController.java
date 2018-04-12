package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ReturnBookController implements Controller {
	private GridPane mainPane;

	public ReturnBookController() {

		mainPane = new GridPane();

		String labelStyle = "-fx-font-weight: bold";
		Label bookIDLabel = new Label("Book ID  ");

		TextField bookIDText = new TextField();

		bookIDLabel.setStyle(labelStyle);

		Button returnButton = new Button("  Return the book  ");

		mainPane.add(bookIDLabel, 0, 0);
		mainPane.add(bookIDText, 0, 1);

		mainPane.add(returnButton, 1, 1);
	}

	public String getTitle() {
		return "Return a book";
	}

	public Parent getComponent() {
		return mainPane;
	}

	@Override
	public void onWindowOpen(Window win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWindowClose(Window win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWindowRefresh(Window win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWindowResume(Window win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWindowPause(Window win) {
		// TODO Auto-generated method stub
		
	}

}
