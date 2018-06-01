package com.via.clms.client.controllers.containers;

import com.via.clms.shared.Book;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class SearchResultTable extends ClickableTable<Book> {
	
	@Override
	public Label[] makeLabels(Book dataElement) {
		Label[] bookLabels = new Label[5];
		bookLabels[0] = new Label(" " + dataElement.title);
		bookLabels[1] = new Label(dataElement.author);
		bookLabels[2] = new Label("    " + dataElement.release);
		bookLabels[3] = new Label("     " + dataElement.inventory);
		bookLabels[4] = new Label(dataElement.location);
		for (Label l : bookLabels) {
			l.setPadding(new Insets(5, 15, 5, 15));
		}
		return bookLabels;
	}

	@Override
	public Label[] makeHeaderLabels() {
		Label[] bookLabels = new Label[5];
		bookLabels[0] = new Label("Title");
		bookLabels[1] = new Label("Author");
		bookLabels[2] = new Label("Release");
		bookLabels[3] = new Label("Available");
		bookLabels[4] = new Label("Location");
		for (Label l : bookLabels) {
			l.setPadding(new Insets(0, 0, 5, 15));
		}
		return bookLabels;
	}
}