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
		availableOperations.add("o");

	}

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

	public double getResult() {
		return this.result;
	}

	public ArrayList<String> getAvailableOperations() {
		return this.availableOperations;
	}

	public void clear() {
		result = 0;
	}

}
