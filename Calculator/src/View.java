
import java.util.Scanner;

public class View {

	public static void main(String[] args) {
		String operation = null;
		Calculator calculator = new Calculator();
		Scanner scanner = new Scanner(System.in);

		System.out.println(calculator.getResult());

		while (true) {

			if (scanner.hasNextDouble()) {

				double enteredNumber = 0;
				enteredNumber = scanner.nextDouble();
				calculator.add(enteredNumber);

			}

			while (scanner.hasNextLine() && !calculator.getAvailableOperations().contains(operation)) {

				String sign = scanner.nextLine();

				if ((sign.length() == 1) && (calculator.getAvailableOperations().contains(sign))) {
					operation = sign;
					if (operation.equals("o")) {
						calculator.clear();
					}

				}

				System.out.println(calculator.getResult());

			}

			if (!operation.equals("o") && scanner.hasNextDouble()) {

				double number = scanner.nextDouble();
				switch (operation) {

				case ("+"):
					calculator.add(number);
					break;
				case ("-"):
					calculator.subtract(number);
					break;
				case ("*"):
					calculator.multiply(number);
					break;
				case ("/"):
					calculator.divide(number);
					break;

				}

				System.out.println(calculator.getResult());

			}

			operation = null;

		}

	}

}
