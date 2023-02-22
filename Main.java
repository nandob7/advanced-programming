package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

	private void start() {
		InterpreterInterface<Set<BigInteger>> interpreter = new Interpreter<Set<BigInteger>>();

		Scanner in = new Scanner(System.in);

		while (in.hasNextLine()) {
			interpreter.eval(in.nextLine());
		}
	}

	public static void main(String[] args) {
		new Main().start();
	}
}
