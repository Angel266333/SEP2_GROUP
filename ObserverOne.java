import java.util.Random;

public class ObserverOne implements Observer {
	String state;
	String name;
	Subject subject;
	Random random;
	String color;

	public ObserverOne(String name, String color, Subject subject) {
		state = "Off";
		this.name = name;
		this.subject = subject;
		random = new Random();
		this.color = color;
	}

	@Override
	public void update(String state) {
		synchronized(state) {
			this.state = state;
		}
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			synchronized(state) {
				System.out.println(name + ": " + state + "\n");
			}
			if(doChange()) {
				subject.changeState(color);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	private boolean doChange() {
		return 0 == random.nextInt(4);
	}
}
