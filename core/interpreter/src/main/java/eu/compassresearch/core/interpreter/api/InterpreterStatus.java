package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import eu.compassresearch.core.interpreter.cml.CmlBehaviour;
import eu.compassresearch.core.interpreter.events.InterpreterStatusEvent;

/**
 * Represents the status of the interpreter at a specific simulation point. 
 * @author akm
 *
 */
public class InterpreterStatus {

	final private CmlProcessInfo[] processInfos;
	final private int topLevelProcessIndex;
	private InterpreterError[] errors = null;
	private final CmlInterpreterState state;
	
	public InterpreterStatus(CmlBehaviour topProcess, CmlInterpreterState state)
	{
		this.processInfos = new CmlProcessInfo[1];
		this.processInfos[0] = new CmlProcessInfo(topProcess.name().getName(),
				topProcess.getTraceModel(),
				topProcess.level(),
				topProcess instanceof CmlBehaviour,
				topProcess.getState());

		topLevelProcessIndex = 0;
		this.state = state;
	}
			
	public List<CmlProcessInfo> getAllProcessInfos()
	{
		return Arrays.asList(this.processInfos);
	}
	
	public CmlProcessInfo getToplevelProcessInfo()
	{
		return processInfos[topLevelProcessIndex];
	}
	
	public List<InterpreterError> getErrors() {
		return Arrays.asList(errors);
	}

	public void AddError(InterpreterError error) {
		
		if(errors == null)
			errors = new InterpreterError[]{error};
		else
		{
			errors = Arrays.copyOf(errors, errors.length + 1);
		}
	}

	public boolean hasErrors()
	{
		return errors != null;
	}
	
	public CmlInterpreterState getInterpreterState()
	{
		return state;
	}
			
}
