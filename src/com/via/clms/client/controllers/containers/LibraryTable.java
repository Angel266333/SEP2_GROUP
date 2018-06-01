package com.via.clms.client.controllers.containers;

import com.via.clms.shared.Library;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class LibraryTable extends ClickableTable<Library> {

	@Override
	public Label[] makeLabels(Library dataElement) {
		Label[] libraryLabels = new Label[3];
		libraryLabels[0] = new Label("" + dataElement.lid);
		libraryLabels[1] = new Label(dataElement.name);
		libraryLabels[2] = new Label(dataElement.location);

		libraryLabels[0].setPadding(new Insets(0, 0, 5, 0));
		libraryLabels[1].setPadding(new Insets(0, 0, 5, 142));
		libraryLabels[2].setPadding(new Insets(0, 0, 5, 142));
		return libraryLabels;
	}

	@Override
	public Label[] makeHeaderLabels() {
		Label[] libraryLabels = new Label[3];
		libraryLabels[0] = new Label("LID");
		libraryLabels[1] = new Label("Name");
		libraryLabels[2] = new Label("Location");

		libraryLabels[0].setPadding(new Insets(0, 0, 5, 0));
		libraryLabels[1].setPadding(new Insets(0, 0, 5, 142));
		libraryLabels[2].setPadding(new Insets(0, 0, 5, 142));

		for (Label l : libraryLabels) {
		l.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		return libraryLabels;
	}
}