package eu.compassresearch.core.typechecker;

import java.util.Collection;
import java.util.LinkedList;

@SuppressWarnings("serial")
public class ShieldedList<T> extends LinkedList<T> {

	@Override
	public T removeFirst() {
		return super.removeFirst();
	}

	@Override
	public T removeLast() {
		return super.removeLast();
	}

	@Override
	public void addFirst(T e) {
		super.addFirst(e);
	}

	@Override
	public void addLast(T e) {
		super.addLast(e);
	}

	@Override
	public boolean add(T e) {
		return super.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return super.remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return super.addAll(index, c);
	}

	@Override
	public void clear() {
		super.clear();
	}

	public void add(int index, T element) {
		super.add(index, element);
	}

	@Override
	public T remove(int index) {
		return super.remove(index);
	}

	@Override
	public T remove() {
		return super.remove();
	}

	@Override
	public void push(T e) {
		super.push(e);
	}

	@Override
	public T pop() {
		return super.pop();
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		return super.removeFirstOccurrence(o);
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		return super.removeLastOccurrence(o);
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		super.removeRange(fromIndex, toIndex);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return super.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return super.retainAll(c);
	}

}
