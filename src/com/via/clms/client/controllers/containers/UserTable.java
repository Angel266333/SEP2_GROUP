package com.via.clms.client.controllers.containers;

import com.via.clms.shared.User;

import javafx.scene.control.Label;

public class UserTable extends ClickableTable<User> {

	@Override
	public Label[] makeLabels(User dataElement) {
		Label[] bookLabels = new Label[4];
		bookLabels[0] = new Label("" + dataElement.uid);
		bookLabels[1] = new Label("" + dataElement.cpr);
		bookLabels[2] = new Label(dataElement.name);
		bookLabels[3] = new Label(dataElement.email);
		return bookLabels;
	}

	@Override
	public Label[] makeHeaderLabels() {
		String extraSpace = "   ";
		Label[] bookLabels = new Label[4];
		bookLabels[0] = new Label("ID" + extraSpace);
		bookLabels[1] = new Label("CPR" + extraSpace);
		bookLabels[2] = new Label("Name" + extraSpace);
		bookLabels[3] = new Label("Email" + extraSpace);
		return bookLabels;
	}
}