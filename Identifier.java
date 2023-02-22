package nl.vu.labs.phoenix.ap;

public class Identifier implements IdentifierInterface {

	private StringBuffer sb;

	Identifier(char c) {
		sb = new StringBuffer();
		sb.append(c);
	}

	@Override
	public String value() {
		return sb.toString();
	}

	@Override
	public void init(char c) {
		sb.setCharAt(0, c);
		sb.delete(1, size());
	}

	@Override
	public void add(char c) {
		sb.append(c);
	}

	@Override
	public int size() {
		return sb.length();
	}

	@Override
	public boolean equals(Object src) {
		if (src == null) return false;

		if (this == src) return true;

		if (getClass() != src.getClass()) return false;

		Identifier rhs = (Identifier) src;

		return hashCode() == rhs.hashCode() && this.toString().equals(rhs.toString());

	}

	@Override
	public int hashCode() {
		return sb.toString().hashCode();
	}

}