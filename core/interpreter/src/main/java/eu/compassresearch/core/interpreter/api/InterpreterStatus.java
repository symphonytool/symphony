package eu.compassresearch.core.interpreter.api;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.lex.LexLocation;

import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;

/**
 * Represents the status of the interpreter at a specific simulation point. 
 * @author akm
 *
 */
public class InterpreterStatus {

	final private List<CmlProcessInfo> processInfos;
	private InterpreterError[] errors = null;
	private final CmlInterpreterState state;

	protected InterpreterStatus()
	{
		state = null;
		processInfos = null;
	}

	private static List<CmlBehaviour> extractAllRunningProcesses(CmlBehaviour topProcess)
	{
		List<CmlBehaviour> children = new LinkedList<CmlBehaviour>();
		if(topProcess != null)
		{
			children.add(topProcess);

			if(topProcess.getLeftChild() != null)
				children.addAll(extractAllRunningProcesses(topProcess.getLeftChild()));

			if(topProcess.getRightChild() != null)
				children.addAll(extractAllRunningProcesses(topProcess.getLeftChild()));
		}

		return children;
	}

	public InterpreterStatus(CmlBehaviour topProcess, CmlInterpreterState state)
	{
		this.processInfos = new LinkedList<CmlProcessInfo>();
		for(CmlBehaviour cmlBehavior : extractAllRunningProcesses(topProcess))
		{
			this.processInfos.add(new CmlProcessInfo(cmlBehavior.name(),
					cmlBehavior.getTraceModel(),
					cmlBehavior.level(),
					cmlBehavior instanceof CmlBehaviour,
					cmlBehavior.getState(),
					(LexLocation)cmlBehavior.getNextState().second.location));
		}

		this.state = state;
	}
	
	public InterpreterStatus(CmlInterpreterState state)
	{
		this.processInfos = new LinkedList<CmlProcessInfo>();
		this.state = state;
	}

	public List<CmlProcessInfo> getAllProcessInfos()
	{
		return this.processInfos;
	}

	public CmlProcessInfo getToplevelProcessInfo()
	{
		return processInfos.get(0);
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
				"topProcess: " + (this.processInfos.size() > 0 ? this.processInfos.get(0).toString() : "NA");
	}
}
