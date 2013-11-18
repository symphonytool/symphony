package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.ArrayList;

public class ArrayListSet<E> extends ArrayList<E> {

	@Override
	public boolean add(E e) {
		boolean result = false;
		if(!this.contains(e)){
			result = super.add(e);
		}
		return result;
	}
	
}
