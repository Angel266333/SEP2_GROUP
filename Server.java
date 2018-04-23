import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(2910);
		Socket socket;
		BufferedWriter bw;

		while(true) {
			socket = ss.accept();
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			for(String m : Database.getIntance().getMembers()) {
				bw.write(m + '\n');
			}
			bw.write("\n");
			bw.flush();
			socket.close();
		}
	}
}
