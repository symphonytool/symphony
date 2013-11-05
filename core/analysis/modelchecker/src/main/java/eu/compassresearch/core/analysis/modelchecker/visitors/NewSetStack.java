package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.Stack;

/**
 * It represents a stack of sets of events to be considered qhen generating lieIn facts 
 */
@SuppressWarnings("serial")
public class NewSetStack<T> extends Stack<T>{
	
	public NewSetStack<T> copy(){
		NewSetStack<T> result = new NewSetStack<T>();
		result.addAll(this);
		return result;
	}
}
