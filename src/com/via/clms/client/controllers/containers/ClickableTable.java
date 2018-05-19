package com.via.clms.client.controllers.containers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class ClickableTable<T> extends GridPane {

	abstract Label[] makeLabels(T dataElement);
	abstract Label[] makeHeaderLabels();

	public int firstEmpty = 1;
	private ClickListener listener = null;
	private int padding = 0;

	public void clear() {
		getChildren().clear();
		firstEmpty = 1;
		populateHeaders();
	}

	public void populate(T[] dataElements) {
		clear();
		for (T element : dataElements) {
			Label[] labels = makeLabels(element);
			VBox[] b = new VBox[labels.length];
			int i = 0;
			for(Label l : labels) {
				b[i] = new VBox(l);
				b[i].setFillWidth(true);
				b[i++].addEventHandler(MouseEvent.MOUSE_CLICKED, onRowSelection);
			}
			addRow(firstEmpty++, b);
		}
	}

	public void populateHeaders() {
		Label[] labels = makeHeaderLabels();
		VBox[] b = new VBox[labels.length];
		int i = 0;
		for(Label l : labels) {
			b[i] = new VBox(l);
			b[i++].setFillWidth(true);
		}
		addRow(0, b);
	}

	public void setListener(ClickListener listener) {
		this.listener = listener;
	}

	EventHandler<MouseEvent> onRowSelection = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent arg0) {
			if(listener != null) {
				int i = getRowIndex((Node) arg0.getSource());
				if(i == 0) {
					return;
				}
				if(arg0.getClickCount() == 2) {
					listener.doubleClick(i - 1);
					return;
				}
				listener.click(i - 1);
			}
		}
	};
}
