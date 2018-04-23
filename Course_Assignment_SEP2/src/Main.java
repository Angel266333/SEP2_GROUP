import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner keyboard = new Scanner(System.in);

		int parseSelection;
		System.out.println("-----==CLIENT PROGRAM MENU SELECTION==-----");
		System.out.println("0) Exit Application");
		System.out.println("1) Find members that have not paid membership fee");
		parseSelection = keyboard.nextInt();

		switch (parseSelection) {
		case 0:
			break;
		case 1:
			Client client = new Client(new Listener() {
				@Override
				public void onOutput(ArrayList<String> userList) {
					System.out.println(userList);
				}
			});
		}
	}
}