
import java.util.Scanner;

/*
 * This class simulates the view of the calculator to the console
 * The console keeps shoing the result after entering each value just like a simple calculator
 * Remember to press enter after and befor entering each operation sign
 */
public class View {

	public static void main(String[] args) {
		String operation = null; // This is where the operation sign is stored
		Calculator calculator = new Calculator();
		Scanner scanner = new Scanner(System.in);

		System.out.println(calculator.getResult());

		while (true) {

			if (scanner.hasNextDouble()) { // The state of catching a number

				double enteredNumber = 0;
				enteredNumber = scanner.nextDouble();
				calculator.add(enteredNumber);

			}

			/*
			 * next while loop is to validate the operation sign and to make sure the user
			 * can change the operation sign just by pressing another sign.
			 */

			while (scanner.hasNextLine() && !calculator.getAvailableOperations().contains(operation)) {

				String sign = scanner.nextLine();

				if ((sign.length() == 1) && (calculator.getAvailableOperations().contains(sign))) {
					operation = sign;
					if (operation.equals("o")) { // clearing the display when pressing On
						calculator.clear();
					}

				}

				System.out.println(calculator.getResult());

			}
			
			/*
			 * catching the second number, sending it to the model to do the calculation, and displaying the result
			 */

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

			operation = null;  // forgetting the operation sign

		}

	}

}
