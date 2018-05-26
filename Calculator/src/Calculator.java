import java.util.ArrayList;

public class Calculator {

	private double result;
	private ArrayList<String> availableOperations;

	public Calculator() {

		this.result = 0;

		this.availableOperations = new ArrayList<String>();
		availableOperations.add("+");
		availableOperations.add("-");
		availableOperations.add("/");
		availableOperations.add("*");
		availableOperations.add("o"); // for ON button

	}

	/*
	 * Operations you can do on the calculator for a simple calculator, the first
	 * calculation number is always the current result which is already displayed,
	 * and then each method takes only one number as an argument to complete the
	 * operation with
	 */

	public void add(double number) {
		this.result += number;
	}

	public void subtract(double number) {
		this.result -= number;

	}

	public void multiply(double number) {
		this.result = result * number;

	}

	public void divide(double number) {
		this.result = result / number;

	}
	


	public double getResult() {  //getting the result for the display
		return this.result;
	}

	public ArrayList<String> getAvailableOperations() { 
		return this.availableOperations;
	}

	public void clear() {  //espessially important for On button
		result = 0;
	}

}
