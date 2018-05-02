import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

	private ArrayList<String> input;

	private Socket socket;
	private BufferedReader reader;
	private Listener listener;

	public String getSocketParameters;

	public Client(Listener listener) throws IOException {
		this.listener = listener;
		try {
			socket = new Socket("localhost", 2910);
		} catch (UnknownHostException e) {
			System.out.println("Could not connect to server side!");
		}
		ClientHandler cHandler = new ClientHandler();
		cHandler.run();
	}

	public String getAllUnpaidMembers() {

		StringBuilder builder = new StringBuilder();

		for (String s : input) {
			builder.append(s);
			builder.append(System.lineSeparator());
		}
		return builder.toString();
	}

	private class ClientHandler extends Thread {
		@Override
		public void run() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			getSocketParameters = socket.toString();
			String line = null;
			input = new ArrayList<String>();
			while (true) {
				try {
					line = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (line == null || line.equals("")) {
					break;
				}
				input.add(line);
			}
			listener.onOutput(getAllUnpaidMembers());
		}
	}
}