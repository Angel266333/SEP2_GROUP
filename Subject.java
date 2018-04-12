import java.util.ArrayList;

public class Subject {
	ArrayList<Observer> observers;
	String state;

	public Subject() {
		observers = new ArrayList<>();
		state = "Off";
	}

	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	public void updateAll() {
		for(Observer o : observers) {
			o.update(state);
		}
	}

	public void changeState(String state) {
		synchronized (state) {
			this.state = state;
			updateAll();
		}
	}
}