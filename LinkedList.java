package nl.vu.labs.phoenix.ap;

public class LinkedList<E extends Comparable<E>> implements ListInterface<E> {

	private class Node {

		E data;
		Node prior, next;

		public Node(E data) {
			this(data, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}

	}

	Node current;
	private int numberOfElements;

	public LinkedList() {
		current = null;
		numberOfElements = 0;
	}

	@Override
	public boolean isEmpty() {
		return current == null;
	}

	@Override
	public ListInterface<E> init() {
		numberOfElements = 0;
		current = null;
		return this;
	}

	@Override
	public int size() {
		return numberOfElements;
	}

	@Override
	public ListInterface<E> insert(E d) { // 4 CASES
		numberOfElements += 1;

		if (isEmpty()) current = new Node(d); // CASE 1
		else {
			goToFirst();

			if (current.data.compareTo(d) > 0) { // CASE 2
				current = current.prior = new Node(d, null, current);
			} else {
				while (current.next != null && current.next.data.compareTo(d) < 0) {
					goToNext();
				}

				if (current.next == null) { // CASE 3
					current = current.next = new Node(d, current, null);
				} else { // CASE 4
					current = current.next = current.next.prior = new Node(d, current, current.next);
				}
			}
		}

		return this;
	}

	@Override
	public E retrieve() {
		return current.data;
	}

	@Override
	public ListInterface<E> remove() { // 4 CASES
		if (size() == 1) init(); // CASE 1
		else {
			if (current.prior == null) { // CASE 2
				goToNext();
				current.prior = current.prior.next = null;
			} else if (current.next == null) { // CASE 3
				goToPrevious();
				current.next = current.next.prior = null;
			} else { // CASE 4
				current.next.prior = current.prior;
				current = current.prior.next = current.next;
			}

			numberOfElements--;
		}

		return this;
	}

	@Override
	public boolean find(E d) {
		if (isEmpty()) return false;

		goToFirst();

		if(current.data.compareTo(d) > 0) return false;

		do {
			if (current.data.compareTo(d) == 0)	return true;
		} while (current.data.compareTo(d) < 0 && goToNext());

		return false;
	}

	@Override
	public boolean goToFirst() {
		if (isEmpty()) return false;

		while (current.prior != null) {
			goToPrevious();
		}

		return true;
	}

	@Override
	public boolean goToLast() {
		if (isEmpty()) return false;

		while (current.next != null) {
			goToNext();
		}

		return true;
	}

	@Override
	public boolean goToNext() {
		if (isEmpty() || current.next == null) {
			return false;
		}

		current = current.next;
		return true;
	}

	@Override
	public boolean goToPrevious() {
		if (isEmpty() || current.prior == null) {
			return false;
		}

		current = current.prior;
		return true;
	}

	@Override
	public ListInterface<E> copy() {
		ListInterface<E> copy = new LinkedList<>();

		if (!isEmpty()) {
			goToFirst();
			while (current.next != null) {
				copy.insert(current.data);
				goToNext();
			}

			copy.insert(current.data);
		}

		return copy;
	}
}