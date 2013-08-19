package eu.compassresearch.core.interpreter.debug;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.lex.LexLocation;

import eu.compassresearch.core.interpreter.api.CmlInterpretationStatus;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.values.CmlOperationValue;
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
	
	private static List<CmlProcessDTO> convertProcessTreeIntoList(CmlProcessDTO process)
	{
		List<CmlProcessDTO> children = new LinkedList<CmlProcessDTO>();
		if(process != null)
		{
			children.add(process);

			if(process.getLeftChild() != null)
				children.addAll(convertProcessTreeIntoList(process.getLeftChild()));

			if(process.getRightChild() != null)
				children.addAll(convertProcessTreeIntoList(process.getLeftChild()));
		}

		return children;
	}
	
	private final CmlProcessDTO topLevelProcess;
	private InterpreterErrorDTO[] errors = null;
	private final CmlInterpretationStatus state;
	private Breakpoint bp = null;

	/**
	 * Dummy constructor for serialization
	 */
	protected CmlInterpreterStateDTO()
	{
		state = null;
		topLevelProcess = null;
	}

	public CmlInterpreterStateDTO(CmlBehaviour topProcess, CmlInterpretationStatus state)
	{
		if(topProcess != null)
			topLevelProcess = new CmlProcessDTO(topProcess, null);
		else
			topLevelProcess = null;
		this.state = state;
	}
	
	public CmlInterpreterStateDTO(CmlBehaviour topProcess, CmlInterpretationStatus state, Breakpoint bp)
	{
		this(topProcess,state);
		this.bp = bp;
	}
	
	public CmlInterpreterStateDTO(CmlInterpretationStatus state)
	{
		this.state = state;
		topLevelProcess = null;
	}

	public List<CmlProcessDTO> getAllProcesses()
	{
		if(topLevelProcess != null)
			return convertProcessTreeIntoList(this.topLevelProcess);
		else
			return new LinkedList<CmlProcessDTO>();
	}

	public CmlProcessDTO getToplevelProcess()
	{
		return this.topLevelProcess;
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
				"topProcess: " + (this.topLevelProcess != null ? this.topLevelProcess.toString() : "NA");
	}
}
