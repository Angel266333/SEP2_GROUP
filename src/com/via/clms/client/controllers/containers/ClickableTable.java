package com.via.clms.client.controllers.containers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public abstract class ClickableTable<T> extends GridPane {
	
	public abstract Label[] makeLabels(T dataElement);
	public abstract Label[] makeHeaderLabels();
	
	public int firstEmpty = 1;
	public int selectedRow = 0;
	
	public ClickableTable() {
		populateHeaders();
		for (Node n : getChildren()) {
			n.addEventHandler(MouseEvent.MOUSE_CLICKED, onRowSelection);
		}
	}

	public void clear() {
		getChildren().clear();
		firstEmpty = 1;
		populateHeaders();
	}

	public void populate(T[] dataElements) {
		for (T element : dataElements) {
			addRow(firstEmpty++, makeLabels(element));
		}
	}
	
	public void populateHeaders() {
		addRow(0, makeHeaderLabels());
	}
	
	EventHandler<MouseEvent> onRowSelection = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent arg0) {
			selectedRow = getRowIndex((Node) arg0.getSource());
		}
	};	
}