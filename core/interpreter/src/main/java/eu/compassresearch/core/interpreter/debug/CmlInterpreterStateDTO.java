package eu.compassresearch.core.interpreter.debug;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.overture.ast.intf.lex.ILexLocation;
import org.overture.ast.node.INode;

import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.behaviour.CmlBehaviour;
import eu.compassresearch.core.interpreter.api.transitions.CmlTransition;
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
				activeInterpreter.getState(),
				activeInterpreter.getActiveBreakpoint(),
				new LinkedList<TransitionDTO>()); 
	}
	
	public static CmlInterpreterStateDTO createCmlInterpreterStateDTO(CmlInterpreter activeInterpreter, List<TransitionDTO> events)
	{
		return new CmlInterpreterStateDTO(
				activeInterpreter.getTopLevelProcess(), 
				activeInterpreter.getState(),
				activeInterpreter.getActiveBreakpoint(),
				events); 
	}
	
	private static List<CmlProcessDTO> convertProcessTreeIntoList(CmlProcessDTO process)
	{
		List<CmlProcessDTO> children = new LinkedList<CmlProcessDTO>();
		
		if(process == null)
			return children;
		
		children.add(process);

		if(process.getLeftChild() != null)
			children.addAll(convertProcessTreeIntoList(process.getLeftChild()));

		if(process.getRightChild() != null)
			children.addAll(convertProcessTreeIntoList(process.getRightChild()));

		return children;
	}
	
	private final CmlProcessDTO topLevelProcess;
	private InterpreterErrorDTO[] errors = null;
	private final CmlInterpreterState state;
	private Breakpoint bp;
	private List<TransitionDTO> transitions;

	/**
	 * Dummy constructor for serialization
	 */
	protected CmlInterpreterStateDTO()
	{
		state = null;
		topLevelProcess = null;
		bp = null;
	}

	public CmlInterpreterStateDTO(CmlBehaviour topProcess, CmlInterpreterState state)
	{
		if(topProcess != null)
			topLevelProcess = new CmlProcessDTO(topProcess, null);
		else
			topLevelProcess = null;
		this.state = state;
	}
	
	public CmlInterpreterStateDTO(CmlBehaviour topProcess, CmlInterpreterState state, Breakpoint bp, List<TransitionDTO> transitions)
	{
		this(topProcess,state);
		this.bp = bp;
		this.transitions  = transitions;
	}
	
	public CmlInterpreterStateDTO(CmlInterpreterState state)
	{
		this.state = state;
		topLevelProcess = null;
		bp = null;
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

	public void addError(InterpreterErrorDTO error) {

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

	public CmlInterpreterState getInterpreterState()
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

	public List<TransitionDTO> getTransitions()
	{
		return transitions;
	}
}
