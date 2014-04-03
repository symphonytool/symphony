package eu.compassresearch.core.analysis.modelchecker.graphBuilder.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import eu.compassresearch.core.analysis.modelchecker.graphBuilder.transition.Transition;

public class LinkedListTransition extends LinkedList<Transition> {

	public LinkedListTransition() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LinkedListTransition(Collection<Transition> arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public int[] indexesOf(Transition transition){
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		for (int i = 0; i < this.size(); i++) {
			Transition t = this.get(i);
			if(t.getEvent().equals(transition.getEvent()) && t.getSourceState().equals(transition.getSourceState())){
				indexes.add(i);
			}
		}
		int[] result = new int[indexes.size()];
		for (int i = 0; i < indexes.size(); i++) {
			result[i] = indexes.get(i);
		}
		
		return result;
	}
	
	

	public boolean containsByEvent(Transition transition, int fromIndex){
		boolean result = false;
		if(fromIndex >= 0 && fromIndex < this.size()){
			for (int i = fromIndex; i <= this.size(); i++) {
				Transition trans = this.get(i);
				if(trans.getEvent().equals(transition.getEvent())){
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public boolean containsByEvent(Transition transition, int fromIndex, int toIndex){
		boolean result = false;
		if(fromIndex >= 0 && fromIndex <= toIndex && toIndex < this.size()){
			for (int i = fromIndex; i <= toIndex; i++) {
				Transition trans = this.get(i);
				if(trans.getEvent().equals(transition.getEvent())){
					result = true;
					break;
				}
			}
		}
		return result;
	}

	
}
