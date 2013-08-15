package eu.compassresearch.core.interpreter.debug;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.lex.LexLocation;

import eu.compassresearch.core.interpreter.api.CmlInterpretationStatus;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.utility.LocationExtractor;

/**
 * Represents the status of the interpreter at a specific simulation point. 
 * @author akm
 *
 */
public class CmlInterpreterStateDTO {

	/**
	 * Static methods for creating the DTO
	 * @param activeInterpreter
	 * @return
	 */
	
	public static CmlInterpreterStateDTO createCmlInterpreterStateDTO(CmlInterpreter activeInterpreter)
	{
		return new CmlInterpreterStateDTO(
				activeInterpreter.getTopLevelProcess(), 
				activeInterpreter.getStatus(),
				activeInterpreter.getActiveBreakpoint()); 
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
	
	final private List<CmlProcessDTO> processInfos;
	private InterpreterErrorDTO[] errors = null;
	private final CmlInterpretationStatus state;
	private Breakpoint bp = null;

	protected CmlInterpreterStateDTO()
	{
		state = null;
		processInfos = null;
	}

	public CmlInterpreterStateDTO(CmlBehaviour topProcess, CmlInterpretationStatus state)
	{
		this.processInfos = new LinkedList<CmlProcessDTO>();
		for(CmlBehaviour cmlBehavior : extractAllRunningProcesses(topProcess))
		{
			this.processInfos.add(new CmlProcessDTO(cmlBehavior.name(),
					cmlBehavior.getTraceModel(),
					cmlBehavior.level(),
					cmlBehavior instanceof CmlBehaviour,
					cmlBehavior.getState(),
					LocationExtractor.extractLocation(cmlBehavior.getNextState().first)));
		}

		this.state = state;
	}
	
	public CmlInterpreterStateDTO(CmlBehaviour topProcess, CmlInterpretationStatus state, Breakpoint bp)
	{
		this(topProcess,state);
		this.bp = bp;
	}
	
	public CmlInterpreterStateDTO(CmlInterpretationStatus state)
	{
		this.processInfos = new LinkedList<CmlProcessDTO>();
		this.state = state;
	}

	public List<CmlProcessDTO> getAllProcesses()
	{
		return this.processInfos;
	}

	public CmlProcessDTO getToplevelProcess()
	{
		return processInfos.get(0);
	}

	public List<InterpreterErrorDTO> getErrors() {
		if(errors != null)
			return Arrays.asList(errors);
		else
			return null;
	}

	public void AddError(InterpreterErrorDTO error) {

		if(errors == null)
			errors = new InterpreterErrorDTO[]{error};
		else
		{
			errors = Arrays.copyOf(errors, errors.length + 1);
		}
	}

	public boolean hasErrors()
	{
		return errors != null;
	}

	public CmlInterpretationStatus getInterpreterState()
	{
		return state;
	}
	
	public boolean hasActiveBreakpoint()
	{
		return this.bp != null;
	}
	
	public Breakpoint getActiveBreakpoint()
	{
		return this.bp;
	}

	@Override
	public String toString() {
		return "CmlInterpreterState: " + state + System.lineSeparator() + 
				"topProcess: " + (this.processInfos.size() > 0 ? this.processInfos.get(0).toString() : "NA");
	}
}
