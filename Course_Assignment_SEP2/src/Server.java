import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(2910);
		BufferedWriter bw;

		while(true) {
			Socket socket = ss.accept();
			(new Thread(new ServerInstance(socket))).start();
		}
	}
}