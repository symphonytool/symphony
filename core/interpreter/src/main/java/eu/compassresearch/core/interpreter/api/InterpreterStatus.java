package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.List;

import org.overture.ast.lex.LexLocation;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

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
	
	protected InterpreterStatus()
	{
		state = null;
		processInfos = null;
		topLevelProcessIndex = 0;
	}
	
	public InterpreterStatus(CmlBehaviour topProcess, CmlInterpreterState state)
	{
		this.processInfos = new CmlProcessInfo[1];
		this.processInfos[0] = new CmlProcessInfo(topProcess.name().getFullName(),
				topProcess.getTraceModel(),
				topProcess.level(),
				topProcess instanceof CmlBehaviour,
				topProcess.getState(),
				(LexLocation)topProcess.getNextState().second.location);

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
		if(errors != null)
			return Arrays.asList(errors);
		else
			return null;
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
			
	@Override
	public String toString() {
		return "CmlInterpreterState: " + state + System.lineSeparator() + 
				"topProcess: " + this.processInfos[0];
	}
}
