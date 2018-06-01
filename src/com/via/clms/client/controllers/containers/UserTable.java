package com.via.clms.client.controllers.containers;

import com.via.clms.shared.User;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class UserTable extends ClickableTable<User> {

	@Override
	public Label[] makeLabels(User dataElement) {
		Label[] bookLabels = new Label[5];
		bookLabels[0] = new Label("" + dataElement.uid);
		bookLabels[1] = new Label("" + dataElement.cpr);
		bookLabels[2] = new Label(dataElement.name);
		bookLabels[3] = new Label(dataElement.email);
		bookLabels[4] = new Label("");

		bookLabels[0].setPadding(new Insets(0, 0, 5, 0));
		bookLabels[1].setPadding(new Insets(0, 0, 5, 30));
		bookLabels[2].setPadding(new Insets(0, 0, 5, 10));
		bookLabels[3].setPadding(new Insets(0, 0, 5, 160));
		bookLabels[4].setPadding(new Insets(0, 0, 5, 81));

		return bookLabels;
	}

	@Override
	public Label[] makeHeaderLabels() {
		Label[] bookLabels = new Label[5];
		bookLabels[0] = new Label("ID");
		bookLabels[1] = new Label("CPR");
		bookLabels[2] = new Label("Name");
		bookLabels[3] = new Label("Email");
		bookLabels[4] = new Label("");

		bookLabels[0].setPadding(new Insets(0, 0, 5, 0));
		bookLabels[1].setPadding(new Insets(0, 0, 5, 30));
		bookLabels[2].setPadding(new Insets(0, 0, 5, 10));
		bookLabels[3].setPadding(new Insets(0, 0, 5, 160));
		bookLabels[4].setPadding(new Insets(0, 0, 5, 80));

		for (Label l : bookLabels) {
			l.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		}

		return bookLabels;
	}
}