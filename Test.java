import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Test implements KeyListener {
	static Thread x;
	static Thread y;
	static Thread z;

	public static void main(String[] args) {
		Subject s = new Subject();
		Observer a = new ObserverOne("One", "Red", s);
		Observer b = new ObserverOne("Two", "Green", s);
		Observer c = new ObserverOne("Three", "Blue", s);

		s.addObserver(a);
		s.addObserver(b);
		s.addObserver(c);

		x = new Thread(a);
		y = new Thread(b);
		z = new Thread(c);



		x.start();
		y.start();
		z.start();


	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'q') {
			x.interrupt();
			y.interrupt();
			z.interrupt();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
