package nl.vu.labs.phoenix.ap;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * A set interpreter for sets of elements of type T
 */
public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {

	private HashMap<Identifier, T> hashTable = new HashMap<Identifier, T>();

	@Override
	public T getMemory(String v) {
		try {
			Identifier key = identifier(new Scanner(v));
			return hashTable.getOrDefault(key, null);
		} catch (APException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public T eval(String s) {
		try {
			return statement(checkEvenBrackets(new Scanner(s)));
		} catch (APException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private Scanner checkEvenBrackets(Scanner in) throws APException {
		String line = "";
		int openingBrackets, closingBrackets;
		openingBrackets = closingBrackets = 0;
		skipSpaces(in);
		
		while (in.hasNext()) {
			if (nextCharIs(in, '{') || nextCharIs(in, '(')) openingBrackets++;
			if (nextCharIs(in, '}') || nextCharIs(in, ')')) closingBrackets++;

			line += nextChar(in);
		}

		if (openingBrackets < closingBrackets) throw new APException("Opening bracket(s) missing");
		if (openingBrackets > closingBrackets) throw new APException("Closing bracket(s) missing");
	
		return new Scanner(line);
	}

	private void skipSpaces(Scanner in) {
		in.useDelimiter("");
		while (nextCharIs(in, ' ')) nextChar(in);
	}

	private boolean skipChar(Scanner in, char c) {
		skipSpaces(in);

		if (!nextCharIs(in, c)) return false;

		nextChar(in); // skips the character
		skipSpaces(in);

		return true;
	}

	private char nextChar(Scanner in) {
		return in.next().charAt(0);
	}

	private boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}

	private boolean nextCharIsDigit(Scanner in) {
		return in.hasNext("[0-9]");
	}

	private boolean nextCharIs(Scanner in, char c) {
		return in.hasNext(Pattern.quote(c + ""));
	}

	private T statement(Scanner in) throws APException {
		if (!in.hasNext()) throw new APException("Input missing");
		else if (skipChar(in, '?')) return print(in);
		else if (nextCharIsLetter(in) || nextCharIs(in, '/')) { 
			if (nextCharIsLetter(in)) assignment(in);
			
			return null;
		}
		else throw new APException("Incorrect statement");
		
	}

	private void assignment(Scanner in) throws APException {
		Identifier key = identifier(in);

		if (skipChar(in, '=')) {
			if (!in.hasNext()) throw new APException("Missing expression to assign");
			else hashTable.put(key, expression(in));
		} else throw new APException("'=' expected after the first identifier.");
	}

	private T print(Scanner print) throws APException {
		T result = expression(print);
		SetInterface<BigInteger> copy = result.copy();

		while (!copy.isEmpty()) {
			System.out.printf("%d ", copy.get());
			copy.remove(copy.get());
		}

		System.out.println();
		return result;
	}

	private Identifier identifier(Scanner in) throws APException {
		in.useDelimiter("");
		if (!nextCharIsLetter(in)) throw new APException("Identifier has to start with a letter.");

		Identifier result = new Identifier(letter(in));

		while (in.hasNext() && !nextCharIs(in, ' ')) {
			if (nextIsAdditiveOperator(in) || nextIsMultiplicativeOperator(in) || nextCharIs(in, ')') || nextCharIs(in, '=')) {
				return result;
			}

			if (!nextCharIsLetter(in) && !nextCharIsDigit(in)) {
				throw new APException("Identifiers can only contain alphanumeric characters.\n");
			}

			result.add(nextChar(in));
		}

		return result;
	}

	private T expression(Scanner in) throws APException {
		T result, termResult;
		result = term(in);

		while (nextIsAdditiveOperator(in)) {
			char addOp = nextChar(in);
			termResult = term(in);

			if (addOp == '+') result = (T) result.union(termResult);
			else if (addOp == '-') result = (T) result.complement(termResult);
			else result = (T) result.symmetricDifference(termResult);
		}

		return result;
	}

	private T term(Scanner in) throws APException {
		T result = factor(in);

		while (nextIsMultiplicativeOperator(in)) {
			nextChar(in); //skips operator
			result = (T) result.intersection(factor(in));
		}

		return result;
	}

	private T factor(Scanner in) throws APException {
		T result;
		skipSpaces(in);

		if (nextCharIsLetter(in)) {
			result = hashTable.get(identifier(in));

			if (result == null) throw new APException("Variable doesn't exist.");

		} else if (skipChar(in, '{')) result = set(in);
		else if (skipChar(in, '(')) result = complexFactor(in);
		else throw new APException("Not a correct factor");

		skipSpaces(in);
		return result;
	}

	private T complexFactor(Scanner in) throws APException {
		T result = expression(in);
		
		if (skipChar(in, ')')) {
			if (in.hasNext() && !nextIsAdditiveOperator(in) && !nextIsMultiplicativeOperator(in) && !nextCharIs(in, ')')) {
				throw new APException("Operator expected");
			}
		} else throw new APException("Closing bracket ')' expected.");

		return result;
	}

	private T set(Scanner in) throws APException {
		String set = "";

		while (in.hasNext() && !nextCharIs(in, '}')) {
			set += nextChar(in);
		}

		if (skipChar(in, '}')) {
			if (in.hasNext() && !nextIsAdditiveOperator(in) && !nextIsMultiplicativeOperator(in) && !nextCharIs(in, ')')) {
				throw new APException("Operator expected");
			}
		} else throw new APException("Closing bracket '}' expected.");

		return rowNaturalNumbers(new Scanner(set));
	}

	private T rowNaturalNumbers(Scanner in) throws APException {
		Set<BigInteger> result = new Set<>();
		skipSpaces(in);

		if (!in.hasNext()) return (T) result;
		else if (!nextCharIsDigit(in)) throw new APException("Set should be empty or start with a natural number");

		String[] naturalNumberArray = in.nextLine().split(",");

		for (int i = 0; i < naturalNumberArray.length; i++) {
			result.add(naturalNumber(new Scanner(naturalNumberArray[i])));
		}

		return (T) result;
	}

	private boolean nextIsAdditiveOperator(Scanner in) {
		return (nextCharIs(in, '+') || nextCharIs(in, '|') || nextCharIs(in, '-'));
	}

	private boolean nextIsMultiplicativeOperator(Scanner in) {
		return (nextCharIs(in, '*'));
	}

	private BigInteger naturalNumber(Scanner in) throws APException {
		BigInteger result = BigInteger.ZERO;
		skipSpaces(in);

		if (nextCharIs(in, '0')) {
			result = zero(in);
			skipSpaces(in);

			if (in.hasNext()) throw new APException("Positive number cannot start with 0");
		} else result = positiveNumber(in);

		skipSpaces(in);

		if (in.hasNext()) {
			if (!nextCharIsDigit(in)) throw new APException("Only digits allowed in natural number");
			throw new APException("No spaces allowed within the natural number.");
		}

		return result;
	}

	private BigInteger positiveNumber(Scanner in) throws APException {
		if (nextCharIs(in, '-')) throw new APException("No sign allowed");

		BigInteger result = notZero(in);

		while (nextCharIsDigit(in)) {
			result = result.multiply(BigInteger.TEN).add(number(in));
		}

		return result;
	}

	private BigInteger number(Scanner in) throws APException {
		if (!nextCharIsDigit(in)) throw new APException("Number expected");

		return new BigInteger(in.next());
	}

	private BigInteger zero(Scanner in) throws APException {
		if (!nextCharIs(in, '0')) throw new APException("Zero expected");

		return in.nextBigInteger();
	}

	private BigInteger notZero(Scanner in) throws APException {
		if (!in.hasNext("[1-9]")) throw new APException("Not zero expected");

		return in.nextBigInteger();
	}

	private char letter(Scanner in) throws APException {
		if (!nextCharIsLetter(in)) throw new APException("Letter expected");

		return in.next().charAt(0);
	}

}