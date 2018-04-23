import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerInstance implements Runnable {
	Socket socket;

	public ServerInstance(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			for (String m : Database.getIntance().getMembers()) {
				bw.write(m);
				bw.write('\n');
			}
			bw.write('\n');
			bw.flush();
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException("", e);
		}
	}
}