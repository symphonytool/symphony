package eu.compassresearch.core.interpreter.api.events;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;
import eu.compassresearch.core.interpreter.utility.events.Event;

/**
 * This represents a change of CmlInterpreterState in a specific CmlInterpreter instance.
 * This event is fired whenever a CmlInterpreter changes its state.
 * @author akm
 *
 */
public class InterpreterStatusEvent extends Event<CmlInterpreter> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6478492523089731326L;
	private final CmlInterpreterState status;
	private final Object content;

	public InterpreterStatusEvent(CmlInterpreter interpreter,CmlInterpreterState status)
	{
		super(interpreter);
		this.status = status;
		this.content = null;
	}
	
	public InterpreterStatusEvent(CmlInterpreter interpreter,CmlInterpreterState status, Object content)
	{
		super(interpreter);
		this.status = status;
		this.content = content;
	}
	
	public CmlInterpreterState getStatus() {
		return status;
	}
	
	public <T> T getContent()
	{
		return (T)this.content;
	} 
}
