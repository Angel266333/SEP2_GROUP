package com.via.clms.client.controllers.containers;

import com.via.clms.shared.Library;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class LibraryTable extends GridPane {

	public int firstEmpty = 1;
	public int selectedRow = 0;
	
	public LibraryTable() {
		populateHeaders();
		for (Node n : getChildren()) {
			n.addEventHandler(MouseEvent.MOUSE_CLICKED, onBookSelection);
		}
	}

	public void clear() {
		getChildren().clear();
		firstEmpty = 1;
		populateHeaders();
	}

	public void populate(Library[] libraries) {
		for (Library l : libraries) {
			addRow(firstEmpty++, new Label("" + l.lid), new Label(l.name), new Label(l.location));
		}
	}
	
	private void populateHeaders() {
		addRow(0, new Label("ID"), new Label("Name"), new Label("Location"));
	}
	
	EventHandler<MouseEvent> onBookSelection = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent arg0) {
			selectedRow = getRowIndex((Node) arg0.getSource());
		}
	};
}