package eu.compassresearch.core.analysis.modelchecker.visitors;

import java.util.Stack;

/**
 * It represents a stack of sets of events to be considered qhen generating lieIn facts 
 */
public class SetStack extends Stack{
	public SetStack copy(){
		SetStack result = new SetStack();
		result.addAll(this);
		return result;
	}
}
