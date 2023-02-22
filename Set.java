package nl.vu.labs.phoenix.ap;

public class Set<T extends Comparable<T>> implements SetInterface<T> {

	private ListInterface<T> set;

	Set() {
		set = new LinkedList<>();
	}

	@Override
	public boolean add(T t) {
		if (isInSet(t)) return false;

		set.insert(t);
		return true;
	}

	@Override
	public T get() {
		return set.retrieve();
	}

	@Override
	public boolean remove(T t) {
		if (!isInSet(t)) return false;

		set.remove();
		return true;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public SetInterface<T> copy() {
		SetInterface<T> result = new Set<>();

		if (isEmpty()) return result;

		set.goToFirst();
		
		do result.add(get());
		while (set.goToNext());

		return result;
	}

	@Override
	public SetInterface<T> union(SetInterface<T> t) {
		SetInterface<T> result = copy();

		if (!t.isEmpty()) {
			SetInterface<T> tCopy = t.copy();
			while (!tCopy.isEmpty()) {
				result.add(tCopy.get());
				tCopy.remove(tCopy.get());
			}
		}

		return result;
	}

	@Override
	public SetInterface<T> intersection(SetInterface<T> t) {
		SetInterface<T> result = copy();

		if (!t.isEmpty()) {
			SetInterface<T> resultCopy = result.copy();
			while (!resultCopy.isEmpty()) {
				if (!t.isInSet(resultCopy.get())) {
					result.remove(resultCopy.get());
				}

				resultCopy.remove(resultCopy.get());
			}
		} else result.init();

		return result;
	}

	@Override
	public SetInterface<T> complement(SetInterface<T> t) {
		SetInterface<T> result = copy();

		if (!result.isEmpty()) {
			SetInterface<T> intersection = intersection(t);

			while (!intersection.isEmpty()) {
				result.remove(intersection.get());
				intersection.remove(intersection.get());
			}
		}

		return result;
	}

	@Override
	public SetInterface<T> symmetricDifference(SetInterface<T> t) {
		SetInterface<T> result = union(t);
		SetInterface<T> intersection = intersection(t);

		while (!intersection.isEmpty()) {
			result.remove(intersection.get());
			intersection.remove(intersection.get());
		}

		return result;
	}

	@Override
	public void init() {
		set.init();
	}

	@Override
	public boolean isInSet(T t) {
		return (set.find(t));
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}
}