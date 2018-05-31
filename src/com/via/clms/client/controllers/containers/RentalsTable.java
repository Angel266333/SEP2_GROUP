package com.via.clms.client.controllers.containers;

import com.via.clms.shared.BookRental;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class RentalsTable extends ClickableTable<String[]> {
	@Override
	Label[] makeLabels(String[] dataElement) {
		Label[] labels = new Label[3];
		labels[0] = new Label(dataElement[0]);
		labels[1] = new Label(dataElement[1]);
		labels[2] = new Label(dataElement[2]);
		for(Label l : labels) {
			l.setPadding(new Insets(5, 15, 5, 15));
		}
		return labels;
	}

	@Override
	Label[] makeHeaderLabels() {
		Label[] labels = new Label[3];
		labels[0] = new Label("Title");
		labels[1] = new Label("Due");
		labels[2] = new Label("Past due");
		for(Label l : labels) {
			l.setPadding(new Insets(0, 0, 5, 15));
		}
		return labels;
	}
}
