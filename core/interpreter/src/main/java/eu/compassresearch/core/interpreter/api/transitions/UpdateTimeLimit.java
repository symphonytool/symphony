package eu.compassresearch.core.interpreter.api.transitions;

import eu.compassresearch.core.interpreter.api.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.ops.MapOperation;

public class UpdateTimeLimit implements MapOperation
{
	long startTimeVal;
	long val;
	CmlBehaviour owner;
	
	public UpdateTimeLimit(CmlBehaviour owner, long val, long startTimeVal)
	{
		this.owner = owner;
		this.val = val;		
		this.startTimeVal = startTimeVal;
	}
	
	@Override
	public CmlTransition apply(CmlTransition transition)
	{
		if(transition instanceof TimedTransition)
		{
			long limit = val - (owner.getCurrentTime() - startTimeVal);
			return ((TimedTransition)transition).synchronizeWith(new TimedTransition(owner, limit));
		}
		else
			return transition;
	}

}
