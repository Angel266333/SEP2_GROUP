import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public abstract class ClickableTable<T> extends GridPane {

	public abstract Label[] makeLabels(T dataElement);
	public abstract Label[] makeHeaderLabels();
	
	public int firstEmpty = 1;
	private ClickListener listener = null;
	
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

	public void setListener(ClickListener listener) {
		this.listener = listener;
	}
	
	EventHandler<MouseEvent> onRowSelection = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent arg0) {
			if(listener != null) {
				int i = getRowIndex((Node) arg0.getSource());
				if(arg0.getClickCount() == 2) {
					listener.doubleClick(i);
					return;
				}
				listener.click(i - 1);
			}
		}
	};	
}
