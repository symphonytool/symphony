package eu.compassresearch.core.interpreter.api.events;

import eu.compassresearch.core.interpreter.api.CmlInterpreter;
import eu.compassresearch.core.interpreter.api.CmlInterpreterState;

/**
 * This represents a change of CmlInterpreterState in a specific CmlInterpreter instance. This event is fired whenever a
 * CmlInterpreter changes its state.
 * 
 * @author akm
 */
public class InterpreterStateChangedEvent extends Event<CmlInterpreter>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6478492523089731326L;
	private final CmlInterpreterState status;

	public InterpreterStateChangedEvent(CmlInterpreter interpreter)
	{
		super(interpreter);
		this.status = interpreter.getState();
	}


	public CmlInterpreterState getStatus()
	{
		return status;
	}


	@Override
	public String toString()
	{

		return getStatus().toString();
	}
}
