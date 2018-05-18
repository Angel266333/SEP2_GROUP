package com.via.clms.client.controllers.containers;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public abstract class ClickableTable<T> extends GridPane {

	abstract VBox[] makeBoxes(T dataElement);
	abstract VBox[] makeHeaderBoxes();

	public int firstEmpty = 1;
	private ClickListener listener = null;
	private int padding = 0;

	public void clear() {
		getChildren().clear();
		firstEmpty = 1;
		populateHeaders();
	}

	private void setupBoxes() {
		for (Node n : getChildren()) {
			n.addEventHandler(MouseEvent.MOUSE_CLICKED, onRowSelection);
		}
	}

	public void populate(T[] dataElements) {
		populateHeaders();
		for (T element : dataElements) {
			addRow(firstEmpty++, makeBoxes(element));
		}
		setupBoxes();
	}

	public void populateHeaders() {
		addRow(0, makeHeaderBoxes());
	}

	public void setListener(ClickListener listener) {
		this.listener = listener;
	}

	public void setBoxPadding(int padding) {
		this.padding = padding;
	}

	protected VBox makeBox(String s) {
		VBox box = new VBox(makeLabel(s));
		box.setFillWidth(true);
		return box;
	}

	protected VBox makeBox(long l) {
		VBox box = new VBox(makeLabel("" + l));
		box.setFillWidth(true);
		return box;
	}

	protected VBox makeBox(int i) {
		VBox box = new VBox(makeLabel("" + i));
		box.setFillWidth(true);
		return box;
	}

	private Label makeLabel(String s) {
		Label l = new Label(s);
		l.setStyle("-fx-padding: " + padding);
		return l;
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
