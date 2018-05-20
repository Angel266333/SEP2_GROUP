package com.via.clms.client.controllers.containers;

import com.via.clms.shared.Book;

import javafx.scene.control.Label;

public class BookTable extends ClickableTable<Book> {

	@Override
	public Label[] makeLabels(Book dataElement) {
		Label[] bookLabels = new Label[7];
		bookLabels[0] = new Label("" + dataElement.bid + "  ");
		bookLabels[1] = new Label(dataElement.title);
		bookLabels[2] = new Label("" + dataElement.inventory);
		bookLabels[3] = new Label("" + dataElement.ISBN);
		bookLabels[4] = new Label("" + dataElement.release);
		bookLabels[5] = new Label(dataElement.author);
		bookLabels[6] = new Label(dataElement.location);
		return bookLabels;
	}

	@Override
	public Label[] makeHeaderLabels() {
		String extraSpace = "  "
				+ "  "
				+ "  "
				+ "  "
				+ "  "
				+ "  "
				+ "  "
				+ "  "
				+ "  "
				+ "  ";
		Label[] bookLabels = new Label[7];
		bookLabels[0] = new Label("ID  ");
		bookLabels[1] = new Label("Title" + extraSpace);
		bookLabels[2] = new Label("Inventory  ");
		bookLabels[3] = new Label("ISBN  ");
		bookLabels[4] = new Label("Release   ");
		bookLabels[5] = new Label("Author" + extraSpace);
		bookLabels[6] = new Label("Location");
		return bookLabels;
	}
}