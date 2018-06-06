package com.via.clms.client.controllers.containers;

import com.via.clms.shared.Book;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BookTable extends ClickableTable<Book> {

	@Override
	public Label[] makeLabels(Book dataElement) {
		Label[] bookLabels = new Label[6];
		bookLabels[0] = new Label("" + dataElement.ISBN);
		bookLabels[1] = new Label("" + dataElement.bid);
		bookLabels[2] = new Label(dataElement.title);
		bookLabels[3] = new Label(dataElement.author);
		bookLabels[4] = new Label(dataElement.location);
		bookLabels[5] = new Label("" + dataElement.release);
		
		bookLabels[0].setPadding(new Insets(0, 5, 5, 0));
		bookLabels[1].setPadding(new Insets(0, 15, 5, 0));
		bookLabels[2].setPadding(new Insets(0, 5, 5, 0));
		bookLabels[3].setPadding(new Insets(0, 80, 5, 0));
		bookLabels[4].setPadding(new Insets(0, 80, 5, 0));
		bookLabels[5].setPadding(new Insets(0, 60, 5, 0));

		return bookLabels;
	}

	@Override
	public Label[] makeHeaderLabels() {
		Label[] bookLabels = new Label[6];
		bookLabels[0] = new Label("ISBN");
		bookLabels[1] = new Label("ID");
		bookLabels[2] = new Label("Title");
		bookLabels[3] = new Label("Author");
		bookLabels[4] = new Label("Location");
		bookLabels[5] = new Label("Release");
		
		bookLabels[0].setPadding(new Insets(0, 5, 5, 0));
		bookLabels[1].setPadding(new Insets(0, 15, 5, 0));
		bookLabels[2].setPadding(new Insets(0, 5, 5, 0));
		bookLabels[3].setPadding(new Insets(0, 80, 5, 0));
		bookLabels[4].setPadding(new Insets(0, 80, 5, 0));
		bookLabels[5].setPadding(new Insets(0, 60, 5, 0));

		for (Label l : bookLabels) {
			l.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
		}	
		return bookLabels;
	}
}
